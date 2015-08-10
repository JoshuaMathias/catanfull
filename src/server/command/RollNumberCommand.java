package server.command;

import java.io.Serializable;
import java.util.ArrayList;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.definitions.HexType;
import shared.gameModel.GameModel;
import shared.gameModel.Hex;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.VertexObject;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * 
 * @author Ife's Group
 *
 */
public class RollNumberCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1196063514032709023L;
	private int sender;
	private int number;
	private transient GameModel serverModel;
	
	private HexLocation robberPosition;
	private int gameID;
	
	public RollNumberCommand(int sender, int number, GameModel serverModel) {
		super();
		this.sender = sender;
		this.number = number;
		this.serverModel = serverModel;
		this.gameID = serverModel.getGameID();
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		if (GamesHandler.test) {
			serverModel = ServerFacadeTest.getSingleton().getGameModel(gameID);
		} else {
			serverModel = ServerFacade.getSingleton().getGameModel(gameID);
		}
		
		Player player = serverModel.getPlayers().get(sender);
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife rolled a " + Integer.toString(number) + ", just like he should roll over and give up");
		} else if (username.toLowerCase().equals("josh")) {
			line.setMessage("Josh rolled a " + Integer.toString(number) + ". He knows how to stop, drop, and roll.");
		}
		else{
			line.setMessage(username + " rolled a " + Integer.toString(number));
		}
//		line.setMessage(username + " rolled a " + Integer.toString(number));
		line.setSource(username);
		serverModel.getLog().addLine(line);
		
		this.robberPosition = serverModel.getMap().getRobber();
		System.out.println("Robber position: "+robberPosition);
		if(number == 7){
			if(needToDiscard()){
				serverModel.getTurnTracker().setStatus("Discarding");
			}
			else{
				serverModel.getTurnTracker().setStatus("Robbing");
			}
			return;
		}
		
		ArrayList<Hex> hexes = serverModel.getMap().getHexes();
		
		for(Hex hex: hexes){
			if(hex.getNumber() == number){
				findSettlementsCities(hex);
			}
		}
		
		serverModel.getTurnTracker().setStatus("Playing");
	}
	
	private boolean needToDiscard() {
		// TODO Auto-generated method stub
		ArrayList<Player> players = serverModel.getPlayers();
		ArrayList<Integer> discardingPlayersIndeces = new ArrayList<>();
		for(Player player: players){
			if(player.getResourceCardNum() > 7){
				discardingPlayersIndeces.add(player.getPlayerIndex());
			}
		}
		if(discardingPlayersIndeces.size() > 0){
			serverModel.setDiscardingPlayersIndeces(discardingPlayersIndeces);
			return true;
		}
		
		serverModel.setDiscardingPlayersIndeces(null);
		return false;
	}

	private void findSettlementsCities(Hex hex){
		ArrayList<VertexObject> settlements = serverModel.getMap().getSettlements();
		ArrayList<VertexObject> cities = serverModel.getMap().getCities();
		int x = hex.getLocation().getX();
		int y = hex.getLocation().getY();
		HexType resource = hex.getResource();
		
		HexLocation hexLoc = new HexLocation(x,y);
		for(VertexObject settlement: settlements){
			System.out.println(settlement);
			int settlementX = settlement.getLocation().getHexLoc().getX();
			int settlementY = settlement.getLocation().getHexLoc().getY();
			if(settlementX == x && settlementY == y && !robberPosition.equals(settlement.getLocation().getHexLoc())){
				giveResource(settlement.getOwner(), resource, false);
			}
		}
		for(VertexObject city: cities){
			int cityX = city.getLocation().getHexLoc().getX();
			int cityY = city.getLocation().getHexLoc().getY();
			if(cityX == x && cityY == y && !robberPosition.equals(city.getLocation().getHexLoc())){
				giveResource(city.getOwner(), resource, true);
			}
		}
		
		
		hexLoc = new HexLocation(x,y-1);
		VertexLocation spot1 = new VertexLocation(hexLoc, VertexDirection.SW);
		
		VertexLocation spot2 = new VertexLocation(hexLoc, VertexDirection.SE);
		
		
		hexLoc = new HexLocation(x+1, y-1);
		spot1 = new VertexLocation(hexLoc, VertexDirection.SW);
		findSettlementCityOnHex(settlements, spot1, resource, false);
		findSettlementCityOnHex(cities, spot1, resource, true);
		spot2 = new VertexLocation(hexLoc, VertexDirection.W);
		findSettlementCityOnHex(settlements, spot2, resource, false);
		findSettlementCityOnHex(cities, spot2, resource, true);
		
		
		hexLoc = new HexLocation(x+1, y);
		spot1 = new VertexLocation(hexLoc, VertexDirection.NW);
		findSettlementCityOnHex(settlements, spot1, resource, false);
		findSettlementCityOnHex(cities, spot1, resource, true);
		spot2 = new VertexLocation(hexLoc, VertexDirection.W);
		findSettlementCityOnHex(settlements, spot2, resource, false);
		findSettlementCityOnHex(cities, spot2, resource, true);
		
		
		hexLoc = new HexLocation(x, y+1);
		spot1 = new VertexLocation(hexLoc, VertexDirection.NW);
		findSettlementCityOnHex(settlements, spot1, resource, false);
		findSettlementCityOnHex(cities, spot1, resource, true);
		spot2 = new VertexLocation(hexLoc, VertexDirection.NE);
		findSettlementCityOnHex(settlements, spot2, resource, false);
		findSettlementCityOnHex(cities, spot2, resource, true);
		
		
		hexLoc = new HexLocation(x-1, y+1);
		spot1 = new VertexLocation(hexLoc, VertexDirection.NE);
		findSettlementCityOnHex(settlements, spot1, resource, false);
		findSettlementCityOnHex(cities, spot1, resource, true);
		spot2 = new VertexLocation(hexLoc, VertexDirection.E);
		findSettlementCityOnHex(settlements, spot2, resource, false);
		findSettlementCityOnHex(cities, spot2, resource, true);
		
		
		hexLoc = new HexLocation(x-1, y);
		spot1 = new VertexLocation(hexLoc, VertexDirection.SE);
		findSettlementCityOnHex(settlements, spot1, resource, false);
		findSettlementCityOnHex(cities, spot1, resource, true);
		spot2 = new VertexLocation(hexLoc, VertexDirection.E);
		findSettlementCityOnHex(settlements, spot2, resource, false);
		findSettlementCityOnHex(cities, spot2, resource, true);
	}
	
	private void findSettlementCityOnHex(ArrayList<VertexObject> settlementsCities, VertexLocation spot, HexType resource, boolean city){
//		spot = spot.getNormalizedLocation();
		for(VertexObject settlementCity: settlementsCities){
			VertexLocation settlementCitySpot = settlementCity.getLocation();//.getNormalizedLocation();
			if(settlementCitySpot.equals(spot) && robberPosition!=null && !robberPosition.equals(settlementCitySpot.getHexLoc())){
				giveResource(settlementCity.getOwner(), resource, city);
			}
		}
	}
	
	private void giveResource(int owner, HexType resource, boolean city){
		Player player = serverModel.getPlayers().get(owner);
		ResourceList playerResources = player.getResources();
		ResourceList bank = serverModel.getBank();
		int resourceAmount = 1;
		if(city){resourceAmount = 2;}
		
		switch(resource){
		case brick:
			if(bank.getBrick() >= resourceAmount){
				playerResources.setBrick(playerResources.getBrick() + resourceAmount);
				bank.setBrick(bank.getBrick() - resourceAmount);
			}
			else if(city && bank.getBrick() >= 1){
				playerResources.setBrick(playerResources.getBrick() + 1);
				bank.setBrick(bank.getBrick() - 1);
			}
			break;
		case desert:
			break;
		case ore:
			if(bank.getOre() >= resourceAmount){
				playerResources.setOre(playerResources.getOre() + resourceAmount);
				bank.setOre(bank.getOre() - resourceAmount);
			}
			else if(city && bank.getOre() >= 1){
				playerResources.setOre(playerResources.getOre() + 1);
				bank.setOre(bank.getOre() - 1);
			}
			break;
		case sheep:
			if(bank.getSheep() >= resourceAmount){
				playerResources.setSheep(playerResources.getSheep() + resourceAmount);
				bank.setSheep(bank.getSheep() - resourceAmount);
			}
			else if(city && bank.getSheep() >= 1){
				playerResources.setSheep(playerResources.getSheep() + 1);
				bank.setSheep(bank.getSheep() - 1);
			}
			break;
		case water:
			break;
		case wheat:
			if(bank.getWheat() >= resourceAmount){
				playerResources.setWheat(playerResources.getWheat() + resourceAmount);
				bank.setWheat(bank.getWheat() - resourceAmount);
			}
			else if(city && bank.getWheat() >= 1){
				playerResources.setWheat(playerResources.getWheat() + 1);
				bank.setWheat(bank.getWheat() - 1);
			}
			break;
		case wood:
			if(bank.getWood() >= resourceAmount){
				playerResources.setWood(playerResources.getWood() + resourceAmount);
				bank.setWood(bank.getWood() - resourceAmount);
			}
			else if(city && bank.getWood() >= 1){
				playerResources.setWood(playerResources.getWood() + 1);
				bank.setWood(bank.getWood() - 1);
			}
			break;
		default:
			break;
		}
	}
}
