package client.serverproxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import shared.gameModel.GameModel;

/**
 * Packages and sends commands to the Server from the ServerProxy
 * @author Ife's Group
 *
 */
public class ClientCommunicator 
{
	private String hostname = "";
	private String cookies = "";
	private HttpURLConnection connection = null;
	
	//Might have to have this be a singleton object
	public ClientCommunicator(String hostname)
	{
		this.hostname = hostname;
	}
	
	/**
	 * packages information from ServerProxy and sends to Server
	 * @param command
	 * @param params
	 * @pre command is a valid command that the Server would understand. Params must not be null and all can do methods applying to the command in the client model have returned true
	 * @post sends the command and params to the server
	 */
	public String send(String ext, String urlParameters)
	{
		String result = "";
		
		try
		{			
			String targetURL = "http://"+hostname+":8081/"+ext;
			URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	
		    connection.setRequestProperty("Content-Length",Integer.toString(urlParameters.getBytes().length));
		    connection.setRequestProperty("Content-Language", "en-US");  
		    if(!cookies.isEmpty())
		    {
			    connection.setRequestProperty("Cookie", cookies);
		    }
	
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
	
		    //Send request
		    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		    wr.writeBytes(urlParameters);
		    wr.close();
	
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
		    String line;
	    	while((line = rd.readLine()) != null) 
	    	{
	    		response.append(line);
	    		response.append('\r');
	    	}
	    	rd.close();
	    	
//	    	System.out.println("");
	    	result = response.toString();
//	    	System.out.println(response.toString()+ "for "+ext+" Response code: "+ connection.getResponseCode()); 
	    	connection.getResponseCode(); //comment this out if you uncomment the previous line
	    	if(ext.equals("user/login")||ext.equals("user/register"))
	    	{
		    	String usercookie = connection.getHeaderField("Set-Cookie");
		    	cookies = (String)usercookie.subSequence(0,usercookie.length()-8);
		    	//System.out.println(cookies);
	    	}
	    	else if(ext.equals("games/join"))
	    	{
	    		String gamecookie = connection.getHeaderField("Set-Cookie");
	    		//System.out.println(gamecookie);
		    	cookies =cookies+";"+" "+ (String)gamecookie.subSequence(0, gamecookie.length()-8);
		    	
	    	}
		}
		
		catch (Exception e) 
		{
			try{
			System.out.println("Response message for the bad call"+connection.getResponseMessage());
			}catch(Exception e1){System.out.println("\nIs this doing anything");}
			System.out.println("Current cookies: "+cookies);
			
			result = "400";
	    	System.out.println("for "+ext+" Response code: "+ result); 
			e.printStackTrace();
	    } 
		
		finally 
		{
			if(connection != null) 
			{
				connection.disconnect(); 
			}
		}
//		System.out.println(result);
		return result;
	}
}
