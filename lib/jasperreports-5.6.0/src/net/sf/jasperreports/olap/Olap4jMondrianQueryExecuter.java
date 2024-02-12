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
package net.sf.jasperreports.olap;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;
import net.sf.jasperreports.olap.mondrian.JRMondrianResult;
import net.sf.jasperreports.olap.result.JROlapResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.OlapStatement;
import org.olap4j.layout.CellSetFormatter;
import org.olap4j.layout.RectangularCellSetFormatter;


/**
 * @author swood
 * @version $Id: Olap4jMondrianQueryExecuter.java 6923 2014-02-24 14:18:52Z teodord $
 */
public class Olap4jMondrianQueryExecuter extends JRAbstractQueryExecuter
{
	private static final Log log = LogFactory.getLog(Olap4jMondrianQueryExecuter.class);

	public static final String OLAP4J_DRIVER = "olap4jDriver";
	public static final String OLAP4J_URL_PREFIX = "urlPrefix";
	public static final String OLAP4J_JDBC_DRIVERS = "JdbcDrivers";
	public static final String OLAP4J_JDBC_URL = "Jdbc";
	public static final String OLAP4J_JDBC_CATALOG = "Catalog";

	public static final String OLAP4J_JDBC_USER = "JdbcUser";
	public static final String OLAP4J_JDBC_PASSWORD = "JdbcPassword";

	public static final String OLAP4J_MONDRIAN_DRIVER_CLASS = "mondrian.olap4j.MondrianOlap4jDriver";
	public static final String OLAP4J_MONDRIAN_URL_PREFIX = "jdbc:mondrian:";

	private Connection rConnection;
	private JRMondrianResult monResult;
	
	/**
	 * 
	 */
	public Olap4jMondrianQueryExecuter(
		JasperReportsContext jasperReportsContext, 
		JRDataset dataset, 
		Map<String,? extends JRValueParameter> parametersMap
		)
	{
		super(jasperReportsContext, dataset, parametersMap);
		
		parseQuery();
	}

	@Override
	protected String getCanonicalQueryLanguage()
	{
		return Olap4jQueryExecuterFactory.CANONICAL_LANGUAGE;
	}

	protected String getParameterReplacement(String parameterName)
	{
		return String.valueOf(getParameterValue(parameterName));
	}

	public JRDataSource createDatasource() throws JRException
	{
		JRDataSource dataSource = null;
		
		Properties connectProps = new Properties();
		connectProps.put(OLAP4J_JDBC_DRIVERS, getParameterValue(Olap4jMondrianQueryExecuterFactory.PARAMETER_JDBC_DRIVERS));
		connectProps.put(OLAP4J_JDBC_URL, getParameterValue(Olap4jMondrianQueryExecuterFactory.PARAMETER_JDBC_URL));
		connectProps.put(OLAP4J_JDBC_CATALOG, getParameterValue(Olap4jMondrianQueryExecuterFactory.PARAMETER_CATALOG));
		String user = (String) getParameterValue(Olap4jMondrianQueryExecuterFactory.PARAMETER_JDBC_USER);
		if (user != null) {
			connectProps.put(OLAP4J_JDBC_USER, user);
		}
		String password = (String) getParameterValue(Olap4jMondrianQueryExecuterFactory.PARAMETER_JDBC_PASSWORD);
		if (password != null) {
			connectProps.put(OLAP4J_JDBC_PASSWORD, password);
		}
		connectProps.put(OLAP4J_DRIVER, OLAP4J_MONDRIAN_DRIVER_CLASS);
		connectProps.put(OLAP4J_URL_PREFIX, OLAP4J_MONDRIAN_URL_PREFIX);
		
		// load driver  and Connection
		rConnection = null;
		try
		{
			Class.forName(OLAP4J_MONDRIAN_DRIVER_CLASS);
			rConnection = java.sql.DriverManager.getConnection(OLAP4J_MONDRIAN_URL_PREFIX, connectProps);
		}
		catch (Throwable t)
		{
			throw new JRException("error loading Mondrian olap4j driver and getting Connection '" + OLAP4J_MONDRIAN_DRIVER_CLASS + "'", t);
		}

		OlapConnection connection = (OlapConnection) rConnection;
		
		String queryStr = getQueryString();
		if (connection != null && queryStr != null)
		{
			if (log.isDebugEnabled())
			{
				log.debug("MDX query: " + queryStr);
			}
			CellSet result = null;

			try
			{
				OlapStatement statement = connection.createStatement();

				result = statement.executeOlapQuery(getQueryString());
			}
			catch (OlapException e)
			{
				throw new JRException("Error executing query: " + getQueryString(),
						e);
			}

			if (log.isDebugEnabled())
			{
				OutputStream bos = new ByteArrayOutputStream();
				CellSetFormatter formatter = new RectangularCellSetFormatter(true);
				formatter.format(result, new PrintWriter(bos, true));
				log.debug("Result:\n" + bos.toString());
			}
			
			dataSource = new Olap4jDataSource(dataset, result);
		}

		return dataSource;
	}

	public boolean cancelQuery() throws JRException
	{
		return false;
	}

	public JROlapResult getResult()
	{
		return monResult;
	}

	@Override
	public void close()
	{
		//FIXME
	}
}
