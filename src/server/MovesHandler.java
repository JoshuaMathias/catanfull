package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.List;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;
import shared.params.*;
import shared.definitions.*;
import shared.locations.*;
import Testing.Proxy.ServerFacadeTest;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MovesHandler implements HttpHandler {
	public static boolean test = false;
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("MovesHandler called");

		// Setting up the things that I will use later
		IServerFacade facade = null;
		if (test) {
			facade = ServerFacadeTest.getSingleton();
		} else {
			facade = ServerFacade.getSingleton();
		}
		Gson g = new Gson();
		boolean successful=true;
		try {
			// Taking the command URI
			String[] commandList = exchange.getRequestURI().toString()
					.split("\\/");
			String command = "";
			if (commandList.length > 2) {
				command = commandList[2];
			}
			System.out.println("Command: " + command);

			// Reading in the incoming request body
			BufferedReader streamReader = new BufferedReader(
					new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
			StringBuilder requestBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null) {
				requestBuilder.append(inputStr);
				requestBuilder.append('\r');
			}
			String requestJson = requestBuilder.toString();

			// Setting up response headers
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type",
					"application/x-www-form-urlencoded");
			responseHeaders.set("Content-Language", "en-US");

			// Getting the request header to get cookies (object Headers is
			// pretty much just like a map)
			Headers requestheader = exchange.getRequestHeaders();

			// Initializing incoming objects from the cookies
			User user = null;
			int gameId = -1;
			String responseStr = "";

			// Checking if cookies even exist
			if (requestheader.containsKey("Cookie")) {
				List<String> cookies = requestheader.get("Cookie");

				// Turning cookies into user object and game id integer
				String[] cookieParts = cookies.get(0).split(" ");
				if (cookieParts.length == 2) {
					String userJson = URLDecoder.decode(
							cookieParts[0].replace("catan.user=", ""), "UTF-8");
					System.out.println("user cookie: "+userJson);
					userJson = userJson.substring(0, userJson.length() - 1);
					user = g.fromJson(userJson, User.class);
					gameId = Integer.parseInt(cookieParts[1].replace(
							"catan.game=", ""));
				} else {
					responseStr = "Don't have game cookies. Make sure to join a game";
					successful = false;
					// set up error response
				}
			} else {
				responseStr = "Don't have cookies. Make sure you login or register";
				successful = false;
				// set up error response
			}

			switch (command) {

			case "sendChat":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						SendChatParams sendChatParams = g.fromJson(requestJson,
								SendChatParams.class);
						facade.sendChat(sendChatParams.getPlayerIndex(), sendChatParams.getContent(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "rollNumber":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						RollNumberParams rollNumberParams = g.fromJson(requestJson,
								RollNumberParams.class);
						facade.rollNumber(rollNumberParams.getPlayerIndex(), rollNumberParams.getNumber(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "robPlayer":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						RobPlayerParams robPlayerParams = g.fromJson(requestJson,
								RobPlayerParams.class);
						facade.robPlayer(robPlayerParams.getPlayerIndex(), robPlayerParams.getVictimIndex(), robPlayerParams.getLocation(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "finishTurn":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
//						FinishTurnParams finishTurnParams = g.fromJson(requestJson,
//								FinishTurnParams.class); // FinishTurnParams isn't needed.
						facade.finishTurn(gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "buyDevCard":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						BuyDevCardParams buyDevCardParams = g.fromJson(requestJson,
								BuyDevCardParams.class);
						facade.buyDevCard(buyDevCardParams.getPlayerIndex(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "Year_of_Plenty":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						YearOfPlentyParams yearOfPlentyParams = g.fromJson(requestJson,
								YearOfPlentyParams.class);
						facade.yearOfPlenty(yearOfPlentyParams.getPlayerIndex(), yearOfPlentyParams.getResource1(), yearOfPlentyParams.getResource2(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "Road_Building":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						RoadBuildingParams roadBuildingParams = g.fromJson(requestJson,
								RoadBuildingParams.class);
						//Convert serverproxy EdgeLocation to shared EdgeLocation for the model functions.
						facade.roadBuilding(roadBuildingParams.getPlayerIndex(), new EdgeLocation(roadBuildingParams.getSpot1()), new EdgeLocation(roadBuildingParams.getSpot2()), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "Soldier":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						SoldierParams soldierParams = g.fromJson(requestJson,
								SoldierParams.class);
						facade.soldier(soldierParams.getPlayerIndex(), soldierParams.getVictimIndex(), soldierParams.getLocation(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "Monopoly":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						MonopolyParams monopolyParams = g.fromJson(requestJson,
								MonopolyParams.class);
						facade.monopoly(monopolyParams.getPlayerIndex(), monopolyParams.getResource(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "Monument":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						MonumentParams sendChatParams = g.fromJson(requestJson,
								MonumentParams.class);
						facade.monument(sendChatParams.getPlayerIndex(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "buildRoad":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						BuildRoadParams buildRoadParams = g.fromJson(requestJson,
								BuildRoadParams.class);
						facade.buildRoad(buildRoadParams.getPlayerIndex(), new EdgeLocation(buildRoadParams.getRoadLocation()), buildRoadParams.isFree(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "buildSettlement":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						BuildSettlementParams buildSettlementParams = g.fromJson(requestJson,
								BuildSettlementParams.class);
						facade.buildSettlement(buildSettlementParams.getPlayerIndex(), new VertexLocation(buildSettlementParams.getVertexLocation()), buildSettlementParams.isFree(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "buildCity":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						BuildCityParams buildCityParams = g.fromJson(requestJson,
								BuildCityParams.class);
						facade.buildCity(buildCityParams.getPlayerIndex(), new VertexLocation(buildCityParams.getVertexLocation()), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "offerTrade":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						OfferTradeParams offerTradeParams = g.fromJson(requestJson,
								OfferTradeParams.class);
						facade.offerTrade(offerTradeParams.getPlayerIndex(), offerTradeParams.getOffer(), offerTradeParams.getReceiver(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "acceptTrade":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						AcceptTradeParams acceptTradeParams = g.fromJson(requestJson,
								AcceptTradeParams.class);
						facade.acceptTrade(acceptTradeParams.getPlayerIndex(), acceptTradeParams.isWillAccept(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "maritimeTrade":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						MaritimeTradeParams maritimeTradeParams = g.fromJson(requestJson,
								MaritimeTradeParams.class);
						facade.maritimeTrade(maritimeTradeParams.getPlayerIndex(), maritimeTradeParams.getRatio(), maritimeTradeParams.getInputResource(), maritimeTradeParams.getOutputResource(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			case "discardCards":
				if (user != null) {
					if (facade.userExist(user) && facade.gameExist(gameId)) {
						DiscardCardsParams discardCardsParams = g.fromJson(requestJson,
								DiscardCardsParams.class);
						facade.discardCards(discardCardsParams.getPlayerIndex(), discardCardsParams.getDiscardedCards(), gameId);
					} else {
						responseStr = "Failed";
						successful = false;
					}
				}
				break;
			default:
				responseStr = "Don't recognize command " + command;
				successful = false;
			}
			
			// Send response code. By default, successful is true.
			if (successful) {
				responseStr=g.toJson(facade.getGameModel(gameId));
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
						responseStr.length());
			} else {
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_BAD_REQUEST,
						responseStr.length());
			}
			// Writing out to client
			OutputStream response = exchange.getResponseBody();
			response.write(responseStr.getBytes());
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
			String responseStr="Exception: "+e.getMessage();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, responseStr.length());
			return;
		}
	}

}