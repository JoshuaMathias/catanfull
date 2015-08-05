package Testing.Poller;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TradeOffer;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import client.facade.ClientFacade;
import client.poller.ServerPoller.updateTask;

public class ServerPollerTest {
	private ClientFacade facade;
	private ClientFacade facade2;
	private ClientFacade facade3;
	private ClientFacade facade4;
	private TurnTracker turnTracker;
	private TradeOffer tradeOffer;
	private Player Ife;
	private Player Josh;
	private Player Daniel;
	private Player Paul;
	
	@Before 
	public void setUp() {
//		facade = new Facade("localhost");
//		facade.createGame(true, true, true, "test");
//		turnTracker = new TurnTracker();
//
//		String u = "Ife"+Integer.toString(Facade.count);
//		String p = "testpass";
//		facade.register(u, p);
//		facade.login(u, p);
//		facade.joinGame(Integer.toString(3+Facade.count), "red");
//
//		String u2 = "Josh"+Integer.toString(Facade.count);
//		String p2 = "testpass";
//		facade2 = new Facade("localhost");
//		facade2.register(u2, p2);
//		facade2.login(u2, p2);
//		facade2.joinGame(Integer.toString(3+Facade.count), "green");
//
//		String u3 = "Daniel"+Integer.toString(Facade.count);
//		String p3 = "testpass";
//		facade3 = new Facade("localhost");
//		facade3.register(u3, p3);
//		facade3.login(u3, p3);
//		facade3.joinGame(Integer.toString(3+Facade.count), "blue");
//
//		String u4 = "Paul"+Integer.toString(Facade.count);
//		String p4 = "testpass";
//		facade4 = new Facade("localhost");
//		facade4.register(u4, p4);
//		facade4.login(u4, p4);
//		facade4.joinGame(Integer.toString(3+Facade.count), "yellow");
//		
//		Facade.count++;
//		
//		Ife = new Player();
//		Josh = new Player();
//		Daniel = new Player();
//		Paul = new Player();
//
//		ResourceList ifeResources = new ResourceList(1, 4, 3, 2, 1);
//		ResourceList joshResources = new ResourceList(0, 2, 3, 0, 2);
//		ResourceList danielResources = new ResourceList(0, 4, 0, 1, 0);
//		ResourceList paulResources = new ResourceList(5, 4, 3, 1, 2);
//
//		Ife.setResources(ifeResources);
//		Josh.setResources(joshResources);
//		Daniel.setResources(danielResources);
//		Paul.setResources(paulResources);
//
//		ArrayList<Player> playerList = new ArrayList<>();
//
//		Ife.setPlayerID(0);
//		Josh.setPlayerID(1);
//		Daniel.setPlayerID(2);
//		Paul.setPlayerID(3);
//
//		playerList.add(Ife);
//		playerList.add(Josh);
//		playerList.add(Daniel);
//		playerList.add(Paul);
//
//		facade.setPlayers(playerList);
//		facade.setTurnTracker(turnTracker);
	}
	
	@Test
	public void testPoller() {
		System.out.println("testPoller");
//		facade.getPoller().getClientModel();
//		ClientModel model=facade.getModel();
//		Timer timer=new Timer();
//		assertTrue(facade.getModel().getWinner()==-1);
//		HexLocation hexLoc = new HexLocation(0, 0);
//		VertexLocation vertLoc = new VertexLocation(hexLoc,
//				VertexDirection.NorthEast);
//		facade.buildCity(0, vertLoc);
//		assertTrue(facade.getModel().getPlayers().get(0).getResources().getOre()==0);
//		try {
//			Thread.sleep(1100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		assertTrue(facade.getModel().getPlayers().get(0).getResources().getOre()==-3);
	}
	
	@After
	public void tearDown() {
		return;
	}
}
