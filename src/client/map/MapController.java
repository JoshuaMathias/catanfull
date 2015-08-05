package client.map;

import java.util.*;

import shared.definitions.*;
import shared.gameModel.*;
import shared.gameModel.Map;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import client.base.*;
import client.data.*;
import client.facade.ClientFacade;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
	private ClientFacade clientFacade;
	private String status;
	private boolean roadBuilding = false;
	ArrayList<EdgeLocation> roadBuildingEdgeLocs = new ArrayList<>();
	private boolean soldierCard = false;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
		
		clientFacade = ClientFacade.getSingleton();
		
		clientFacade.setMapController(this);
		
//		initFromModel();
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	public void wipeMap(){
		getView().newMap();
	}
	
	public void initFromModel(GameModel clientModel) {
		
//		getView().newMap();
		
		status = clientModel.getTurnTracker().getStatus();
		
		Map map = clientModel.getMap();
		
		//place hexes
		ArrayList<Hex> hexes = map.getHexes();
		for(Hex hex: hexes){
			getView().addHex(hex.getLocation(), hex.getResource());
			if(hex.getNumber() != 0){
				getView().addNumber(hex.getLocation(), hex.getNumber());
			}
		}
		
		//place water hexes
		getView().addHex(new HexLocation(-3, 3), HexType.water);
		getView().addHex(new HexLocation(-3, 2), HexType.water);
		getView().addHex(new HexLocation(-3, 1), HexType.water);
		getView().addHex(new HexLocation(-3, 0), HexType.water);
		
		getView().addHex(new HexLocation(-2, -1), HexType.water);
		getView().addHex(new HexLocation(-2, 3), HexType.water);
		
		getView().addHex(new HexLocation(-1, -2), HexType.water);
		getView().addHex(new HexLocation(-1, 3), HexType.water);
		
		getView().addHex(new HexLocation(0, 3), HexType.water);
		getView().addHex(new HexLocation(0, -3), HexType.water);
		
		getView().addHex(new HexLocation(1, -3), HexType.water);
		getView().addHex(new HexLocation(1, 2), HexType.water);
		
		getView().addHex(new HexLocation(2, -3), HexType.water);
		getView().addHex(new HexLocation(2, 1), HexType.water);
		
		getView().addHex(new HexLocation(3, -3), HexType.water);
		getView().addHex(new HexLocation(3, -2), HexType.water);
		getView().addHex(new HexLocation(3, -1), HexType.water);
		getView().addHex(new HexLocation(3, 0), HexType.water);
		
		//place settlements
		ArrayList<VertexObject> settlements = map.getSettlements();
		ArrayList<Player> players = clientModel.getPlayers();
		for(VertexObject settlement: settlements){
			CatanColor color = players.get(settlement.getOwner()).getColor();
			getView().placeSettlement(settlement.getLocation(), color);
		}
		
		//place cities
		ArrayList<VertexObject> cities = map.getCities();
		for(VertexObject city: cities){
			CatanColor color = players.get(city.getOwner()).getColor();
			getView().placeCity(city.getLocation(), color);
		}
		
		//place roads
		ArrayList<Road> roads = map.getRoads();
		for(Road road: roads){
			CatanColor color = players.get(road.getOwner()).getColor();
			getView().placeRoad(road.getLocation(), color);
		}
		
		
		
		//place ports
		ArrayList<Port> ports = map.getPorts();
		for(Port port: ports){
			EdgeLocation side = new EdgeLocation(port.getLocation(), port.getDirection());
			getView().addPort(side, port.getResource());
		}
		
		//place robber
		getView().placeRobber(map.getRobber());
		
//		if(status.equals("Robbing") 
//				&& clientModel.getTurnTracker().getCurrentTurn() == clientFacade.getPlayerIndex()) {
//			startMove(PieceType.ROBBER, false, false);
//		}
		
	}
		
	
	protected void initFromModel() {
		//<temp>
		
		Random rand = new Random();

		for (int x = 0; x <= 3; ++x) {
			
			int maxY = 3 - x;			
			for (int y = -3; y <= maxY; ++y) {				
				int r = rand.nextInt(HexType.values().length);
				HexType hexType = HexType.values()[r];
				HexLocation hexLoc = new HexLocation(x, y);
				getView().addHex(hexLoc, hexType);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NW),
						CatanColor.red);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SW),
						CatanColor.blue);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.S),
						CatanColor.orange);
				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NW), CatanColor.green);
				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NE), CatanColor.purple);
			}
			
			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					int r = rand.nextInt(HexType.values().length);
					HexType hexType = HexType.values()[r];
					HexLocation hexLoc = new HexLocation(-x, y);
					getView().addHex(hexLoc, hexType);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NW),
							CatanColor.red);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SW),
							CatanColor.blue);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.S),
							CatanColor.orange);
					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NW), CatanColor.green);
					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NE), CatanColor.purple);
				}
			}
		}
		
		PortType portType = PortType.brick;
		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.N), portType);
		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.S), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NE), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SE), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SW), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NW), portType);
		
		getView().placeRobber(new HexLocation(0, 0));
		
		getView().addNumber(new HexLocation(-2, 0), 2);
		getView().addNumber(new HexLocation(-2, 1), 3);
		getView().addNumber(new HexLocation(-2, 2), 4);
		getView().addNumber(new HexLocation(-1, 0), 5);
		getView().addNumber(new HexLocation(-1, 1), 6);
		getView().addNumber(new HexLocation(1, -1), 8);
		getView().addNumber(new HexLocation(1, 0), 9);
		getView().addNumber(new HexLocation(2, -2), 10);
		getView().addNumber(new HexLocation(2, -1), 11);
		getView().addNumber(new HexLocation(2, 0), 12);
		
		//</temp>
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		Road road = new Road(clientFacade.getPlayerIndex(),edgeLoc);
		if(roadBuilding == true){
			return clientFacade.tempCanBuildRoad(road);
		}
		else{
			return clientFacade.canBuildRoad(road);
		}
	
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		VertexObject settlement = new VertexObject(clientFacade.getPlayerIndex(), vertLoc);
		return clientFacade.canBuildSettlement(settlement);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		VertexObject city = new VertexObject(clientFacade.getPlayerIndex(), vertLoc);
		return clientFacade.canBuildCity(city);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return clientFacade.canPlaceRobber(7, hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		switch(status){
		case "FirstRound":
			clientFacade.buildRoad(edgeLoc, true);
			clientFacade.finishTurn();
			break;
		case "SecondRound":
			clientFacade.buildRoad(edgeLoc, true);
			clientFacade.finishTurn();
			break;
		default:
			if(roadBuilding == true){
//				clientFacade.buildRoad(edgeLoc, true);
				if(roadBuildingEdgeLocs.isEmpty()){
					roadBuildingEdgeLocs.add(edgeLoc);
					clientFacade.getTempMap().addRoad(new Road(clientFacade.getPlayerIndex(), edgeLoc));
					getView().startDrop(PieceType.ROAD, clientFacade.getPlayerColor(), false);
				}
				else{
					roadBuildingEdgeLocs.add(edgeLoc);
					System.out.println(roadBuildingEdgeLocs.toString());
					roadBuilding = false;
					clientFacade.roadBuilding(roadBuildingEdgeLocs.get(0), roadBuildingEdgeLocs.get(1));
					roadBuildingEdgeLocs.clear();
				}
			}
			else{
				clientFacade.buildRoad(edgeLoc, false);
			}
			break;
		}
		
		getView().placeRoad(edgeLoc, clientFacade.getPlayerColor());
	}

	public void placeSettlement(VertexLocation vertLoc) {
		switch(status){
		case "FirstRound":
			clientFacade.buildSettlement(vertLoc, true);
			break;
		case "SecondRound":
			clientFacade.buildSettlement(vertLoc, true);
			break;
		default:
			clientFacade.buildSettlement(vertLoc, false);
			break;
		}
		
		getView().placeSettlement(vertLoc, clientFacade.getPlayerColor());
	}

	public void placeCity(VertexLocation vertLoc) {
		clientFacade.buildCity(vertLoc);
		getView().placeCity(vertLoc, clientFacade.getPlayerColor());
	}

	public void placeRobber(HexLocation hexLoc) {
		
		ArrayList<Player> players = clientFacade.getPlayers();
		ArrayList<RobPlayerInfo> newList = new ArrayList<RobPlayerInfo>();
		
		clientFacade.getClientModel().getMap().setRobber(hexLoc);//Make this the COPY clientFacade
		
		for(int i = 0; i < players.size(); i++) {
			if(clientFacade.canStealResourceCard(7, i) == false){
				System.out.println("Cannot steal resource from " + players.get(i).getName());
			}
			if(clientFacade.getPlayerIndex() != i && clientFacade.canStealResourceCard(7, i)) {
				
				Player player = players.get(i);
				
				System.out.println("Can steal resource from " + player.getName());
				
				RobPlayerInfo robPlayer = new RobPlayerInfo();
				robPlayer.setColorEnum(player.getColor());
				robPlayer.setId(player.getPlayerID());
				robPlayer.setPlayerIndex(player.getPlayerIndex());
				robPlayer.setName(player.getName());
				robPlayer.setNumCards(player.getResourceCardNum());//need to change this from hard coded 5 to actual number of resource cards
				
				newList.add(robPlayer);
			}
		}
		
		RobPlayerInfo[] robPlayerArray = new RobPlayerInfo[newList.size()];
		
		if(robPlayerArray.length > 0) {
		
		robPlayerArray = newList.toArray(robPlayerArray);
		robView.setPlayers(robPlayerArray);
		robView.showModal();
		}
		else if(robPlayerArray.length == 0) {
			
			if(soldierCard == true){
				clientFacade.soldier(-1, clientFacade.getRobber());
				soldierCard = false;
				robView.closeModal();
			}
			else{

				clientFacade.robPlayer(-1, clientFacade.getRobber());//moves the robber even though there was no player to steal from
				robView.closeModal();
				clientFacade.refresh();
			} 
			
		}
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		roadBuilding = false;
		soldierCard = false;
		GameModel clientModel = clientFacade.getClientModel();
		int playerIndex = clientFacade.getPlayerIndex();
		ResourceList resources = clientModel.getPlayers().get(playerIndex).getResources();
		
		boolean canPlace = false;
		
		if (pieceType==PieceType.ROAD) {
			canPlace = clientModel.hasEnoughForRoad(resources, playerIndex);
		} else if (pieceType==PieceType.SETTLEMENT) {
			canPlace = clientModel.hasEnoughForSettlement(resources, playerIndex);
		} else if (pieceType==PieceType.CITY) {
			canPlace = clientModel.hasEnoughForCity(resources, playerIndex);
		}else if(pieceType == PieceType.ROBBER){
			System.out.println("PieceType: " + pieceType);
			getView().startDrop(pieceType, clientFacade.getPlayerColor(), false);
		}
		
		if(canPlace){
			getView().startDrop(pieceType, clientFacade.getPlayerColor(), true);
		}
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		soldierCard  = true;
		getView().startDrop(PieceType.ROBBER, clientFacade.getPlayerColor(), true);
	}
	
	public void playRoadBuildingCard() {	
		roadBuilding = true;
		clientFacade.initTempClientModel();
		getView().startDrop(PieceType.ROAD, clientFacade.getPlayerColor(), true);
	}
	
	public void robPlayer(RobPlayerInfo victim) {
		//robView.setPlayers(victim);
		if(soldierCard == true){
			clientFacade.soldier(victim.getPlayerIndex(), clientFacade.getRobber());
			soldierCard = false;
			robView.closeModal();
		}
		else{
			clientFacade.robPlayer(victim.getPlayerIndex(), clientFacade.getRobber());
			robView.closeModal();
		} 
	}
	
}

