package dao.database;

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
			
			stmt.setObject(1, game);
			
			if(stmt.executeUpdate() == 1) {
				
				System.out.println("Inserted game correctly");
				Statement keyStmt = connection.createStatement();
				keyRS = keyStmt.executeQuery(sql);
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
			String sql = "select primaryKey, gameModel from Game";
			stmt = connection.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				int primaryKey = rs.getInt(1);
				GameModel game = (GameModel)rs.getObject(2);
				
				game.setPrimaryKey(primaryKey);
				games.add(game);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
			stmt.setObject(2, command);
			
			
			if(stmt.executeUpdate() == 1) {
				
				System.out.println("Inserted game correctly");
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
	public List<Command> getCommands(int gameID) {//Look at how to deserialize from the specs we found online
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection connection = db.getConnection();
		ArrayList<Command> commands = new ArrayList<>();
		
		try {
			String sql = "select command from Command where gameID = ?";
			stmt = connection.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			stmt.setInt(1, gameID);
			
			rs = stmt.executeQuery();
			
			Command command = null;
			while(rs.next()) {
				
				command =(Command) rs.getObject(1);
				commands.add(command);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			DbAbstractFactory.safeClose(stmt);
			DbAbstractFactory.safeClose(rs);
		}
		return commands;
	}

}
