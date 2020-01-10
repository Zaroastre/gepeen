package io.nirahtech.gepeen.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static final String DATABASE_NAME = "petshop";

	private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
	private static final String USER = "petshop";
	private static final String PASSWORD = "petshop";

	private static Connection connexion = null;
	
	private boolean isConnected = false;
	private String table;

	private void loadDriver() {
		try {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Database() {
		loadDriver();
	}

	public <T> Database( Class<T> table) {
		this.table = table.getSimpleName().toLowerCase();
		/* Chargement du driver JDBC pour MySQL */
		loadDriver();
	}
	
	public boolean connect() {
		loadDriver();
		try {
		    connexion = DriverManager.getConnection(URL, USER, PASSWORD);

		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		return this.isConnected;
	}
	
	public void disconnect() {
		if (connexion != null) {
			try {
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.isConnected = false;
	}
	
	public boolean exist() {
		final String QUERY = "SHOW TABLES;";
		Statement statement;
		try {
			statement = connexion.createStatement();
			final ResultSet resultSet = statement.executeQuery(QUERY);
			while(resultSet.next()) {
				final String tableName = resultSet.getString(1);
				if (tableName.equalsIgnoreCase(this.table)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean execute(String sqlFileName) {
		if (sqlFileName.endsWith(".sql")) {
			File sqlFileToExecute = new File(getClass().getClassLoader().getResource(("sql"+File.separator+sqlFileName).toLowerCase()).getFile());
			if (sqlFileToExecute.exists() && sqlFileToExecute.isFile()) {
				try {
					final BufferedReader eye = new BufferedReader(new FileReader(sqlFileToExecute));
					String line;
					StringBuffer buffer = new StringBuffer();
					while ((line = eye.readLine()) != null) {
						buffer.append(line+"\n");
					}
					eye.close();
					Statement statement = connexion.createStatement();
					return statement.execute(buffer.toString());
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public Connection getConnection() {
		return connexion;
	}
}
