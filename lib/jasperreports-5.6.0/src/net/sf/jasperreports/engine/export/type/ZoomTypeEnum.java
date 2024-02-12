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
package net.sf.jasperreports.engine.export.type;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.type.EnumUtil;
import net.sf.jasperreports.engine.type.JREnum;


/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 * @version $Id: ZoomTypeEnum.java 6941 2014-03-03 10:32:32Z narcism $
 */
public enum ZoomTypeEnum implements JREnum
{
	/**
	 *
	 */
	ACTUAL_SIZE((byte)0, "ActualSize", "fit_actual"),

	/**
	 *
	 */
	FIT_WIDTH((byte)1, "FitWidth", "fit_width"),

	/**
	 *
	 */
	FIT_HEIGHT((byte)2, "FitHeight", "fit_height"),

	/**
	 *
	 */
	FIT_PAGE((byte)3, "FitPage", "fit_page");


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	private final transient byte value;
	private final transient String name;
	private final transient String htmlValue;

	private ZoomTypeEnum(byte value, String name, String htmlValue)
	{
		this.value = value;
		this.name = name;
		this.htmlValue = htmlValue;
	}

	/**
	 *
	 */
	public Byte getValueByte()
	{
		return new Byte(value);
	}
	
	/**
	 *
	 */
	public final byte getValue()
	{
		return value;
	}
	
	/**
	 *
	 */
	public String getName()
	{
		return name;
	}

	/**
	 *
	 */
	public String getHtmlValue()
	{
		return htmlValue;
	}

	/**
	 *
	 */
	public static ZoomTypeEnum getByName(String name)
	{
		return (ZoomTypeEnum)EnumUtil.getByName(values(), name);
	}
	
	/**
	 *
	 */
	public static ZoomTypeEnum getByValue(Byte value)
	{
		return (ZoomTypeEnum)EnumUtil.getByValue(values(), value);
	}
	
	/**
	 *
	 */
	public static ZoomTypeEnum getByValue(byte value)
	{
		return getByValue(new Byte(value));
	}

}
