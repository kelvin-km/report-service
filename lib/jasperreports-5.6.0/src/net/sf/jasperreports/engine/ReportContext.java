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
package net.sf.jasperreports.engine;



/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: ReportContext.java 6984 2014-03-21 13:17:53Z narcism $
 */
public interface ReportContext
{
	public static final String REQUEST_PARAMETER_APPLICATION_DOMAIN = "jr_app_domain";

	/**
	 *
	 */
	public String getId();

	/**
	 *
	 */
	public Object getParameterValue(String parameterName);

	/**
	 *
	 */
	public void setParameterValue(String parameterName, Object value);

	/**
	 *
	 */
	public boolean containsParameter(String parameterName);
}
