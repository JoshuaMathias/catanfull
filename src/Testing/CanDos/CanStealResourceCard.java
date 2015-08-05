package Testing.CanDos;


import org.junit.*;

import shared.gameModel.*;
import shared.locations.*;

import java.util.ArrayList;

import static org.junit.Assert.* ;

public class CanStealResourceCard {

		private Map map;
		private GameModel clientModel;
		private TurnTracker turnTracker;
		private Player Ife;
		private Player Josh;
		private Player Daniel;
		private Player Paul;
		
		@Before 
		public void setUp() {
			
			map = new Map();
			clientModel = new GameModel();
			turnTracker = new TurnTracker();
			Ife = new Player();
			Josh = new Player();
			Daniel = new Player();
			Paul= new Player();
			
			ArrayList<Player> playerList = new ArrayList<>();

			ResourceList ifeResources = new ResourceList(0,0,0,0,0);
			ResourceList joshResources = new ResourceList(0,2,3,0,2);
			ResourceList danielResources = new ResourceList(0,4,0,1,0);
			ResourceList paulResources = new ResourceList(5,4,0,1,0);
			
			Ife.setResources(ifeResources);
			Josh.setResources(joshResources);
			Daniel.setResources(danielResources);
			Paul.setResources(paulResources);
			
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
			clientModel.setMap(map);
		}
		
		@After
		public void tearDown() {
			clientModel = null;
			return;
		}
		
		@Test
		public void testOne() {
			
			HexLocation hexLoc = new HexLocation(0,1);
			VertexLocation spot = new VertexLocation(hexLoc,VertexDirection.NE);
		
			VertexObject paulSettlement = new VertexObject();
			paulSettlement.setOwner(3);
			paulSettlement.setLocation(spot);
			
			map.addSettlement(paulSettlement);
			map.setRobber(hexLoc);
			
			Paul.incrementSettlement();
			turnTracker.setStatus("Robbing");
			turnTracker.setCurrentTurn(0);
			
			assertTrue(clientModel.canStealResourceCard(0, 7, 3));//Ife steals successfully from Paul
			
			VertexLocation spot2 = new VertexLocation(hexLoc,VertexDirection.W);
			
			VertexObject joshCity = new VertexObject();
			joshCity.setOwner(1);
			joshCity.setLocation(spot2);
			
			map.addCity(joshCity);//Paul and Josh have a settlement/city on same hex location
			
			Josh.incrementCity();
			turnTracker.setCurrentTurn(3);
			
			assertTrue(clientModel.canStealResourceCard(3, 7, 1));//Paul steals successfully from Josh
			//assertFalse(clientModel.canStealResourceCard(3, 6, 1));//not rolled 7
			
			HexLocation hexLoc2 = new HexLocation(0,0);
			map.setRobber(hexLoc2);
			turnTracker.setCurrentTurn(2);
			
			assertTrue(clientModel.canStealResourceCard(2, 7, 3));//Daniel steals successfully from Paul
			assertFalse(clientModel.canStealResourceCard(2,  7,  1));//Daniel fails to steal from Josh because Satan is bordering paul, but not josh, even though pual and josh share a hex
			
			HexLocation hexLoc3 = new HexLocation(2,-3);
			VertexLocation spot3 = new VertexLocation(hexLoc3,VertexDirection.E);
			
			VertexObject ifeCity = new VertexObject();
			ifeCity.setOwner(0);
			ifeCity.setLocation(spot3);
			
			map.addCity(ifeCity);
			map.setRobber(hexLoc3);
			
			Ife.incrementCity();
			turnTracker.setCurrentTurn(2);
			
			assertFalse(clientModel.canStealResourceCard(2, 7, 0));//Ife has no resource Cards
		}
		
		
		
}
