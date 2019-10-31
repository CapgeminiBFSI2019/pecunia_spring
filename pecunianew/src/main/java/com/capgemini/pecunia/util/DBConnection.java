package com.capgemini.pecunia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.capgemini.pecunia.exception.PecuniaException;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DBConnection {
	private static Connection conn = null;
	private static DBConnection instance = null;
	private static Properties props = null;
	private static MysqlDataSource dataSource = null;

	/*************************************************************************************
	 * - @throws DonorException - Private Constructor - Author : CAPGEMINI -
	 * Creation Date : 18/11/2016 - Desc:Loads the jdbc.properties file and Driver
	 * Class and gets the connection
	 ***************************************************************************************/
	private DBConnection() throws PecuniaException {
		try {
			props = loadProperties();
			dataSource = prepareDataSource();
		} catch (IOException e) {
			throw new PecuniaException(" Could not read the database details from properties file ");
		} catch (SQLException e) {
			throw new PecuniaException(e.getMessage());
		}

	}

	/*****************************************************************
	 * - Method Name:getInstance() - Input Parameters : - Return Type :DBConnection
	 * instance - Throws : DonorException - Author : CAPGEMINI - Creation Date :
	 * 18/11/2016 - Description : Singleton and Thread safe class
	 *******************************************************************/

	public static DBConnection getInstance() throws PecuniaException {
		synchronized (DBConnection.class) {
			if (instance == null) {
				instance = new DBConnection();
			}
		}
		return instance;
	}

	/*****************************************************************
	 * - Method Name:getConnection() - Input Parameters : - Return Type
	 * :DBConnection instance - Throws : DonorException - Author : CAPGEMINI -
	 * Creation Date : 18/11/2016 - Description : Returns connection object
	 *******************************************************************/
	public Connection getConnection() throws PecuniaException {
		try {

			conn = dataSource.getConnection();
		} catch (SQLException e) {
			throw new PecuniaException(" Database connection problem");
		}
		return conn;
	}

	/*****************************************************************
	 * - Method Name:loadProperties() - Input Parameters : - Return Type :Properties
	 * object - Author : CAPGEMINI - Creation Date : 18/11/2016 - Description :
	 * Returns Properties object
	 *******************************************************************/

	private Properties loadProperties() throws IOException {

		if (props == null) {
			Properties newProps = new Properties();
			String fileName = DBConnection.class.getClassLoader().getResource("").getPath()+"/jdbc.properties";
			InputStream inputStream = new FileInputStream(fileName);
			newProps.load(inputStream);

			inputStream.close();

			return newProps;
		} else {
			return props;
		}
	}

	/*****************************************************************
	 * - Method Name:prepareDataSource() - Input Parameters : - Return Type
	 * :OracleDataSource object - Author : CAPGEMINI - Creation Date : 18/11/2016 -
	 * Description : Returns OracleDataSource object
	 *******************************************************************/

	private MysqlDataSource prepareDataSource() throws SQLException {

		if (dataSource == null) {
			if (props != null) {
				String connectionURL = props.getProperty("dburl");
				String username = props.getProperty("username");
				String password = props.getProperty("password");

				dataSource = new MysqlDataSource();

				dataSource.setURL(connectionURL);
				dataSource.setUser(username);
				dataSource.setPassword(password);
			}
		}
		return dataSource;
	}

}
