package shared.params;

/**
 * 
 * Class for sending data for a register request.
 * @author Ife's Group
 *
 */
public class RegisterParams 
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

	public RegisterParams(String username, String password) 
	{
		this.username = username;
		this.password = password;
	}
}
