package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class RoadBuildingCommand {

	private IServerFacade serverFacade;
	private Player paul = new Player();
	private Player daniel = new Player();
	private Player ife = new Player();
	private Player josh = new Player();
	private GameModel game;
	private TurnTracker turnTracker;
	private ResourceList bank;
	private ArrayList<Player> players;
	
	@Before 
	public void setUp() {
		
		serverFacade = ServerFacade.getSingleton();
	
		paul.setPlayerIndex(0);
		daniel.setPlayerIndex(1);
		ife.setPlayerIndex(2);
		josh.setPlayerIndex(3);
		
		players = new ArrayList<>();
		players.add(paul);
		players.add(daniel);
		players.add(ife);
		players.add(josh);
		
		ResourceList paulsResources = new ResourceList(5,5,5,5,5);
		ResourceList danielsResources = new ResourceList(9,4,0,0,1);
		ResourceList ifesResources = new ResourceList(0,0,0,1,2);
		ResourceList joshsResources = new ResourceList(8,4,5,6,7);
		
		paul.setResources(paulsResources);
		daniel.setResources(danielsResources);
		ife.setResources(ifesResources);
		josh.setResources(joshsResources);
		
		paul.setName("paul");
		daniel.setName("daniel");
		ife.setName("ife");
		josh.setName("josh");
		
		turnTracker = new TurnTracker();
		turnTracker.setStatus("Playing");
		turnTracker.setCurrentTurn(0);
		
		serverFacade.createGame(false, false, false, "default game");
		
		game = serverFacade.getGameModel(0);
		game.setPlayers(players);
		game.setTurnTracker(turnTracker);
		
		
		ArrayList<VertexObject> settlements = new ArrayList<>();
		VertexObject paulsSettlement = new VertexObject();
		paulsSettlement.setOwner(0);
		paulsSettlement.setLocation(new VertexLocation(new HexLocation(3,-3), VertexDirection.NE));
		settlements.add(paulsSettlement);
		game.getMap().setSettlements(settlements);
		
		bank = new ResourceList(15,15,15,15,15);
		game.setBank(bank);
		game.setGameID(0);
		
//		serverFacade.addGameToList(game);
	}
	
	@After
	public void tearDown() {
		ServerFacade.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
		
		DevCardList oldDevCards = new DevCardList(0,0,1,0,0);
		
		paul.setPlayerID(0);
		paul.setPlayerIndex(0);
		paul.setRoads(15);
		paul.setOldDevCards(oldDevCards);
		EdgeLocation road1 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.N);
		EdgeLocation road2 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.NE);
		System.out.println(players.toString());
		serverFacade.roadBuilding(0, road1, road2, 0);
		System.out.println(paul.getRoads());
	}
	
	
}
