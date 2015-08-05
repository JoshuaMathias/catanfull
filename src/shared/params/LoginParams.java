package shared.params;

/**
 * 
 * Class for sending data for a login request.
 * @author Ife's Group
 *
 */
public class LoginParams 
{
	private String username = "";
	private String password = "";
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginParams(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
}
