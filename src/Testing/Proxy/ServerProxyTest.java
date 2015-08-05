package Testing.Proxy;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.ResourceType;
import shared.gameModel.*;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import client.facade.ClientFacade;
import client.serverproxy.ServerProxy;

public class ServerProxyTest {

	private ServerProxy facade;
	private ServerProxy facade2;
	private ServerProxy facade3;
	private ServerProxy facade4;
	private TurnTracker turnTracker;
	private TradeOffer tradeOffer;
	private Player Ife;
	private Player Josh;
	private Player Daniel;
	private Player Paul;

	@Before
	public void setUp() {
		// System.out.println("count: "+ClientFacade.count);
		facade = new ServerProxy("localhost");
		// turnTracker = new TurnTracker();
		// String u = "Ife"+Integer.toString(ClientFacade.count);
		// String p = "testpass";
		// facade.register(u, p);
		// System.out.println("Registered "+u);
		// // facade.login(u, p);
		// facade.createGame(false, false, false, "test");
		// System.out.println("Created game");
		// facade.joinGame(Integer.toString(3+ClientFacade.count), "red");
		// System.out.println("Joined game as "+u);
		// String u2 = "Josh"+Integer.toString(ClientFacade.count);
		// String p2 = "testpass";
		// facade2 = new ServerProxy("localhost");
		// facade2.register(u2, p2);
		// System.out.println("Registered "+u2);
		// // facade2.login(u2, p2);
		// facade2.joinGame(Integer.toString(3+ClientFacade.count), "green");
		// System.out.println("Joined game as "+u2);
		// String u3 = "Daniel"+Integer.toString(ClientFacade.count);
		// String p3 = "testpass";
		// facade3 = new ServerProxy("localhost");
		// facade3.register(u3, p3);
		// System.out.println("Registered "+u3);
		// // facade3.login(u3, p3);
		// facade3.joinGame(Integer.toString(ClientFacade.count), "blue");
		// System.out.println("Joined game as "+u3);
		// String u4 = "Paul"+Integer.toString(ClientFacade.count);
		// String p4 = "testpass";
		// facade4 = new ServerProxy("localhost");
		// facade4.register(u4, p4);
		// // facade4.login(u4, p4);
		// facade4.joinGame(Integer.toString(3+ClientFacade.count), "yellow");
		//
		// ClientFacade.count++;
		//
		// Ife = new Player();
		// Josh = new Player();
		// Daniel = new Player();
		// Paul = new Player();
		//
		// ResourceList ifeResources = new ResourceList(1, 4, 3, 2, 1);
		// ResourceList joshResources = new ResourceList(0, 2, 3, 0, 2);
		// ResourceList danielResources = new ResourceList(0, 4, 0, 1, 0);
		// ResourceList paulResources = new ResourceList(5, 4, 3, 1, 2);
		//
		// Ife.setResources(ifeResources);
		// Josh.setResources(joshResources);
		// Daniel.setResources(danielResources);
		// Paul.setResources(paulResources);
		//
		// ArrayList<Player> playerList = new ArrayList<>();
		//
		// Ife.setPlayerID(0);
		// Josh.setPlayerID(1);
		// Daniel.setPlayerID(2);
		// Paul.setPlayerID(3);
		//
		// playerList.add(Ife);
		// playerList.add(Josh);
		// playerList.add(Daniel);
		// playerList.add(Paul);
		//
		// facade.setPlayers(playerList);
		// facade.setTurnTracker(turnTracker);
	}

	@Test
	public void testSendChat() {
		facade = new ServerProxy("localhost");
		String u = "TestChatUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testChat");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testSendChat");
		facade.sendChat(0, "Hello World");
	}

	@Test
	public void testRollNumber() {
		facade = new ServerProxy("localhost");
		String u = "TestRollUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testRoll");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testRollNumber");
		facade.rollNumber(0, 5);
	}

	@Test
	public void testRoadBuilding() {
		facade = new ServerProxy("localhost");
		String u = "TestRoadBuildingUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testRoadBuilding");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testRoadBuilding");
		HexLocation hexLoc = new HexLocation(0, 0);
		EdgeLocation loc = new EdgeLocation(hexLoc, EdgeDirection.N);
		HexLocation hexLoc2 = new HexLocation(0, 0);
		EdgeLocation loc2 = new EdgeLocation(hexLoc, EdgeDirection.N);
		facade.roadBuilding(0, loc, loc2);
	}

	@Test
	public void testFinishTurn() {
		facade = new ServerProxy("localhost");
		String u = "TestFinishTurnUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testFinishTurn");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testFinishTurn");
		facade.finishTurn(0);
	}

	@Test
	public void testBuyDevCard() {
		facade = new ServerProxy("localhost");
		String u = "testBuyDevCardUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testBuyDevCard");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testBuyDevCard");
		facade.buyDevCard(0);
	}

	@Test
	public void testYearOfPlenty() {
		facade = new ServerProxy("localhost");
		String u = "testYearOfPlentyUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testYearOfPlenty");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testYearOfPlenty");
		facade.yearOfPlenty(0, "wood", "wheat");
	}

	@Test
	public void testSoldier() {
		facade = new ServerProxy("localhost");
		facade2 = new ServerProxy("localhost");
		String u = "TestSoldierUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testSoldier");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		String u2 = "TestSoldierUser2";
		String p2 = "testpass2";
		facade2.register(u2, p2);
		facade2.joinGame(Integer.toString(ClientFacade.count), "yellow");
		ClientFacade.count++;
		System.out.println("testSoldier");

		// Player 1 puts down a house on one side of the (0,0)
		HexLocation hexLoc = new HexLocation(0, 0);
		VertexLocation vertLoc = new VertexLocation(hexLoc, VertexDirection.E);
		facade.buildSettlement(0, vertLoc, true);

		// Player 2 puts down a house on one side of the (0,0)
		HexLocation hexLoc1 = new HexLocation(0, 0);
		VertexLocation vertLoc1 = new VertexLocation(hexLoc1, VertexDirection.W);
		facade2.buildSettlement(1, vertLoc1, true);

		// Both players get some resource to steal
		facade.rollNumber(0, 11);

		facade.soldier(0, 1, new HexLocation(0, 0));
	}

	@Test
	public void testMonopoly() {
		facade = new ServerProxy("localhost");
		String u = "TestMonopolyUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testMonopoly");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testMonopoly");
		facade.monopoly("wood", 0);
	}

	@Test
	public void testMonument() {
		facade = new ServerProxy("localhost");
		String u = "TestMonumentUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testMonument");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		System.out.println("testMonument");
		facade.monument(0);
	}

	// Need to find out what we are gonna do about North(what the given code
	// has) and N what the server expects
	@Test
	public void testBuildRoad() {
		facade = new ServerProxy("localhost");
		String u = "TestBuildRoadUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testBuildRoad");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testBuildRoad");
		HexLocation hexLoc = new HexLocation(0, 0);
		EdgeLocation loc = new EdgeLocation(hexLoc, EdgeDirection.N);
		facade.buildRoad(0, loc, true);
	}

	@Test
	public void testBuildSettlement() {
		facade = new ServerProxy("localhost");
		String u = "TestBuildSettlementUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testBuildSettlement");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testBuildSettlement");
		HexLocation hexLoc = new HexLocation(0, 0);
		VertexLocation vertLoc = new VertexLocation(hexLoc, VertexDirection.E);
		facade.buildSettlement(0, vertLoc, true);
	}

	@Test
	public void testBuildCity() {
		facade = new ServerProxy("localhost");
		String u = "TestBuildCityUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testBuildCity");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testBuildCity");
		HexLocation hexLoc = new HexLocation(0, 0);
		VertexLocation vertLoc = new VertexLocation(hexLoc, VertexDirection.NE);
		facade.buildCity(0, vertLoc);
	}

	@Test
	public void testOfferTrade() {
		facade = new ServerProxy("localhost");
		facade2 = new ServerProxy("localhost");
		String u = "TestOfferTradeUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testOfferTrade");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		String u2 = "TestOfferTradeUser2";
		String p2 = "testpass2";
		facade2.register(u2, p2);
		facade2.joinGame(Integer.toString(ClientFacade.count), "yellow");
		ClientFacade.count++;
		System.out.println("testOfferTrade");
		ResourceList offer = new ResourceList(1, -4, 3, -2, 1);
		facade.offerTrade(0, offer, 1);
	}

	@Test
	public void testAcceptTrade() {
		facade = new ServerProxy("localhost");
		facade2 = new ServerProxy("localhost");
		String u = "TestAcceptTradeUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testAcceptTrade");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		String u2 = "TestAcceptTradeUser2";
		String p2 = "testpass2";
		facade2.register(u2, p2);
		facade2.joinGame(Integer.toString(ClientFacade.count), "yellow");
		ClientFacade.count++;
		// Need to have a trade offered before you can accept it or reject it
		System.out.println("testAcceptTrade");
		ResourceList offer = new ResourceList(132, -465, 348, -298, 141);
		facade.offerTrade(0, offer, 1);
		facade2.acceptTrade(1, true);
	}

	@Test
	public void testDiscardCards() {
		facade = new ServerProxy("localhost");
		String u = "TestDiscardCardsUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testDiscardCards");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testDiscardCards");
		ResourceList discardedCards = new ResourceList(1, 1, 1, 1, 1);
		facade.discardCards(0, discardedCards);
	}

	@Test
	public void testGetClientModel() {
		facade = new ServerProxy("localhost");
		String u = "TestGetClientModelUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testClientModelChat");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		System.out.println("testGetClientModel");
		facade.getClientModel(0);
	}

	@Test
	public void testRobPlayer() {
		facade = new ServerProxy("localhost");
		facade2 = new ServerProxy("localhost");
		String u = "TestRobUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testRob");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		String u2 = "TestRobUser2";
		String p2 = "testpass2";
		facade2.register(u2, p2);
		facade2.joinGame(Integer.toString(ClientFacade.count), "yellow");
		ClientFacade.count++;
		System.out.println("testRobPlayer");

		// Player 1 puts down a house on one side of the (0,0)
		HexLocation hexLoc = new HexLocation(0, 0);
		VertexLocation vertLoc = new VertexLocation(hexLoc, VertexDirection.E);
		facade.buildSettlement(0, vertLoc, true);

		// Player 2 puts down a house on one side of the (0,0)
		HexLocation hexLoc1 = new HexLocation(0, 0);
		VertexLocation vertLoc1 = new VertexLocation(hexLoc1, VertexDirection.W);
		facade2.buildSettlement(1, vertLoc1, true);

		// Both players get some resource to steal
		facade.rollNumber(0, 11);

		facade.robPlayer(0, 1, new HexLocation(0, 0));
	}

	@Test
	public void maritimeTrade() {
		facade = new ServerProxy("localhost");
		String u = "TestMaritimeTradeUser";
		String p = "testpass";
		facade.register(u, p);
		facade.createGame(false, false, false, "testMaritimeTrade");
		facade.joinGame(Integer.toString(ClientFacade.count), "red");
		ClientFacade.count++;
		// Make sure that the resources you ask for are real
		System.out.println("maritimeTrade");
		facade.maritimeTrade(0, 2, "wood", "sheep");
	}

	@Test
	public void login() {
		facade = new ServerProxy("localhost");
		String u = "TestLoginUser";
		String p = "testpass";
		facade.register(u, p);
		System.out.println("login");
		facade.login(u, p);
	}

}
