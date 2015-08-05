package dao.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import abstractFactory.DbAbstractFactory;
import server.command.Command;
import shared.gameModel.GameModel;
import dao.IGameDao;

/**
 * concrete implementation of the Game DAO for using a DB
 * @author Ife's Group
 *
 */
public class DbGameDao implements IGameDao {

	private DbAbstractFactory db;
	
	public DbGameDao(DbAbstractFactory db) {
		this.db = db;
	}
	
	@Override
	public void addGame(GameModel game) {
		
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Connection connection = db.getConnection();
		
		try {
			String sql = "insert into Game " + "( gameModel)" + "values ( ?);";
			stmt = connection.prepareStatement(sql);
			
			ByteArrayOutputStream b = new ByteArrayOutputStream();
	        ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(game);
			byte[] gameBytes = b.toByteArray();
			
			stmt.setBytes(1, gameBytes);
			
			if(stmt.executeUpdate() == 1) {
				
				System.out.println("Inserted game correctly");
				Statement keyStmt = connection.createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				game.setPrimaryKey(id);
			}
			else {
				System.out.println("Could not insert the user");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DbAbstractFactory.safeClose(stmt);
			DbAbstractFactory.safeClose(keyRS);
		}
		
	}

	@Override
	public void getGame(int gameID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameModel> getAllGames() {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection connection = db.getConnection();
		ArrayList<GameModel> games = new ArrayList<>();
		
		try {
			String sql = "select id, gameModel from Game";
			stmt = connection.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				int primaryKey = rs.getInt(1);
				
				System.out.println("Primary Key is: " + primaryKey);
				
				byte[] buf = rs.getBytes(2);
				ObjectInputStream objectIn = null;
				if (buf != null)
					objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

				GameModel game = (GameModel)objectIn.readObject();
				
				game.setPrimaryKey(primaryKey);
				games.add(game);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DbAbstractFactory.safeClose(stmt);
			DbAbstractFactory.safeClose(rs);
		}
		
		return games;
	}

	@Override
	public void updateGame(GameModel game) {
		
		PreparedStatement stmt = null;
		Connection connection = db.getConnection();
		
		try {
			String sql = "update Game set gameModel = ? where id = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setObject(1, game);
			stmt.setInt(2, game.getPrimaryKey());
			
			if(stmt.executeUpdate() != 1) {;
				
				System.out.println("The Game was not update!!!!");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			DbAbstractFactory.safeClose(stmt);
		}
		
		removeCommands(game.getPrimaryKey());
	}
	
	private void removeCommands(int key) {
		
		PreparedStatement stmt = null;
		Connection connection = db.getConnection();
		
		try {
			String sql = "DELETE FROM Command WHERE gameID = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, key);
			
			if(stmt.executeUpdate() != 1) {;
				
				System.out.println("The Commands were not deleted!!!!");
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
	public void removeGame(int gameID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCommand(Command command, int key) {
		
		PreparedStatement stmt = null;
		Connection connection = db.getConnection();
		
		try {
			String sql = "insert into Command " + "( gameID, command)" + "values ( ?, ?);";
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, key);
			
			ByteArrayOutputStream b = new ByteArrayOutputStream();
	        ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(command);
			byte[] commandBytes = b.toByteArray();
			
			stmt.setBytes(2, commandBytes);
			
			
			if(stmt.executeUpdate() == 1) {
				
				System.out.println("Inserted game correctly");
			}
			else {
				System.out.println("Could not insert the user");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DbAbstractFactory.safeClose(stmt);
		}
	}

	@Override
	public List<Command> getCommands(int gameID) {//Look at how to deserialize from the specs we found online
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection connection = db.getConnection();
		ArrayList<Command> commands = new ArrayList<>();
		
		try {
			String sql = "select command from Command where gameID = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, gameID);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				byte[] buf = rs.getBytes(1);
				ObjectInputStream objectIn = null;
				if (buf != null)
					objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

				Command command = (Command)objectIn.readObject();
				
				commands.add(command);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DbAbstractFactory.safeClose(stmt);
			DbAbstractFactory.safeClose(rs);
		} 
		return commands;
	}

}
