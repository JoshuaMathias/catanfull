package server;
/**
 * Represents a user stored in the server.
 * @author Ife's Group
 *
 */
public class User {
	String name;
	String password;
	int playerID;
	
	public User(String username, String password, int playerID) {
		this.name = username;
		this.password = password;
		this.playerID = playerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String username) {
		this.name = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
}
