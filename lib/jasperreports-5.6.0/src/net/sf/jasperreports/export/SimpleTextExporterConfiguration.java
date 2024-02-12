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
package net.sf.jasperreports.export;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: SimpleTextExporterConfiguration.java 6993 2014-03-28 10:33:00Z teodord $
 */
public class SimpleTextExporterConfiguration extends SimpleExporterConfiguration implements TextExporterConfiguration
{
	private String pageSeparator;
	private String lineSeparator;

	
	/**
	 * 
	 */
	public SimpleTextExporterConfiguration()
	{
	}

	/**
	 * 
	 */
	public String getPageSeparator()
	{
		return pageSeparator;
	}
	
	/**
	 * 
	 */
	public void setPageSeparator(String pageSeparator)
	{
		this.pageSeparator = pageSeparator;
	}
	
	/**
	 * 
	 */
	public String getLineSeparator()
	{
		return lineSeparator;
	}
	
	/**
	 * 
	 */
	public void setLineSeparator(String lineSeparator)
	{
		this.lineSeparator = lineSeparator;
	}
}
