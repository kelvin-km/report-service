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

import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.export.annotations.ExporterParameter;
import net.sf.jasperreports.export.annotations.ExporterProperty;


/**
 * Interface containing settings used by the pure text exporter.
 *
 * @see JRTextExporter
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: TextExporterConfiguration.java 6993 2014-03-28 10:33:00Z teodord $
 */
public interface TextExporterConfiguration extends ExporterConfiguration
{
	/**
	 * Property whose value is used as default for the {@link #getPageSeparator()} export configuration setting.
	 * The default is the duplicated value of the "line.separator" system property.
	 * 
	 * @see JRPropertiesUtil
	 */
	public static final String PROPERTY_PAGE_SEPARATOR = JRPropertiesUtil.PROPERTY_PREFIX + "export.pdf.text.page.separator";

	/**
	 * Property whose value is used as default for the {@link #getLineSeparator()} export configuration setting.
	 * The default is the value of the "line.separator" system property.
	 * 
	 * @see JRPropertiesUtil
	 */
	public static final String PROPERTY_LINE_SEPARATOR = JRPropertiesUtil.PROPERTY_PREFIX + "export.pdf.text.line.separator";

	/**
	 * Returns a string representing text that will be inserted between pages of the generated report. By default, JasperReports
	 * separates pages by two empty lines, but this behavior can be overridden by this parameter.
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=net.sf.jasperreports.engine.export.JRTextExporterParameter.class, 
		name="BETWEEN_PAGES_TEXT"
		)
	@ExporterProperty(PROPERTY_PAGE_SEPARATOR)
	public String getPageSeparator();
	
	/**
	 * Returns a string representing the separator between two lines of text. This parameter is useful since line separators can
	 * vary from one operating system to another. The default value is the system "line.separator" property.
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=net.sf.jasperreports.engine.export.JRTextExporterParameter.class, 
		name="LINE_SEPARATOR"
		)
	@ExporterProperty(PROPERTY_LINE_SEPARATOR)
	public String getLineSeparator();
}
