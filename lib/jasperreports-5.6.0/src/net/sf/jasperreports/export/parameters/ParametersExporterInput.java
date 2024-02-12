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
package net.sf.jasperreports.export.parameters;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;


/**
 * @deprecated To be removed.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: ParametersExporterInput.java 6698 2013-11-07 14:52:02Z teodord $
 */
public class ParametersExporterInput extends SimpleExporterInput
{
	/**
	 * 
	 */
	public ParametersExporterInput(Map<JRExporterParameter, Object> parameters)
	{
		super(getItems(getJasperPrintList(parameters)));
	}

	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private static List<JasperPrint> getJasperPrintList(Map<JRExporterParameter, Object> parameters)
	{
		List<JasperPrint> jasperPrintList = (List<JasperPrint>)parameters.get(JRExporterParameter.JASPER_PRINT_LIST);
		if (jasperPrintList == null)
		{
			JasperPrint jasperPrint = (JasperPrint)parameters.get(JRExporterParameter.JASPER_PRINT);
			if (jasperPrint == null)
			{
				try
				{
					InputStream is = (InputStream)parameters.get(JRExporterParameter.INPUT_STREAM);
					if (is != null)
					{
						jasperPrint = (JasperPrint)JRLoader.loadObject(is);
					}
					else
					{
						URL url = (URL)parameters.get(JRExporterParameter.INPUT_URL);
						if (url != null)
						{
							jasperPrint = (JasperPrint)JRLoader.loadObject(url);
						}
						else
						{
							File file = (File)parameters.get(JRExporterParameter.INPUT_FILE);
							if (file != null)
							{
								jasperPrint = (JasperPrint)JRLoader.loadObject(file);
							}
							else
							{
								String fileName = (String)parameters.get(JRExporterParameter.INPUT_FILE_NAME);
								if (fileName != null)
								{
									jasperPrint = (JasperPrint)JRLoader.loadObjectFromFile(fileName);
								}
								else
								{
									throw new JRRuntimeException("No input source supplied to the exporter.");
								}
							}
						}
					}
				}
				catch (JRException e)
				{
					throw new JRRuntimeException(e);
				}
			}
			
			jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);
		}
		else
		{
			if (jasperPrintList.size() == 0)
			{
				throw new JRRuntimeException("Empty input source supplied to the exporter in batch mode.");
			}
		}

		return jasperPrintList;
	}
}
