package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.Road;
import shared.gameModel.TurnTracker;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class BuildRoadCommand {

	private IServerFacade serverFacade;
	private ArrayList<Player> players = new ArrayList<>();
	private TurnTracker turnTracker;
	
	@Before 
	public void setUp() {
		
		serverFacade = ServerFacade.getSingleton();
		
		Player paul = new Player();
		Player daniel = new Player();
		Player ife = new Player();
		Player josh = new Player();
		
		
		paul.setName("paul");
		daniel.setName("daniel");
		ife.setName("ife");
		josh.setName("josh");
		
		players.add(paul);
		players.add(daniel);
		players.add(ife);
		players.add(josh);
		
		ResourceList paulsResources = new ResourceList(6,6,6,6,6);
		paul.setResources(paulsResources);
		
		ResourceList danielsResources = new ResourceList(6,6,6,6,6);
		daniel.setResources(danielsResources);
		
		GameModel newGame = new GameModel();
		newGame.setGameID(0);
		
		turnTracker = new TurnTracker();
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("FirstRound");
		
		Map map1 = new Map();
		newGame.setMap(map1);
		
		ArrayList<GameModel> games = serverFacade.getGamesList();
		newGame.setPlayers(players);
		
		games.add(newGame);	
	}
	
	@After
	public void tearDown() {
		ServerFacade.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
		
		int gameNum = 0;
		roadTest(true, "FirstRound", gameNum);
		
		GameModel game2 = new GameModel();
		game2.setGameID(1);
		game2.setTurnTracker(turnTracker);
		
		Map map2 = new Map();
		game2.setMap(map2);
		
		game2.setPlayers(players);
		serverFacade.addGameToList(game2);
		
		
		VertexLocation settlementLocation = new VertexLocation(new HexLocation(0,0), VertexDirection.E);
		
		serverFacade.buildSettlement(0, settlementLocation, true, 1);
		System.out.println(serverFacade.getGameModel(1).getMap().getSettlements().size());
		EdgeLocation roadLocation = new EdgeLocation(new HexLocation(0,0), EdgeDirection.NE);
		serverFacade.buildRoad(0, roadLocation, true, 1);
		gameNum = 1;
		//roadTest(false, "Playing", gameNum);
	}
	
	private void roadTest(boolean free, String status, int gameNum) {
		
		turnTracker.setStatus(status);
		serverFacade.getGameModel(gameNum).setTurnTracker(turnTracker);
		
		EdgeLocation roadLocation = new EdgeLocation(new HexLocation(0,0), EdgeDirection.N);
		HexLocation hexLoc = roadLocation.getHexLoc();
		
		ResourceList beforeResources = players.get(0).getResources();
		ResourceList tempResources = new ResourceList(beforeResources.getBrick(), beforeResources.getOre(), beforeResources.getSheep(),
				beforeResources.getWheat(), beforeResources.getWood());
		
		ResourceList beforeBankResources = serverFacade.getGamesList().get(gameNum).getBank();
		ResourceList tempBankResources = new ResourceList(beforeBankResources.getBrick(),beforeBankResources.getOre(),beforeBankResources.getSheep(),
				beforeBankResources.getWheat(), beforeBankResources.getWood());
		
		int roadAmount = players.get(0).getRoads();
		
		VertexLocation settlementLocation = new VertexLocation(new HexLocation(0,0), VertexDirection.NE);
		System.out.println("before: " + serverFacade.getGameModel(gameNum).getMap().getSettlements().size());
		serverFacade.buildSettlement(0, settlementLocation, free, gameNum);
		System.out.println("after: " + serverFacade.getGameModel(gameNum).getMap().getSettlements().size());
		
		serverFacade.buildRoad(0, roadLocation, free, gameNum);
		
		int afterRoadAmount = players.get(0).getRoads();
	
		System.out.println(roadAmount + " " + afterRoadAmount);
		assertEquals(roadAmount, (afterRoadAmount + 1));
		
		ResourceList afterResources = players.get(0).getResources();
		
		ResourceList afterBankResources = serverFacade.getGamesList().get(gameNum).getBank();
		
		compareResources(tempResources,afterResources, free, false);
		
		if(free == false) {
			compareResources(tempBankResources,afterBankResources,free, true);
		}
		
		Map board = serverFacade.getGamesList().get(gameNum).getMap();
		ArrayList<Road> roads= board.getRoads();
		
		assertTrue(hexLoc.equals(roads.get(0).getLocation().getHexLoc()));
		
	}
	
	private void compareResources(ResourceList beforeResources, ResourceList afterResources, boolean free, boolean banking) {
		
		int beforeBrick = beforeResources.getBrick();
		int beforeOre = beforeResources.getOre();
		int beforeSheep = beforeResources.getSheep();
		int beforeWheat = beforeResources.getWheat();
		int beforeWood = beforeResources.getWood();
		
		int afterBrick = afterResources.getBrick();
		int afterOre = afterResources.getOre();
		int afterSheep = afterResources.getSheep();
		int afterWheat = afterResources.getWheat();
		int afterWood = afterResources.getWood();
		
		if(free == false && banking == false) {
			
		assertEquals(beforeBrick, (afterBrick + 1));
		assertEquals(beforeOre, afterOre);
		assertEquals(beforeSheep, afterSheep);
		assertEquals(beforeWheat, afterWheat);
		assertEquals(beforeWood, (afterWood + 1)); 
		} 
		else if(banking == false && free == true){
			assertEquals(beforeBrick, afterBrick);
			assertEquals(beforeOre, afterOre);
			assertEquals(beforeSheep, afterSheep);
			assertEquals(beforeWheat, afterWheat);
			assertEquals(beforeWood, afterWood); 
		} else {
			assertEquals(beforeBrick, (afterBrick - 1));
			assertEquals(beforeOre, afterOre);
			assertEquals(beforeSheep, afterSheep);
			assertEquals(beforeWheat, afterWheat);
			assertEquals(beforeWood, (afterWood - 1)); 
		}
	}
	
}
