package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.List;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;

import Testing.Proxy.ServerFacadeTest;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GameHandler implements HttpHandler 
{
	public static boolean test = false;

	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		//Setting up the things that I will use later
		IServerFacade facade = null;
		if (test) {
			facade = ServerFacadeTest.getSingleton();
		} else {
			facade = ServerFacade.getSingleton();
		}
		Gson g = new Gson();
//		System.out.println("GameHandler called");
		
		try
		{
			//taking the command URI
			String command = exchange.getRequestURI().toString().substring(6);
//			System.out.println("Command: "+command);
			
			//Getting version number if it exists
			String[] uriRestparams = command.split("=");
			
			//Reading in the incoming request body
			BufferedReader streamReader = new BufferedReader(
					new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
			StringBuilder requestBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null) {
				requestBuilder.append(inputStr);
				requestBuilder.append('\r');
			}
			String requestJson = requestBuilder.toString();
			
			//Setting up response headers
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type",
					"application/x-www-form-urlencoded");
			responseHeaders.set("Content-Language", "en-US");
			
			//Getting the request header to get cookies (object Headers is pretty much just like a map)
			Headers requestheader = exchange.getRequestHeaders();
			
			//Initializing incoming objects from the cookies
			User user = null;
			int gameId = -1;
			String responseStr = "";
			
			//Checking if cookies even exist
			if(requestheader.containsKey("Cookie"))
			{
				List<String> cookies = requestheader.get("Cookie");
				
				//Turning cookies into user object and game id integer
				String[] cookieParts = cookies.get(0).split(" ");
				if(cookieParts.length>=2)
				{
					String userJson =  URLDecoder.decode(cookieParts[0].replace("catan.user=", ""),"UTF-8");
					userJson = userJson.substring(0,userJson.length()-1);
					user = g.fromJson(userJson,User.class);
					gameId = Integer.parseInt(cookieParts[cookieParts.length-1].replace("catan.game=", ""));
				}
				else
				{
					responseStr = "Dont have game cookies, make sure to join a game";
					exchange.sendResponseHeaders(
							HttpURLConnection.HTTP_BAD_REQUEST,
							responseStr.length());
					//set up error response
				}
			}
			else
			{
				responseStr = "Dont have cookies, make sure you login or register";
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_BAD_REQUEST,
						responseStr.length());
				//set up error response
			}
			
			switch (uriRestparams[0]) {
				//No version number situation
				case "model":
					if(user!=null)
					{
						if(facade.userExist(user)&&facade.gameExist(gameId))
						{
							responseStr = g.toJson(facade.getGameModel(gameId));
							exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
									responseStr.length());
						}
						else
						{
							responseStr = "Player or game does not exist";
							exchange.sendResponseHeaders(
									HttpURLConnection.HTTP_BAD_REQUEST,
									responseStr.length());
						}
					}
					break;
				
				//given version number situation
				case "model?version":
					int version = Integer.parseInt(uriRestparams[1]);
					if(user!=null)
					{
						//Checking if the game and user exist
						if(facade.userExist(user)&&facade.gameExist(gameId))
						{
							//Checking the version number
							GameModel currGame = facade.getGameModel(gameId);
							if(currGame.getVersion()!=version)
							{
								responseStr = g.toJson(currGame);
								exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
										responseStr.length());
							}
							else
							{
								responseStr="\"true\"\r";
								exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
										responseStr.length());
							}
						}
						else
						{
							responseStr = "User or game does not exist";
							exchange.sendResponseHeaders(
									HttpURLConnection.HTTP_BAD_REQUEST,
									responseStr.length());
						}
					}				
					break;
					
				default:
					responseStr = "Dont recognize "+ uriRestparams[0]+" command";
					exchange.sendResponseHeaders(
							HttpURLConnection.HTTP_BAD_REQUEST,
							responseStr.length());
			}
			
			
			
			//Writing out to client
			OutputStream response = exchange.getResponseBody();
			response.write(responseStr.getBytes());
			response.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			return;
		}
	}

}
