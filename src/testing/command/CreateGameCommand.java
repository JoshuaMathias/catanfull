package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.gameModel.GameModel;
import shared.gameModel.Hex;
import shared.gameModel.Map;
import shared.gameModel.Port;

public class CreateGameCommand {

	private IServerFacade serverFacade;
	
	@Before 
	public void setUp() {
		
		serverFacade = ServerFacade.getSingleton();
	}
	
	@After
	public void tearDown() {
		ServerFacade.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
		
		portTest(false,0);
		portTest(true,1);
		
		chitTest(false,2);
		chitTest(true,3);
		
		hexTest(false,4);
		hexTest(true,5);
		
		randomEverything(6);
	}
	
	private void portTest(boolean random, int gameNumber) {
		serverFacade.createGame(false, false, random, "port test");
		
		ArrayList<GameModel> gamesList = serverFacade.getGamesList();
		GameModel game = gamesList.get(gameNumber);
	
		Map gameBoard = game.getMap();
		ArrayList<Port> ports = gameBoard.getPorts();
		
		int portRatioTwoCount = 0;
		int portRatioThreeCount = 0;
		int brickPortCount = 0;
		int orePortCount = 0;
		int woodPortCount = 0;
		int wheatPortCount = 0;
		int sheepPortCount = 0;
		int threePortCount = 0;
		
		for(Port port: ports) {
			
			if(port.getRatio() == 2) {
				portRatioTwoCount++;
			} else if(port.getRatio() == 3) {
				portRatioThreeCount++;
			}
			
			if(port.getResource() == PortType.brick) {
				brickPortCount++;
			} else if(port.getResource() == PortType.ore) {
				orePortCount++;
			} else if(port.getResource() == PortType.sheep) {
				sheepPortCount++;
			} else if(port.getResource() == PortType.wood) {
				woodPortCount++;
			} else if(port.getResource() == PortType.wheat) {
				wheatPortCount++;
			} else if(port.getResource() == PortType.three) {
				threePortCount++;
			}
		}
		
		assertTrue(portRatioTwoCount == 5);
		assertTrue(portRatioThreeCount == 4);
		assertTrue(brickPortCount == 1);
		assertTrue(orePortCount == 1);
		assertTrue(sheepPortCount == 1);
		assertTrue(woodPortCount == 1);
		assertTrue(wheatPortCount == 1);
		assertTrue(threePortCount == 4);
	}
	
	private void chitTest(boolean random, int gameNumber) {
		
		serverFacade.createGame(false, random, false, "chit test");
		
		ArrayList<GameModel> gamesList = serverFacade.getGamesList();
		GameModel game = gamesList.get(gameNumber);

		Map gameBoard = game.getMap();
		ArrayList<Hex> hexes = gameBoard.getHexes();
		
		int chitTwoCount = 0;
		int chitThreeCount = 0;
		int chitFourCount = 0;
		int chitFiveCount = 0;
		int chitSixCount = 0;
		int chitSevenCount = 0;
		int chitEightCount = 0;
		int chitNineCount = 0;
		int chitTenCount = 0;
		int chitElevenCount = 0;
		int chitTwelveCount = 0;
		
		for(Hex hex : hexes) {
			
			HexType hexType = hex.getResource();
			int chit = hex.getNumber();
			
			if(hexType == HexType.desert) {
				assertTrue(chit == 0);
			} else {
				assertFalse(chit == 0);
			}
			
			switch(chit){
				
			case 2:
				chitTwoCount++;
				break;
			case 3:
				chitThreeCount++;
				break;
			case 4:
				chitFourCount++;
				break;
			case 5:
				chitFiveCount++;
				break;
			case 6:
				chitSixCount++;
				break;
			case 7:
				chitSevenCount++;
				break;
			case 8:
				chitEightCount++;
				break;
			case 9:
				chitNineCount++;
				break;
			case 10:
				chitTenCount++;
				break;
			case 11:
				chitElevenCount++;
				break;
			case 12:
				chitTwelveCount++;
				break;
			default:
				break;
			}
		}
		
		assertTrue(chitTwoCount == 1);
		assertTrue(chitThreeCount == 2);
		assertTrue(chitFourCount == 2);
		assertTrue(chitFiveCount == 2);
		assertTrue(chitSixCount == 2);
		assertTrue(chitSevenCount == 0);
		assertTrue(chitEightCount == 2);
		assertTrue(chitNineCount == 2);
		assertTrue(chitTenCount == 2);
		assertTrue(chitElevenCount == 2);
		assertTrue(chitTwelveCount == 1);
		
		if(random == true) {
			GameModel nonRandomGame = gamesList.get(2);
			GameModel randomGame = gamesList.get(3);
			
			Map nonRandomBoard = nonRandomGame.getMap();
			Map randomBoard = randomGame.getMap();
			
			ArrayList<Hex> nonRandomHexes = nonRandomBoard.getHexes();
			ArrayList<Hex> randomHexes = randomBoard.getHexes();
			
			boolean difference = false;
			
			for(int i = 0; i < nonRandomHexes.size(); i++) {

				int nonRandomChit = nonRandomHexes.get(i).getNumber();
				int randomChit = randomHexes.get(i).getNumber();
				
				if(nonRandomChit != randomChit) {
					difference = true;
					break;
				}
				
			}
			
			assertTrue(difference == true);//If there is any difference between non random and random boards chits then this will be true
		}
	}
	
	private void hexTest(boolean random, int gameNumber) {
		
		serverFacade.createGame(random, false, false, "hex test");
		
		ArrayList<GameModel> gamesList = serverFacade.getGamesList();
		GameModel game = gamesList.get(gameNumber);

		Map gameBoard = game.getMap();
		ArrayList<Hex> hexes = gameBoard.getHexes();
		
		int desertCount = 0;
		
		for(Hex hex : hexes) {
			
			HexType hexType = hex.getResource();
			
			if(hexType == HexType.desert) {
				desertCount++;
			}
		}
		
		assertTrue(hexes.size() == 19);
		assertTrue(desertCount == 1);
		
		if(random == false) {
			
			int oreHexCount = 0;
			int woodHexCount = 0;
			int wheatHexCount = 0;
			int sheepHexCount = 0;
			int brickHexCount = 0;
			int desertHexCount = 0;
			int waterHexCount = 0;
			
			HexType hexType;
			for(Hex hex : hexes) {
				
				hexType = hex.getResource();
				if(hexType == HexType.brick) {
					brickHexCount++;
				} else if(hexType == HexType.ore) {
					oreHexCount++;
				} else if(hexType == HexType.wood) {
					woodHexCount++;
				} else if(hexType == HexType.wheat) {
					wheatHexCount++;
				} else if(hexType == HexType.sheep) {
					sheepHexCount++;
				} else if(hexType == HexType.water) {
					waterHexCount++;
				} else if(hexType == HexType.desert) {
					desertHexCount++;
				}
				
			}
			
			assertTrue(desertHexCount == 1);
			assertTrue(brickHexCount == 3);
			assertTrue(oreHexCount == 3);
			assertTrue(wheatHexCount == 4);
			assertTrue(sheepHexCount == 4);
			assertTrue(woodHexCount == 4);
		}
		else {
			
			GameModel nonRandomGame = gamesList.get(4);
			GameModel randomGame = gamesList.get(5);
			
			Map nonRandomBoard = nonRandomGame.getMap();
			Map randomBoard = randomGame.getMap();
			
			ArrayList<Hex> nonRandomHexes = nonRandomBoard.getHexes();
			ArrayList<Hex> randomHexes = randomBoard.getHexes();
			
			boolean difference = false;
			
			for(int i = 0; i < nonRandomHexes.size(); i++) {

				HexType nonRandomHexType = nonRandomHexes.get(i).getResource(); 
				HexType randomHexType = randomHexes.get(i).getResource();
				
				if(nonRandomHexType != randomHexType) {
					difference = true;
					break;
				}
			}
			assertTrue(difference == true);
		}
	}
	
	private void randomEverything(int gameNumber) {
		
		serverFacade.createGame(true, true, true, "randomEverything test");
		
		ArrayList<GameModel> gamesList = serverFacade.getGamesList();
		GameModel game = gamesList.get(gameNumber);
		
		Map board = game.getMap();
		
		ArrayList<Port> ports = board.getPorts();
		
		int portRatioTwoCount = 0;
		int portRatioThreeCount = 0;
		int brickPortCount = 0;
		int orePortCount = 0;
		int woodPortCount = 0;
		int wheatPortCount = 0;
		int sheepPortCount = 0;
		int threePortCount = 0;
		
		for(Port port: ports) {
			
			if(port.getRatio() == 2) {
				portRatioTwoCount++;
			} else if(port.getRatio() == 3) {
				portRatioThreeCount++;
			}
			
			if(port.getResource() == PortType.brick) {
				brickPortCount++;
			} else if(port.getResource() == PortType.ore) {
				orePortCount++;
			} else if(port.getResource() == PortType.sheep) {
				sheepPortCount++;
			} else if(port.getResource() == PortType.wood) {
				woodPortCount++;
			} else if(port.getResource() == PortType.wheat) {
				wheatPortCount++;
			} else if(port.getResource() == PortType.three) {
				threePortCount++;
			}
		}
		
		
		//Test for the ports
		assertTrue(portRatioTwoCount == 5);
		assertTrue(portRatioThreeCount == 4);
		assertTrue(brickPortCount == 1);
		assertTrue(orePortCount == 1);
		assertTrue(sheepPortCount == 1);
		assertTrue(woodPortCount == 1);
		assertTrue(wheatPortCount == 1);
		assertTrue(threePortCount == 4);
		
		ArrayList<Hex> hexes = board.getHexes();
		
		int chitTwoCount = 0;
		int chitThreeCount = 0;
		int chitFourCount = 0;
		int chitFiveCount = 0;
		int chitSixCount = 0;
		int chitSevenCount = 0;
		int chitEightCount = 0;
		int chitNineCount = 0;
		int chitTenCount = 0;
		int chitElevenCount = 0;
		int chitTwelveCount = 0;
		
		for(Hex hex : hexes) {
			
			HexType hexType = hex.getResource();
			int chit = hex.getNumber();
			
			if(hexType == HexType.desert) {
				assertTrue(chit == 0);
			} else {
				assertFalse(chit == 0);
			}
			
			switch(chit){
				
			case 2:
				chitTwoCount++;
				break;
			case 3:
				chitThreeCount++;
				break;
			case 4:
				chitFourCount++;
				break;
			case 5:
				chitFiveCount++;
				break;
			case 6:
				chitSixCount++;
				break;
			case 7:
				chitSevenCount++;
				break;
			case 8:
				chitEightCount++;
				break;
			case 9:
				chitNineCount++;
				break;
			case 10:
				chitTenCount++;
				break;
			case 11:
				chitElevenCount++;
				break;
			case 12:
				chitTwelveCount++;
				break;
			default:
				break;
			}
		}
		
		//Tests the chits
		assertTrue(chitTwoCount == 1);
		assertTrue(chitThreeCount == 2);
		assertTrue(chitFourCount == 2);
		assertTrue(chitFiveCount == 2);
		assertTrue(chitSixCount == 2);
		assertTrue(chitSevenCount == 0);
		assertTrue(chitEightCount == 2);
		assertTrue(chitNineCount == 2);
		assertTrue(chitTenCount == 2);
		assertTrue(chitElevenCount == 2);
		assertTrue(chitTwelveCount == 1);
		
		ArrayList<Hex> hexArray = board.getHexes();
		
		int desertCount = 0;
		
		for(Hex hex : hexArray) {
			
			HexType hexType = hex.getResource();
			
			if(hexType == HexType.desert) {
				desertCount++;
			}
		}
		
		assertTrue(hexArray.size() == 19);
		assertTrue(desertCount == 1);
			
		int oreHexCount = 0;
		int woodHexCount = 0;
		int wheatHexCount = 0;
		int sheepHexCount = 0;
		int brickHexCount = 0;
		int desertHexCount = 0;
		int waterHexCount = 0;
		
		HexType hexType;
		for(Hex hex : hexArray) {
			
			hexType = hex.getResource();
			if(hexType == HexType.brick) {
				brickHexCount++;
			} else if(hexType == HexType.ore) {
				oreHexCount++;
			} else if(hexType == HexType.wood) {
				woodHexCount++;
			} else if(hexType == HexType.wheat) {
				wheatHexCount++;
			} else if(hexType == HexType.sheep) {
				sheepHexCount++;
			} else if(hexType == HexType.water) {
				waterHexCount++;
			} else if(hexType == HexType.desert) {
				desertHexCount++;
			}
			
		}
		
		assertTrue(desertHexCount == 1);
		assertTrue(brickHexCount == 3);
		assertTrue(oreHexCount == 3);
		assertTrue(wheatHexCount == 4);
		assertTrue(sheepHexCount == 4);
		assertTrue(woodHexCount == 4);
		assertTrue(waterHexCount == 0);
	}
	
	
}
