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
package net.sf.jasperreports.export.type;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.type.EnumUtil;
import net.sf.jasperreports.engine.type.NamedEnum;

import com.lowagie.text.pdf.PdfWriter;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: PdfPermissionsEnum.java 6885 2014-02-11 08:53:01Z shertage $
 */
public enum PdfPermissionsEnum implements NamedEnum {
	
	/**
	 * All user permissions
	 */
	ALL(PdfWriter.ALLOW_PRINTING 
			| PdfWriter.ALLOW_MODIFY_CONTENTS 
			| PdfWriter.ALLOW_COPY 
			| PdfWriter.ALLOW_MODIFY_ANNOTATIONS 
			| PdfWriter.ALLOW_FILL_IN 
			| PdfWriter.ALLOW_SCREENREADERS 
			| PdfWriter.ALLOW_ASSEMBLY 
			|PdfWriter.ALLOW_DEGRADED_PRINTING, "ALL"),
	/**
	 * Assembly permission
	 */
	ASSEMBLY(PdfWriter.ALLOW_ASSEMBLY, "ASSEMBLY"),
	
	/**
	 * Copy permission
	 */
	COPY(PdfWriter.ALLOW_COPY, "COPY"),
	
	/**
	 * Degraded printing permission
	 */
	DEGRADED_PRINTING(PdfWriter.ALLOW_DEGRADED_PRINTING, "DEGRADED_PRINTING"),
	
	/**
	 * Fill in forms permission
	 */
	FILL_IN(PdfWriter.ALLOW_FILL_IN, "FILL_IN"),
	
	/**
	 * Modify annotations permission
	 */
	MODIFY_ANNOTATIONS(PdfWriter.ALLOW_MODIFY_ANNOTATIONS, "MODIFY_ANNOTATIONS"),
	
	/**
	 * Modify contents permission
	 */
	MODIFY_CONTENTS(PdfWriter.ALLOW_MODIFY_CONTENTS, "MODIFY_CONTENTS"),
	
	/**
	 * Print permission
	 */
	PRINTING(PdfWriter.ALLOW_PRINTING, "PRINTING"),
	
	/**
	 * Screen readers permission
	 */
	SCREENREADERS(PdfWriter.ALLOW_SCREENREADERS, "SCREENREADERS");
	
	
	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	private final transient int value;
	private final transient String name;

	private PdfPermissionsEnum(int value, String name) {
		this.value = value;
		this.name = name!= null ? name.toUpperCase() : null;
	}

	/**
	 *
	 */
	public Integer getValueInteger() {
		return Integer.valueOf(value);
	}
	
	/**
	 *
	 */
	public final int getValue() {
		return value;
	}
	
	/**
	 *
	 */
	public String getName() {
		return name;
	}
	
	/**
	 *
	 */
	public static PdfPermissionsEnum getByName(String name) {
		return name != null ? EnumUtil.<PdfPermissionsEnum>getEnumByName(values(), name.toUpperCase()) : null;
	}
	
	/**
	 *
	 */
	public static PdfPermissionsEnum getByValue(Integer value) {
		PdfPermissionsEnum[] values = values();
		if (values != null && value != null) {
			for(PdfPermissionsEnum e:values) {
				if (value.equals(e.getValueInteger())) {
					return e;
				}
			}
		}
		return null;
	}
	
	/**
	 *
	 */
	public static PdfPermissionsEnum getByValue(byte value)	{
		return getByValue(Integer.valueOf(value));
	}

}
