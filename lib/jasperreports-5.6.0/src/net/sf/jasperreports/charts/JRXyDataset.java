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
package net.sf.jasperreports.charts;

import net.sf.jasperreports.engine.JRChartDataset;


/**
 * This dataset is a wrapper for data series made of (x, y) value pairs and is used for 
 * rendering XY Bar, XY Line, XY Area, and Scatter Plot charts.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRXyDataset.java 6995 2014-04-01 11:26:53Z shertage $
 */
public interface JRXyDataset extends JRChartDataset
{
	
	/**
	 * @return an array of {@link JRXySeries} objects representing the 
	 * series for the XY charts.
	 * @see JRXySeries
	 */
	public JRXySeries[] getSeries();

}
