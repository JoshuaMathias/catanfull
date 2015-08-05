package abstractFactory;

import java.sql.*;
import java.sql.DriverManager;

import dao.IGameDao;
import dao.IUserDao;
import dao.database.DbGameDao;
import dao.database.DbUserDao;
import database.ApacheImporter;

public class DbAbstractFactory implements IAbstractFactory {

	private static final String DATABASE_FILE = "CatanDB.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:"+ DATABASE_FILE;
	
	private IGameDao gameDao = new DbGameDao(this);
	private IUserDao userDao = new DbUserDao(this);
	
	private Connection connection;
	
	public DbAbstractFactory() {
		
		initialize();
	}
	
	@Override
	public IUserDao getUserDao() {
		// TODO Auto-generated method stub
		return userDao;
	}

	@Override
	public IGameDao getGameDao() {
		// TODO Auto-generated method stub
		return gameDao;
	}

	private void initialize() {
		// TODO Auto-generated method stub
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			System.out.println("Could not load database driver\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void erase() {
		// TODO Auto-generated method stub
		ApacheImporter.resetTables();
	}

	@Override
	public void startTransaction() {
		// TODO Auto-generated method stub
		try {
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			System.out.println();
			System.out.println("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean inTransaction() {
		// TODO Auto-generated method stub
		return (connection != null);
	}
	
	@Override
	public void endTransaction(boolean commit) {
		// TODO Auto-generated method stub
		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}

	private void safeClose(Connection conn) {//was originally public static
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				System.out.println("Could not close connection\n" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static void safeClose(PreparedStatement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public static void safeClose(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
