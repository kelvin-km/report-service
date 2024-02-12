/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.engine.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.util.FormatUtils;
import net.sf.jasperreports.repo.RepositoryUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * This data source implementation reads an XLSX or XLS stream.
 * <p>
 * The default naming convention is to name report fields COLUMN_x and map each column with the field found at index x 
 * in each row (these indices start with 0). To avoid this situation, users can either specify a collection of column 
 * names or set a flag to read the column names from the first row of the XLSX or XLS file.
 *
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: ExcelDataSource.java 6968 2014-03-12 08:58:07Z shertage $
 */
public abstract class AbstractPoiXlsDataSource extends AbstractXlsDataSource
{
	private Workbook workbook;

	private int sheetIndex = -1;
	private int recordIndex = -1;

	private InputStream inputStream;
	private boolean closeWorkbook;
	private boolean closeInputStream;


	/**
	 * Creates a data source instance from a workbook.
	 * @param workbook the workbook
	 */
	public AbstractPoiXlsDataSource(Workbook workbook)
	{
		this.workbook = workbook;
		this.closeWorkbook = false;
	}


	/**
	 * Creates a data source instance from an XLSX or XLS data input stream.
	 * @param is an input stream containing XLSX or XLS data
	 */
	public AbstractPoiXlsDataSource(InputStream is) throws JRException, IOException
	{
		this.inputStream = is;
		this.closeWorkbook = true;
		this.closeInputStream = false;

		this.workbook = loadWorkbook(inputStream);
	}


	/**
	 * Creates a data source instance from an XLSX or XLS file.
	 * @param file a file containing XLSX or XLS data
	 */
	public AbstractPoiXlsDataSource(File file) throws JRException, IOException
	{
		this(new FileInputStream(file));
		this.closeInputStream = true;
	}

	
	/**
	 * Creates a data source instance that reads XLSX or XLS data from a given location.
	 * @param jasperReportsContext the JasperReportsContext
	 * @param location a String representing XLSX or XLS data source
	 * @throws IOException 
	 */
	public AbstractPoiXlsDataSource(JasperReportsContext jasperReportsContext, String location) throws JRException, IOException
	{
		this(RepositoryUtil.getInstance(jasperReportsContext).getInputStreamFromLocation(location));
		this.closeInputStream = true;
	}

	
	/**
	 * @see #AbstractPoiXlsDataSource(JasperReportsContext, String)
	 */
	public AbstractPoiXlsDataSource(String location) throws JRException, IOException
	{
		this(DefaultJasperReportsContext.getInstance(), location);
	}
	
	
	/**
	 * 
	 */
	protected abstract Workbook loadWorkbook(InputStream is) throws IOException;
	

	/**
	 *
	 */
	public boolean next() throws JRException
	{
		if (workbook != null)
		{
			//initialize sheetIndex before first record
			if (sheetIndex < 0)
			{
				if (sheetSelection == null) 
				{
					sheetIndex = 0;
				}
				else
				{
					try
					{
						sheetIndex = Integer.parseInt(sheetSelection);
						if (sheetIndex < 0 || sheetIndex > workbook.getNumberOfSheets() - 1)
						{
							throw new JRRuntimeException("Sheet index " + sheetIndex + " is out of range: [0.." + (workbook.getNumberOfSheets() - 1) + "]");
						}
					}
					catch (NumberFormatException e)
					{
					}
					
					if (sheetIndex < 0)
					{
						sheetIndex = workbook.getSheetIndex(workbook.getSheet(sheetSelection));

						if (sheetIndex < 0)
						{
							throw new JRRuntimeException("Sheet '" + sheetSelection + "' not found in workbook.");
						}
					}
				}
			}

			recordIndex++;
			
			if (sheetSelection == null) 
			{
				if (recordIndex > workbook.getSheetAt(sheetIndex).getLastRowNum())
				{
					if (sheetIndex + 1 < workbook.getNumberOfSheets() 
						&& workbook.getSheetAt(sheetIndex + 1).getLastRowNum() > 0)
					{
						sheetIndex++;
						recordIndex = -1;
						return next();
					}
				}
			}
			
			if ((sheetSelection != null || sheetIndex == 0) && useFirstRowAsHeader && recordIndex == 0) 
			{
				readHeader();
				recordIndex++;
			}

			if (recordIndex <= workbook.getSheetAt(sheetIndex).getLastRowNum())
			{
				return true;
			}
			else
			{
				if (closeWorkbook)
				{
					//FIXME: close workbook
					//workbook.close();
				}
			}
		}

		return false;
	}


	/**
	 *
	 */
	public void moveFirst()
	{
		this.recordIndex = -1;
		this.sheetIndex = -1;
	}


	/**
	 *
	 */
	public Object getFieldValue(JRField jrField) throws JRException
	{
		String fieldName = jrField.getName();

		Integer columnIndex = columnNames.get(fieldName);
		if (columnIndex == null && fieldName.startsWith("COLUMN_")) 
		{
			columnIndex = Integer.valueOf(fieldName.substring(7));
		}
		if (columnIndex == null)
		{
			throw new JRException("Unknown column name : " + fieldName);
		}
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		Cell cell = sheet.getRow(recordIndex).getCell(columnIndex);
		Class<?> valueClass = jrField.getValueClass();
		
		if (valueClass.equals(String.class)) 
		{
			return cell.getStringCellValue();
		}
		try 
		{
			if (valueClass.equals(Boolean.class)) 
			{
				if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
				{
					return cell.getBooleanCellValue();
				}
				else
				{
					return convertStringValue(cell.getStringCellValue(), valueClass);
				}
			}
			else if (Number.class.isAssignableFrom(valueClass))
			{
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				{
					return convertNumber(cell.getNumericCellValue(), valueClass);
				}
				else
				{
					if (numberFormat != null)
					{
						return FormatUtils.getFormattedNumber(numberFormat, cell.getStringCellValue(), valueClass);
					}
					else 
					{
						return convertStringValue(cell.getStringCellValue(), valueClass);
					}
				}
			}
			else if (Date.class.isAssignableFrom(valueClass))
			{
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				{
					return cell.getDateCellValue();
				}
				else
				{
					if (dateFormat != null)
					{
						return FormatUtils.getFormattedDate(dateFormat, cell.getStringCellValue(), valueClass);
					} 
					else
					{
						return convertStringValue(cell.getStringCellValue(), valueClass);
					}
				}
			}
			else
			{
				throw new JRException("Field '" + jrField.getName() + "' is of class '" + valueClass.getName() + "' and can not be converted");
			}
		}
		catch (Exception e) 
		{
			throw new JRException("Unable to get value for field '" + jrField.getName() + "' of class '" + valueClass.getName() + "'", e);
		}
	}


	/**
	 *
	 */
	private void readHeader()
	{
		Sheet sheet = workbook.getSheetAt(sheetSelection != null ? sheetIndex : 0);
		if (columnNames.size() == 0)
		{
			Row row = sheet.getRow(recordIndex);
			for(int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++)
			{
				Cell cell = row.getCell(columnIndex);
				if(cell != null)
				{
					columnNames.put(cell.toString(), columnIndex);
				}
				else
				{
					columnNames.put("COLUMN_" + columnIndex, columnIndex);
				}
			}
		}
		else
		{
			Map<String, Integer> newColumnNames = new LinkedHashMap<String, Integer>();
			for(Iterator<Integer> it = columnNames.values().iterator(); it.hasNext();)
			{
				Integer columnIndex = it.next();
				Row row = sheet.getRow(recordIndex) ;
				Cell cell = row.getCell(columnIndex);
				if(cell != null)
				{
					newColumnNames.put(cell.toString(), columnIndex);
				}
			}
			columnNames = newColumnNames;
		}
	}


	/**
	 * Closes the reader. Users of this data source should close it after usage.
	 */
	public void close()
	{
		try
		{
			if (closeInputStream)
			{
				inputStream.close();
			}
		}
		catch(IOException e)
		{
			//nothing to do
		}
	}


	protected void checkReadStarted()
	{
		if (sheetIndex >= 0)
		{
			throw new JRRuntimeException("Cannot modify data source properties after data reading has started.");
		}
	}
	
}


