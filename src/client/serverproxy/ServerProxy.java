package client.serverproxy;

import com.google.gson.Gson;

import server.IServer;
import shared.gameModel.GameModel;
import shared.gameModel.ResourceList;
import shared.locations.HexLocation;
import shared.params.AcceptTradeParams;
import shared.params.BuildCityParams;
import shared.params.BuildRoadParams;
import shared.params.BuildSettlementParams;
import shared.params.BuyDevCardParams;
import shared.params.CreateGamesParams;
import shared.params.DiscardCardsParams;
import shared.params.FinishTurnParams;
import shared.params.JoinGameParams;
import shared.params.LoginParams;
import shared.params.MaritimeTradeParams;
import shared.params.MonopolyParams;
import shared.params.MonumentParams;
import shared.params.OfferTradeParams;
import shared.params.RegisterParams;
import shared.params.RoadBuildingParams;
import shared.params.RobPlayerParams;
import shared.params.RollNumberParams;
import shared.params.SendChatParams;
import shared.params.SoldierParams;
import shared.params.YearOfPlentyParams;

/**
 * Proxy Server for the Client to interact with the Server. Packages information into objects and strings for the ClientCommunicator to send to the Server
 * @author Ife's Group
 *
 */
public class ServerProxy implements IServer 
{

	//Some server functions might have to return booleans because the board must be in a certain in order to execute
	//certain commands. Like for example the server will not do the robPlayer command unless the victim has a house
	//on the targeted hex, at least one resource has to be present in the victims hand, and the hex location has to
	//exist. I know that our cando functions are checking these but some rules may slip through.
	
	private Gson g = new Gson();
	private String hostname = "";
	private ClientCommunicator clientComm;
	private boolean startPolling = false;
	
	public ServerProxy(String hostname)
	{
		this.hostname = hostname;
		clientComm = new ClientCommunicator (hostname);
	}
	
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param number
	 * @pre playerIndex and number != null, number between 2 and 12 inclusive, playerIndex between 0 and 3 inclusive
	 * @post Server receives information
	 */
	public void rollNumber(int playerIndex, int number) 
	{
		RollNumberParams rnp = new RollNumberParams();
		rnp.setNumber(number);
		rnp.setPlayerIndex(playerIndex);
		String input = g.toJson(rnp);
		clientComm.send("moves/rollNumber", input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param spot1
	 * @param spot2
	 * @pre playerIndex between 0 and 3 inclusive, playerIndex and spot1 and spot2 are not null
	 * @post Server receives information
	 */
	public void roadBuilding(int playerIndex, shared.locations.EdgeLocation spot1, shared.locations.EdgeLocation spot2) 
	{
		client.serverproxy.EdgeLocation rl1 = new client.serverproxy.EdgeLocation(spot1.getHexLoc().getX(),
				spot1.getHexLoc().getY(),spot1.getDir());
		
		client.serverproxy.EdgeLocation rl2 = new client.serverproxy.EdgeLocation(spot2.getHexLoc().getX(),
				spot2.getHexLoc().getY(),spot2.getDir());
		
		RoadBuildingParams roadbuilding = new RoadBuildingParams();
		roadbuilding.setPlayerIndex(playerIndex);
		roadbuilding.setSpot1(rl1);
		roadbuilding.setSpot2(rl2);
		String input = g.toJson(roadbuilding);
		clientComm.send("moves/Road_Building", input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @pre playerIndex between 0 and 3 inclusive and not null
	 * @post Server receives information
	 */
	public void finishTurn(int playerIndex) 
	{
		FinishTurnParams finishturn = new FinishTurnParams ();
		finishturn.setPlayerIndex(playerIndex);
		String input = g.toJson(finishturn);
		clientComm.send("moves/finishTurn",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @pre playerIndex between 0 and 3 inclusive and not null
	 * @post Server receives information
	 */
	public void buyDevCard(int playerIndex) 
	{
		BuyDevCardParams buydevcard = new BuyDevCardParams();
		buydevcard.setPlayerIndex(playerIndex);
		String input = g.toJson(buydevcard);
		clientComm.send("moves/buyDevCard",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param resource1
	 * @param resource2
	 * @pre playerIndex between 0 and 3 inclusive and not null, both resources must not be null and one of the key words for resources
	 * @post Server receives information
	 */
	public void yearOfPlenty(int playerIndex, String resource1, String resource2) 
	{
		YearOfPlentyParams yearofplenty = new YearOfPlentyParams ();
		yearofplenty.setPlayerIndex(playerIndex);
		yearofplenty.setResource1(resource1);
		yearofplenty.setResource2(resource2);
		String input = g.toJson(yearofplenty);
		clientComm.send("moves/Year_of_Plenty",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param victimIndex
	 * @param location
	 * @pre playerIndex and victinIndex between 0 and 3 inclusive and not null, location not null
	 * @post Server receives information
	 */
	public void soldier(int playerIndex, int victimIndex, HexLocation location) 
	{
		SoldierParams soldier = new SoldierParams();
		soldier.setPlayerIndex(playerIndex);
		soldier.setVictimIndex(victimIndex);
		soldier.setLocation(location);
		String input = g.toJson(soldier);
		clientComm.send("moves/Soldier",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param resource
	 * @param playerIndex
	 * @pre resource must not be null and one of the key words for resources, playerIndex between 0 and 3 inclusive and not null
	 * @post Server receives information
	 */
	public void monopoly(String resource, int playerIndex) 
	{
		MonopolyParams monopoly = new MonopolyParams ();
		monopoly.setPlayerIndex(playerIndex);
		monopoly.setResource(resource);
		String input = g.toJson(monopoly);
		clientComm.send("moves/Monopoly", input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param roadLocation
	 * @param free
	 * @pre playerIndex between 0 and 3 inclusive and not null, roadLocation not null
	 * @post Server receives information
	 */
	public void buildRoad(int playerIndex, shared.locations.EdgeLocation roadLocation, boolean free) 
	{
		BuildRoadParams buildroad = new BuildRoadParams();
		buildroad.setPlayerIndex(playerIndex);
		client.serverproxy.EdgeLocation rl = new client.serverproxy.EdgeLocation(roadLocation.getHexLoc().getX(),
				roadLocation.getHexLoc().getY(),roadLocation.getDir());
		buildroad.setRoadLocation(rl);
		buildroad.setFree(free);
		String input = g.toJson(buildroad);
		clientComm.send("moves/buildRoad",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param vertexLocation
	 * @param free
	 * @pre playerIndex between 0 and 3 inclusive and not null, vertexLocation not null
	 * @post Server receives information
	 */
	public void buildSettlement(int playerIndex, shared.locations.VertexLocation vertexLocation, boolean free)
	{
		BuildSettlementParams buildsettlement = new BuildSettlementParams ();
		buildsettlement.setPlayerIndex(playerIndex);
		buildsettlement.setFree(free);
		client.serverproxy.VertexLocation vl = new client.serverproxy.VertexLocation(vertexLocation.getHexLoc().getX(),
				vertexLocation.getHexLoc().getY(),vertexLocation.getDir());
		
		buildsettlement.setVertexLocation(vl);
		String input = g.toJson(buildsettlement);
		clientComm.send("moves/buildSettlement", input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param vertexLocation
	 * @param free
	 * @pre playerIndex between 0 and 3 inclusive and not null, vertexLocation not null
	 * @post Server receives information
	 */
	public void buildCity(int playerIndex, shared.locations.VertexLocation vertexLocation) 
	{
		BuildCityParams buildcity = new BuildCityParams();
		buildcity.setPlayerIndex(playerIndex);
		client.serverproxy.VertexLocation vl = new client.serverproxy.VertexLocation(vertexLocation.getHexLoc().getX(),
				vertexLocation.getHexLoc().getY(),vertexLocation.getDir());
		buildcity.setVertexLocation(vl);
		String input = g.toJson(buildcity);
		clientComm.send("moves/buildCity",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param offer
	 * @param receiver
	 * @pre playerIndex and receiver between 0 and 3 inclusive and not null, offer not null
	 * @post Server receives information
	 */
	public void offerTrade(int playerIndex, ResourceList offer, int receiver) 
	{
		OfferTradeParams offertrade = new OfferTradeParams();
		offertrade.setOffer(offer);
		offertrade.setPlayerIndex(playerIndex);
		offertrade.setReceiver(receiver);
		String input = g.toJson(offertrade);
		clientComm.send("moves/offerTrade",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param willAccept
	 * @pre playerIndex between 0 and 3 inclusive and not null
	 * @post Server receives information
	 */
	public void acceptTrade(int playerIndex, boolean willAccept) 
	{
		AcceptTradeParams accepttrade = new AcceptTradeParams();
		accepttrade.setPlayerIndex(playerIndex);
		accepttrade.setWillAccept(willAccept);
		String input = g.toJson(accepttrade);
		clientComm.send("moves/acceptTrade",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @param playerIndex
	 * @param discardedCards
	 * @pre playerIndex between 0 and 3 inclusive and not null, discardedCards not null
	 * @post Server receives information
	 */
	public void discardCards(int playerIndex, ResourceList discardedCards) 
	{
		DiscardCardsParams discardcards = new DiscardCardsParams();
		discardcards.setDiscardedCards(discardedCards);
		discardcards.setPlayerIndex(playerIndex);
		String input = g.toJson(discardcards);
		clientComm.send("moves/discardCards",input);
	}
	
	public void monument(int playerIndex )
	{
		MonumentParams monument = new MonumentParams ();
		monument.setPlayerIndex(playerIndex);
		String input = g.toJson(monument);
		clientComm.send("moves/Monument", input);
	}
	
	public void sendChat(int playerIndex, String content)
	{
		SendChatParams sendchat = new SendChatParams();
		sendchat.setContent(content);
		sendchat.setPlayerIndex(playerIndex);
		String input = g.toJson(sendchat);
		clientComm.send("moves/sendChat",input);
	}
	
	public void maritimeTrade(int playerIndex, int ratio, String inputResource, String outputResource)
	{
		MaritimeTradeParams maritime = new MaritimeTradeParams();
		maritime.setPlayerIndex(playerIndex);
		maritime.setInputResource(inputResource);
		maritime.setOutputResource(outputResource);
		maritime.setRatio(ratio);
		String input = g.toJson(maritime);
		clientComm.send("moves/maritimeTrade",input);
	}
	
	public void robPlayer(int playerIndex, int victimIndex, HexLocation location)
	{
		RobPlayerParams robber = new RobPlayerParams();
		robber.setLocation(location);
		robber.setPlayerIndex(playerIndex);
		robber.setVictimIndex(victimIndex);
		String input = g.toJson(robber);
		System.out.println("Json of rob player that is going in: "+input);
		clientComm.send("moves/robPlayer",input);
	}
	
	/**
	 * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
	 * @pre none
	 * @post Server receives information
	 */
	public GameModel getClientModel(int version) 
	{
		GameModel newclient = null;
		String JsonClient = clientComm.send("game/model?version="+version, "");
		if(!JsonClient.equals("\"true\"\r")&&!JsonClient.equals("400"))
		{
			newclient = g.fromJson(JsonClient, GameModel.class);
		}
		else if(JsonClient.equals("400"))
		{
			return getClientModel(version);
		}
		return newclient;
	}
	
	//----------------------------------------------SETTING COOKIES---------------------------------------------------//
	
	
	public boolean register(String username, String password)
	{
		boolean result = false;
		RegisterParams register = new RegisterParams(username,password);
		String input = g.toJson(register);
		String check = clientComm.send("user/register", input);
		if(!check.equals("400"))
		{
			result = true;
		}
		return result;
	}
	
	public boolean login(String username, String password)
	{
		boolean result = false;
		LoginParams login = new LoginParams(username,password);
		String input = g.toJson(login);
		String check = clientComm.send("user/login",input);
		if(!check.equals("400"))
		{
			result = true;
		}
		return result;
	}
	
	public void createGame(boolean randomTiles,boolean randomNumbers,boolean randomPorts, String gameName)
	{
		CreateGamesParams creategame = new CreateGamesParams(randomTiles, randomNumbers, randomPorts, gameName);
		String input = g.toJson(creategame);
		clientComm.send("games/create", input);
	}
	
	public boolean joinGame(String gameId, String color)
	{
		boolean result = false;
		JoinGameParams joingame = new JoinGameParams(gameId,color);
		String input = g.toJson(joingame);
		String check = clientComm.send("games/join", input);
		if(!check.equals("400"))
		{
			result = true;
			startPolling = true;
		}
		return result;
	}
	
	//Because the server doesn't return the player's index we will have to determine each player's index in the game
	//Most likely by order they are listed in the arraylist of players in the gameinfo object
	public GamesList getGamesList()
	{
		GamesList result = new GamesList(); 
		String Jsonoutput = clientComm.send("games/list","");
		if(!Jsonoutput.isEmpty()&&!Jsonoutput.equals("400"))
		{
			Jsonoutput = "{\"games\":"+Jsonoutput+"}";
			result = g.fromJson(Jsonoutput, GamesList.class);
		}
		else
		{
			return getGamesList();
		}
		return result;
	}
	
	public boolean gotCookies()
	{
		return startPolling;	
	}

	//Add a couple of functions from the server
	//Remember to set up the game before making certain calls
	//Make serverProxy and clientcommunicator a singleton
	//Fix serverproxy interface and implement it
	//Check the equals function for the clientmodel
	
//	public static void main(String arg[])
//	{
////		String u = "ogeorge";
////		String p = "cookies";
////
////		String u1 = "ogeorge1";
////		String p1 = "cookies1";
////		
////		String u2 = "ogeorge2";
////		String p2 = "cookies2";
////		
////		String u3 = "ogeorge3";
////		String p3 = "cookies3";
////		
////		ServerProxy server = new ServerProxy("longbow");
////		server.register(u, p);server.createGame(true,true,true,"test");
////		server.joinGame("3", "red");
////
////		//server.login(u, p);
////		
////		ServerProxy server1 = new ServerProxy("longbow");
////		server1.register(u1, p1);
////		server1.joinGame("3", "blue");
////		//server1.login(u, p);
////		
////		ServerProxy server2 = new ServerProxy("longbow");
////		server2.register(u2, p2);		
////		server2.joinGame("3", "green");
////		//server2.login(u, p);
////		
////		ServerProxy server3 = new ServerProxy("longbow");
////		server3.register(u3, p3);
////		server3.joinGame("3", "yellow");
////		//server3.login(u, p);
////		
////		ClientModel c = server.getClientModel(-1);
////
////		ResourceList ifes = new ResourceList (100,-400,200,-300,100);
////		server.offerTrade(0, ifes , 1);
////		server1.acceptTrade(1, true);
////		
////		server.getClientModel(2);				//Will return null because of version number
//		
//		ServerProxy server = new ServerProxy("longbow");
//
//		GamesList gl= server.getGamesList();
//		
////		System.out.println(gl.getGames().get(0).getPlayers().get(0).getColorEnum());
//	}

}
