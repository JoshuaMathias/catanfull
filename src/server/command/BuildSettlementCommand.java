package server.command;

import java.util.ArrayList;

import server.facade.ServerFacade;
import shared.definitions.HexType;
import shared.gameModel.GameModel;
import shared.gameModel.Hex;
import shared.gameModel.Map;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * 
 * @author Ife's Group
 *
 */
public class BuildSettlementCommand implements Command {

	int playerIndex;
	VertexLocation vertexLocation;
	boolean free;
	private GameModel serverModel;
	
	private Player player;
	
	public BuildSettlementCommand(int playerIndex,
			VertexLocation vertexLocation, boolean free, GameModel serverModel) {
		// TODO Auto-generated constructor stub
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation;
		this.free = free;
		this.serverModel = serverModel;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ArrayList<Player> playerList = serverModel.getPlayers();
		ResourceList bank = serverModel.getBank();
	
		player = playerList.get(playerIndex);
		
		if(free == false) {
			updatePlayerResources(player);
			updateBankResources(bank);
		}
		
		player.decrementSettlement();
		int victoryPoints = player.getVictoryPoints();
		player.setVictoryPoints(victoryPoints + 1);
		Map map = serverModel.getMap();
		VertexObject settlement = new VertexObject(playerIndex,vertexLocation.getNormalizedLocation());
		map.addSettlement(settlement);
		
		if(serverModel.getTurnTracker().getStatus().equals("SecondRound")){
			giveSecondRoundResources(settlement);
		}
		
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife buit a settlement all by himself, because he is single and lonely. No girlfriend. No worth.");
		}
		else{
			line.setMessage(username + " built a settlement");
		}
//		line.setMessage(username + " built a settlement");
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}
	
	private void updatePlayerResources(Player player) {
		
		ResourceList playerResourceList = player.getResources();
		int currentWood = playerResourceList.getWood();
		int currentWheat = playerResourceList.getWheat();
		int currentBrick = playerResourceList.getBrick();
		int currentSheep = playerResourceList.getSheep();
		
		playerResourceList.setWood(currentWood - 1);
		playerResourceList.setWheat(currentWheat - 1);
		playerResourceList.setBrick(currentBrick - 1);
		playerResourceList.setSheep(currentSheep - 1);
	}
	
	private void updateBankResources(ResourceList bank) {
		
		int currentBankWood = bank.getWood();
		int currentBankWheat = bank.getWheat();
		int currentBankBrick = bank.getBrick();
		int currentBankSheep = bank.getSheep();
		
		bank.setWood(currentBankWood + 1);
		bank.setWheat(currentBankWheat + 1);
		bank.setBrick(currentBankBrick + 1);
		bank.setSheep(currentBankSheep + 1);
	}

	
	private void giveSecondRoundResources(VertexObject settlement){
		VertexLocation location = settlement.getLocation();
		VertexDirection direction = location.getDir();
		HexLocation hexLoc = location.getHexLoc();
		int x = hexLoc.getX();
		int y = hexLoc.getY();
		
		HexLocation hexLoc2 = new HexLocation(x, y-1);
		
		HexLocation hexLoc3 = null;
		switch(direction){
		case E:
			break;
		case NE:
			hexLoc3 = new HexLocation(x+1, y-1);
			break;
		case NW:
			hexLoc3 = new HexLocation(x-1, y);
			break;
		case SE:
			break;
		case SW:
			break;
		case W:
			break;
		default:
			break;
		
		}
		
		findHex(hexLoc);
		findHex(hexLoc2);
		findHex(hexLoc3);
	}
	
	private void findHex(HexLocation loc){
		ArrayList<Hex> hexes = serverModel.getMap().getHexes();
		for(Hex hex: hexes){
			HexLocation hexLoc = hex.getLocation();
			if(hexLoc.equals(loc)){
				giveResource(hex.getResource());
			}
		}
	}
	
	private void giveResource(HexType resource){
		ResourceList playerResources = player.getResources();
		ResourceList bank = serverModel.getBank();
		
		switch(resource){
		case brick:
			playerResources.setBrick(playerResources.getBrick() + 1);
			bank.setBrick(bank.getBrick() -1);
			break;
		case desert:
			break;
		case ore:
			playerResources.setOre(playerResources.getOre() + 1);
			bank.setOre(bank.getOre() -1);
			break;
		case sheep:
			playerResources.setSheep(playerResources.getSheep() + 1);
			bank.setSheep(bank.getSheep() -1);
			break;
		case water:
			break;
		case wheat:
			playerResources.setWheat(playerResources.getWheat() + 1);
			bank.setWheat(bank.getWheat() -1);
			break;
		case wood:
			playerResources.setWood(playerResources.getWood() + 1);
			bank.setWood(bank.getWood() -1);
			break;
		default:
			break;
		
		}
	}
}
