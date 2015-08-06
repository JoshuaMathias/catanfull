package server;

import java.io.IOException;
import java.net.InetSocketAddress;

import server.facade.ServerFacade;

import com.sun.net.httpserver.HttpServer;

/**
 * The server
 * 
 * @author Ife's Group
 * 
 */
public class Server {
	private static int SERVER_PORT_NUMBER = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private HttpServer server;
	private GameHandler gameHandler = new GameHandler();
	private GamesHandler gamesHandler = new GamesHandler();
	private MovesHandler movesHandler = new MovesHandler();
	private UserHandler userHandler = new UserHandler();

	private Server() {
		return;
	}

	private void run() {

		try {
			server = HttpServer.create(
					new InetSocketAddress(SERVER_PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			System.out.println("Could not create HTTP server: "
					+ e.getMessage());
			e.printStackTrace();
			return;
		}

		server.setExecutor(null); // use the default executor

		server.createContext("/game", gameHandler);
		server.createContext("/games", gamesHandler);
		server.createContext("/moves", movesHandler);
		server.createContext("/user", userHandler);
		server.createContext("/docs/api/data", new Handlers.JSONAppender("")); 
		server.createContext("/docs/api/view", new Handlers.BasicFile("")); 

		server.start();
	}
	
	public void setPort(String port) {
		SERVER_PORT_NUMBER = Integer.parseInt(port);
	}

	public int getPort() {
		return SERVER_PORT_NUMBER;
	}
	
	public static void main(String[] args) {
		System.out.println("Started server");
		if (args.length > 0 && args[0] != null) {
			SERVER_PORT_NUMBER = Integer.parseInt((args[0]));
			if (args.length > 1 && args[1] != null) {
				if (args[1].equals("test")) {
					UserHandler.test=true;
					GameHandler.test=true;
					GamesHandler.test=true;
					MovesHandler.test=true;
					if (args.length > 2 && args[2] != null) {
						ServerFacade.storageType=args[2];
					}
				} else {
					ServerFacade.storageType=args[1].toLowerCase();
				}
			}

		}
		new Server().run();
	}
}
