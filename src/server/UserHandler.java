package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.params.LoginParams;
import shared.params.RegisterParams;
import Testing.Proxy.ServerFacadeTest;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserHandler implements HttpHandler {
	public static boolean test = false;
	
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String[] commandList = exchange.getRequestURI().toString().split("\\/");
		String command = "";
		if (commandList.length > 2) {
			command = commandList[2];
		}
		try {
			System.out.println("UserHandler called");
			Gson g = new Gson();
			System.out.println("Command: " + command);
			IServerFacade facade = null;
			if (test) {
				facade = ServerFacadeTest.getSingleton();
			} else {
				facade = ServerFacade.getSingleton();
			}
			// JsonReader reader = new JsonReader(new InputStreamReader(
			// exchange.getRequestBody(), "UTF-8"));
			BufferedReader streamReader = new BufferedReader(
					new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
			StringBuilder requestBuilder = new StringBuilder();
			String inputStr;
			String responseStr = "";
			while ((inputStr = streamReader.readLine()) != null) {
				requestBuilder.append(inputStr);
				requestBuilder.append('\r');
			}
			String requestJson = requestBuilder.toString();
			String username = "";
			String password = "";
			int userID = -1;
			streamReader.close();
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type",
					"application/x-www-form-urlencoded");
			responseHeaders.set("Content-Language", "en-US");
			switch (command) {
			case "register":
				System.out.println("Register handler");
				RegisterParams params = g.fromJson(requestJson,
						RegisterParams.class);
				username = params.getUsername();
				password = params.getPassword();
				userID = facade.register(username, password);
				if (userID == -1) {
					System.out.println("Failed to register - someone already has that username.");
					responseStr = "Failed to register - someone already has that username.";
					exchange.sendResponseHeaders(
							HttpURLConnection.HTTP_BAD_REQUEST,
							responseStr.length());
				}
				break;
			case "login":
				System.out.println("Login handler");
				LoginParams lParams = g
						.fromJson(requestJson, LoginParams.class);
				username = lParams.getUsername();
				password = lParams.getPassword();
				userID = facade.logIn(username, password);
				if (userID == -1) {
					responseStr = "Failed to login - bad username or password.";
					exchange.sendResponseHeaders(
							HttpURLConnection.HTTP_BAD_REQUEST,
							responseStr.length());
				}
				break;
			}

			if (responseStr.equals("")) {
				List<String> values = new ArrayList<>();
				String cookieString = "{\"name\":\"" + username
						+ "\",\"password\":\"" + password + "\",\"playerID\":"
						+ userID + "}";
				System.out.println("catan.user="+URLEncoder.encode(cookieString, "UTF-8")+";Path=/;");
				values.add("catan.user="+URLEncoder.encode(cookieString, "UTF-8")+";Path=/;");
				
				responseHeaders.put("Set-Cookie", values);
				responseStr = "Success";
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
						responseStr.length());
			}
			OutputStream response = exchange.getResponseBody();
			response.write(responseStr.getBytes());
			response.close();
		} catch (Exception e) {
			System.out.println("Server error on command " + command);
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			return;
		}
	}

}
