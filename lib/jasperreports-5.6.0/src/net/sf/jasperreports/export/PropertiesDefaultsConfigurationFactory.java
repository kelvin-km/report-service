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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRPropertiesUtil.PropertySuffix;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.type.JREnum;
import net.sf.jasperreports.export.annotations.ExporterProperty;

import org.apache.commons.lang.ClassUtils;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: PropertiesExporterConfigurationFactory.java 6805 2014-01-10 12:42:19Z teodord $
 */
public class PropertiesDefaultsConfigurationFactory<C extends CommonExportConfiguration>
{
	/**
	 * 
	 */
	private final JasperReportsContext jasperReportsContext;
	
	/**
	 * 
	 */
	public PropertiesDefaultsConfigurationFactory(JasperReportsContext jasperReportsContext)
	{
		this.jasperReportsContext = jasperReportsContext;
	}

	
	/**
	 * 
	 */
	public C getConfiguration(final Class<C> configurationInterface)
	{
		return getProxy(configurationInterface, new PropertiesDefaultsInvocationHandler());
	}


	/**
	 * 
	 */
	private final C getProxy(Class<?> clazz, InvocationHandler handler)
	{
		List<Class<?>> allInterfaces = new ArrayList<Class<?>>();

		if (clazz.isInterface())
		{
			allInterfaces.add(clazz);
		}
		else
		{
			@SuppressWarnings("unchecked")
			List<Class<?>> lcInterfaces = ClassUtils.getAllInterfaces(clazz);
			allInterfaces.addAll(lcInterfaces);
		}

		@SuppressWarnings("unchecked")
		C proxy =
			(C)Proxy.newProxyInstance(
				ExporterConfiguration.class.getClassLoader(),
				allInterfaces.toArray(new Class<?>[allInterfaces.size()]),
				handler
				);
		
		return proxy;
	}


	/**
	 * 
	 */
	class PropertiesDefaultsInvocationHandler implements InvocationHandler
	{
		/**
		 * 
		 */
		public PropertiesDefaultsInvocationHandler()
		{
		}
		
		/**
		 * 
		 */
		public Object invoke(
			Object proxy, 
			Method method, 
			Object[] args
			) throws Throwable 
		{
			return getPropertyValue(method);
		}
	}
	
	
	/**
	 * 
	 */
	protected Object getPropertyValue(Method method)
	{
		Object value = null;
		ExporterProperty exporterProperty = method.getAnnotation(ExporterProperty.class);
		if (exporterProperty != null)
		{
			value = getPropertyValue(jasperReportsContext, exporterProperty, method.getReturnType());
		}
		return value;
	}
	
	
	/**
	 * 
	 */
	public static Object getPropertyValue(
		JasperReportsContext jasperReportsContext,
		ExporterProperty exporterProperty, 
		Class<?> type 
		)
	{
		Object value = null;
		
		String propertyName = exporterProperty.value();
		
		JRPropertiesUtil propertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);

		if (String[].class.equals(type))
		{
			List<PropertySuffix> properties = propertiesUtil.getProperties(propertyName);
			if (properties != null && !properties.isEmpty())
			{
				String[] values = new String[properties.size()];
				for(int i = 0; i < values.length; i++)
				{
					values[i] = properties.get(i).getValue();
				}
				
				value = values;
			}
		}
		else
		{
			String strValue = propertiesUtil.getProperty(propertyName);
			
			if (String.class.equals(type))
			{
				value = strValue;
			}
			else if (Character.class.equals(type))
			{
				value = JRPropertiesUtil.asCharacter(strValue);
			}
			else if (Integer.class.equals(type))
			{
				if (strValue == null)
				{
					if (!exporterProperty.acceptNull())
					{
						value = exporterProperty.intDefault();
					}
				}
				else
				{
					value = JRPropertiesUtil.asInteger(strValue);
				}
			}
			else if (Long.class.equals(type))
			{
				if (strValue == null)
				{
					if (!exporterProperty.acceptNull())
					{
						value = exporterProperty.longDefault();
					}
				}
				else
				{
					value = JRPropertiesUtil.asLong(strValue);
				}
			}
			else if (Float.class.equals(type))
			{
				if (strValue == null)
				{
					if (!exporterProperty.acceptNull())
					{
						value = exporterProperty.floatDefault();
					}
				}
				else
				{
					value = JRPropertiesUtil.asFloat(strValue);
				}
			}
			else if (Boolean.class.equals(type))
			{
				if (strValue == null)
				{
					if (!exporterProperty.acceptNull())
					{
						value = exporterProperty.booleanDefault();
					}
				}
				else
				{
					value = JRPropertiesUtil.asBoolean(strValue);
				}
			}
			else if (JREnum.class.isAssignableFrom(type))
			{
				if (strValue != null)
				{
					try
					{
						Method byNameMethod = type.getMethod("getByName", new Class<?>[]{String.class});
						value = byNameMethod.invoke(null, strValue);
					}
					catch (NoSuchMethodException e)
					{
						throw new JRRuntimeException(e);
					}
					catch (InvocationTargetException e)
					{
						throw new JRRuntimeException(e);
					}
					catch (IllegalAccessException e)
					{
						throw new JRRuntimeException(e);
					}
				}
			}
			else
			{
				throw new JRRuntimeException("Export property type " + type + " not supported.");
			}
		}
		
		return value;
	}
}
