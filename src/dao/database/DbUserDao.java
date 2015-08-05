package dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import abstractFactory.DbAbstractFactory;
import server.User;
import dao.IUserDao;

public class DbUserDao implements IUserDao {
	
	private DbAbstractFactory db;
	
	public DbUserDao(DbAbstractFactory db) {
		this.db = db;
	}
	
	@Override
	public void addUser(User user) {
		
		PreparedStatement stmt = null;
		Connection connection = db.getConnection();
		
		
		try {
			String sql = "insert into User " + "( username, password, playerID)" + "values ( ?, ?, ?);";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setInt(3, user.getPlayerID());
			
			if(stmt.executeUpdate() == 1) {
				
				System.out.println("Inserted the user correctly");
			}
			else {
				System.out.println("Could not insert the user");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			DbAbstractFactory.safeClose(stmt);
		}
		
		
	}

	@Override
	public List<User> getAllUsers() {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection connection = db.getConnection();
		ArrayList<User> users = new ArrayList<>();
		
		try {
			String sql = "select username, password, playerID from User";
			stmt = connection.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				String username = rs.getString(1);
				String password = rs.getString(2);
				int userID = rs.getInt(3);
				
				User user = new User(username,password,userID);
				users.add(user);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			DbAbstractFactory.safeClose(stmt);
			DbAbstractFactory.safeClose(rs);
		}
		
		return users;
	}

}
