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
import shared.gameModel.MessageLine;
import shared.gameModel.MessageList;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class SendChatCommand {

	private IServerFacade serverFacade;
	private Player paul = new Player();
	private Player daniel = new Player();
	private Player ife = new Player();
	private Player josh = new Player();
	private GameModel game;
	private TurnTracker turnTracker;
	private ResourceList bank;
	
	@Before 
	public void setUp() {
		
		serverFacade =null;
		serverFacade = ServerFacade.getSingleton();
		
		paul.setPlayerIndex(0);
		daniel.setPlayerIndex(1);
		ife.setPlayerIndex(2);
		josh.setPlayerIndex(3);
		
		game = new GameModel();
		game.setGameID(0);
		
		ArrayList<Player> players = new ArrayList<>();
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
		
		DevCardList paulsOldDevCard = new DevCardList(1,1,1,1,1);
		paul.setOldDevCards(paulsOldDevCard);
		
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
		
	}
	
	@After
	public void tearDown() {
		ServerFacade.clearSingleton();
		serverFacade = null;
		return;
	}
	
	
	@Test
	public void test() {
		
		serverFacade.sendChat(0, "ohhhh come on", 0);
		serverFacade.sendChat(1, "yes sirss", 0);
		serverFacade.sendChat(2, " ", 0);
		serverFacade.sendChat(3, "nope", 0);
		
		MessageList messageList = game.getChat();
		
		ArrayList<MessageLine> lines= messageList.getLines();
		assertEquals("paul",lines.get(0).getSource());
		assertEquals("ohhhh come on",lines.get(0).getMessage());
		
		assertEquals("daniel",lines.get(1).getSource());
		assertEquals("yes sirss",lines.get(1).getMessage());
		
		assertEquals("ife",lines.get(2).getSource());
		assertEquals(" ",lines.get(2).getMessage());
		
		assertEquals("josh",lines.get(3).getSource());
		assertEquals("nope",lines.get(3).getMessage());
		
	}
	
	

}
