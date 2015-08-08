package server.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.gameModel.GameModel;
import shared.gameModel.Hex;
import shared.gameModel.Map;
import shared.gameModel.Port;
import shared.gameModel.ValidMapLocations;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

/**
 * 
 * @author Ife's Group
 *
 */
public class CreateGameCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1764798123223433364L;
	private IServerFacade serverFacade;
	private boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	private String gameName;
	
	private Map gameBoard;
	
	public CreateGameCommand(boolean randomTiles,boolean randomNumbers,boolean randomPorts, String gameName){
		IServerFacade facade = null;
		if (GamesHandler.test) {
			serverFacade = ServerFacadeTest.getSingleton();
		} else {
			serverFacade = ServerFacade.getSingleton();
		}
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.gameName = gameName;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		GameModel serverModel = new GameModel();
		serverModel.setGameName(gameName);
		serverModel.setVersion(0);
		this.gameBoard = new Map();
		gameBoard.setHexes(createTiles());
		if(randomPorts){
			gameBoard.setPorts(createRandomPorts());
		}
		else{
			gameBoard.setPorts(createPorts());
		}
		
		serverModel.setMap(gameBoard);
		serverModel.setGameID(serverFacade.getGamesList().size());
		serverFacade.addGameToList(serverModel);
	}

	private ArrayList<Hex> createTiles(){
		ArrayList<HexType> hexTypeOrder = new ArrayList<HexType> (Arrays.asList(ValidMapLocations.hexTypeOrder));
		
		ArrayList<Hex> hexes = new ArrayList<>();
		
		if(randomTiles){
			Collections.shuffle(hexTypeOrder);
		}
		
		for(int i = 0; i < ValidMapLocations.hexes.size(); i++){
			Hex hex = new Hex();
			HexLocation hexLoc = ValidMapLocations.hexes.get(i);
			hex.setLocation(hexLoc);
			HexType hexType = hexTypeOrder.get(i);
			if(hexType == HexType.desert){
				gameBoard.setRobber(hexLoc);
			}
			hex.setResource(hexType);
			hexes.add(hex);
		}
		
		setNumbers(hexes);
		return hexes;
	}
	
	private void setNumbers(ArrayList<Hex> hexes){
		HashMap<Integer, Integer> chitAmounts = new HashMap<>(ValidMapLocations.chitAmounts);
		
		ArrayList<Integer> chitOrder = new ArrayList<Integer>(Arrays.asList(ValidMapLocations.chitOrder));
		
		if(randomNumbers){
			Collections.shuffle(chitOrder);
		}
		
		Hex leftOverHex = new Hex();
		
		for(int i = 0; i < hexes.size(); i++){
			Hex hex = hexes.get(i);
			if(hex.getResource() != HexType.desert){
				int chit = chitOrder.get(i);
				if(chit != 0){
					hex.setNumber(chit);
					chitAmounts.put(chit, chitAmounts.get(chit)-1);
				}
				else{
					leftOverHex = hex;
				}
			}
		}
		
		for(int key: chitAmounts.keySet()){
			if(chitAmounts.get(key) > 0){
				leftOverHex.setNumber(key);
			}
		}
	}
	
	private ArrayList<Port> createPorts(){
		ArrayList<Port> ports = new ArrayList<>();
		
		Port woodPort = new Port();
		woodPort.setLocation(new HexLocation(-3,2));
		woodPort.setDirection(EdgeDirection.NE);
		woodPort.setResource(PortType.wood);
		woodPort.setRatio(2);
		
		Port sheepPort = new Port();
		sheepPort.setLocation(new HexLocation(3,-1));
		sheepPort.setDirection(EdgeDirection.NW);
		sheepPort.setResource(PortType.sheep);
		sheepPort.setRatio(2);
		
		Port orePort = new Port();
		orePort.setLocation(new HexLocation(1,-3));
		orePort.setDirection(EdgeDirection.S);
		orePort.setResource(PortType.ore);
		orePort.setRatio(2);
		
		Port brickPort = new Port();
		brickPort.setLocation(new HexLocation(-2,3));
		brickPort.setDirection(EdgeDirection.NE);
		brickPort.setResource(PortType.brick);
		brickPort.setRatio(2);
		
		Port wheatPort = new Port();
		wheatPort.setLocation(new HexLocation(-1,-2));
		wheatPort.setDirection(EdgeDirection.S);
		wheatPort.setResource(PortType.wheat);
		wheatPort.setRatio(2);
		
		Port threePort1 = new Port();
		threePort1.setLocation(new HexLocation(3, -3));
		threePort1.setDirection(EdgeDirection.SW);
		threePort1.setRatio(3);
		threePort1.setResource(PortType.three);
		
		Port threePort2 = new Port();
		threePort2.setLocation(new HexLocation(2, 1));
		threePort2.setDirection(EdgeDirection.NW);
		threePort2.setRatio(3);
		threePort2.setResource(PortType.three);
		
		Port threePort3 = new Port();
		threePort3.setLocation(new HexLocation(0, 3));
		threePort3.setDirection(EdgeDirection.N);
		threePort3.setRatio(3);
		threePort3.setResource(PortType.three);
		
		Port threePort4 = new Port();
		threePort4.setLocation(new HexLocation(-3, 0));
		threePort4.setDirection(EdgeDirection.SE);
		threePort4.setRatio(3);
		threePort4.setResource(PortType.three);
		
		ports.add(woodPort);
		ports.add(sheepPort);
		ports.add(orePort);
		ports.add(brickPort);
		ports.add(wheatPort);
		
		ports.add(threePort1);
		ports.add(threePort2);
		ports.add(threePort3);
		ports.add(threePort4);
		
		return ports;
	}
	
	private ArrayList<Port> createRandomPorts(){
		ArrayList<Port> ports = new ArrayList<>();
		HashSet<Integer> usedPortLocations = new HashSet<>();
		Random random = new Random();
		int portLocation;
		
		do{
			portLocation = random.nextInt(ValidMapLocations.numOfPossiblePortLocations);
		}while(usedPortLocations.contains(portLocation));
		usedPortLocations.add(portLocation);
		usedPortLocations.add(ValidMapLocations.matchingPortLocations.get(portLocation));
		EdgeLocation woodPortLocation = ValidMapLocations.ports.get(portLocation);
		Port woodPort = new Port();
		woodPort.setDirection(woodPortLocation.getDir());
		woodPort.setLocation(woodPortLocation.getHexLoc());
		woodPort.setResource(PortType.wood);
		woodPort.setRatio(2);
		ports.add(woodPort);
		
		
		do{
			portLocation = random.nextInt(ValidMapLocations.numOfPossiblePortLocations);
		}while(usedPortLocations.contains(portLocation));
		usedPortLocations.add(portLocation);
		usedPortLocations.add(ValidMapLocations.matchingPortLocations.get(portLocation));
		EdgeLocation orePortLocation = ValidMapLocations.ports.get(portLocation);
		Port orePort = new Port();
		orePort.setDirection(orePortLocation.getDir());
		orePort.setLocation(orePortLocation.getHexLoc());
		orePort.setResource(PortType.ore);
		orePort.setRatio(2);
		ports.add(orePort);
		
		do{
			portLocation = random.nextInt(ValidMapLocations.numOfPossiblePortLocations);
		}while(usedPortLocations.contains(portLocation));
		usedPortLocations.add(portLocation);
		usedPortLocations.add(ValidMapLocations.matchingPortLocations.get(portLocation));
		EdgeLocation sheepPortLocation = ValidMapLocations.ports.get(portLocation);
		Port sheepPort = new Port();
		sheepPort.setDirection(sheepPortLocation.getDir());
		sheepPort.setLocation(sheepPortLocation.getHexLoc());
		sheepPort.setResource(PortType.sheep);
		sheepPort.setRatio(2);
		ports.add(sheepPort);
		
		do{
			portLocation = random.nextInt(ValidMapLocations.numOfPossiblePortLocations);
		}while(usedPortLocations.contains(portLocation));
		usedPortLocations.add(portLocation);
		usedPortLocations.add(ValidMapLocations.matchingPortLocations.get(portLocation));
		EdgeLocation wheatPortLocation = ValidMapLocations.ports.get(portLocation);
		Port wheatPort = new Port();
		wheatPort.setDirection(wheatPortLocation.getDir());
		wheatPort.setLocation(wheatPortLocation.getHexLoc());
		wheatPort.setResource(PortType.wheat);
		wheatPort.setRatio(2);
		ports.add(wheatPort);
		
		do{
			portLocation = random.nextInt(ValidMapLocations.numOfPossiblePortLocations);
		}while(usedPortLocations.contains(portLocation));
		usedPortLocations.add(portLocation);
		usedPortLocations.add(ValidMapLocations.matchingPortLocations.get(portLocation));
		EdgeLocation brickPortLocation = ValidMapLocations.ports.get(portLocation);
		Port brickPort = new Port();
		brickPort.setDirection(brickPortLocation.getDir());
		brickPort.setLocation(brickPortLocation.getHexLoc());
		brickPort.setResource(PortType.brick);
		brickPort.setRatio(2);
		ports.add(brickPort);
		
		for(int i = 0; i < 4; i++){
			do{
				portLocation = random.nextInt(ValidMapLocations.numOfPossiblePortLocations);
			}while(usedPortLocations.contains(portLocation));
			usedPortLocations.add(portLocation);
			usedPortLocations.add(ValidMapLocations.matchingPortLocations.get(portLocation));
			EdgeLocation threePortLocation = ValidMapLocations.ports.get(portLocation);
			Port threePort = new Port();
			threePort.setDirection(threePortLocation.getDir());
			threePort.setLocation(threePortLocation.getHexLoc());
			threePort.setResource(PortType.three);
			threePort.setRatio(3);
			ports.add(threePort);
		}
		
		return ports;
	}
}
