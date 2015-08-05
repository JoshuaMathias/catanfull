package Testing.CanDos;

import org.junit.*;

import shared.gameModel.*;

import java.util.ArrayList;

import static org.junit.Assert.* ;

public class CanRollDice {

	private GameModel clientModel;
	private TurnTracker turnTracker;
	private Player Ife;
	private Player Josh;
	private Player Daniel;
	private Player Paul;
	
	@Before 
	public void setUp() {
		
		clientModel = new GameModel();
		turnTracker = new TurnTracker();
		Ife = new Player();
		Josh = new Player();
		Daniel = new Player();
		Paul= new Player();
		
		ArrayList<Player> playerList = new ArrayList<>();

		Ife.setPlayerID(0);
		Josh.setPlayerID(1);
		Daniel.setPlayerID(2);
		Paul.setPlayerID(3);
		
		playerList.add(Ife);
		playerList.add(Josh);
		playerList.add(Daniel);
		playerList.add(Paul);
		
		clientModel.setPlayers(playerList);
		clientModel.setTurnTracker(turnTracker);
	}
	
	@After
	public void tearDown() {
		clientModel = null;
		return;
	}
	
	@Test
	public void ifeRoll() {
		
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Rolling");
		
		assertTrue(clientModel.canRollDice(0));
		assertFalse(clientModel.canRollDice(1));
		assertFalse(clientModel.canRollDice(2));
		assertFalse(clientModel.canRollDice(3));
	}
	
	@Test
	public void notRollStatus() {
		
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Playing");
		
		assertFalse(clientModel.canRollDice(0));
		assertFalse(clientModel.canRollDice(1));
		assertFalse(clientModel.canRollDice(2));
		assertFalse(clientModel.canRollDice(3));
	}
	
}