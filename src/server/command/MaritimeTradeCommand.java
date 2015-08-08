package server.command;

import java.io.Serializable;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.definitions.ResourceType;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;

/**
 * 
 * @author Ife's Group
 *
 */
public class MaritimeTradeCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -678453708731838256L;
	private int playerIndex; 
	private int ratio;
	private ResourceType inputResource;
	private ResourceType outputResource;
	private transient GameModel serverModel;
	private int gameID;
	
	public MaritimeTradeCommand(int playerIndex, int ratio,
			ResourceType inputResource, ResourceType outputResource, GameModel serverModel) {
		super();
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = inputResource;
		this.outputResource = outputResource;
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
		
		Player player = serverModel.getPlayers().get(playerIndex);
		ResourceList playerResources = player.getResources();
		ResourceList bank = serverModel.getBank();
		
		switch(inputResource){
		case brick:
			playerResources.setBrick(playerResources.getBrick() - ratio);
			bank.setBrick(bank.getBrick() + ratio);
			break;
		case ore:
			playerResources.setOre(playerResources.getOre() - ratio);
			bank.setOre(bank.getOre() + ratio);
			break;
		case sheep:
			playerResources.setSheep(playerResources.getSheep() - ratio);
			bank.setSheep(bank.getSheep() + ratio);
			break;
		case wheat:
			playerResources.setWheat(playerResources.getWheat() - ratio);
			bank.setWheat(bank.getWheat() + ratio);
			break;
		case wood:
			playerResources.setWood(playerResources.getWood() - ratio);
			bank.setWood(bank.getWood() + ratio);
			break;
		default:
			break;
		}
		
		switch(outputResource){
		case brick:
			bank.setBrick(bank.getBrick() - 1);
			playerResources.setBrick(playerResources.getBrick() + 1);
			break;
		case ore:
			bank.setOre(bank.getOre() - 1);
			playerResources.setOre(playerResources.getOre() + 1);
			break;
		case sheep:
			bank.setSheep(bank.getSheep() - 1);
			playerResources.setSheep(playerResources.getSheep() + 1);
			break;
		case wheat:
			bank.setWheat(bank.getWheat() - 1);
			playerResources.setWheat(playerResources.getWheat() + 1);
			break;
		case wood:
			bank.setWood(bank.getWood() - 1);
			playerResources.setWood(playerResources.getWood() + 1);
			break;
		default:
			break;
		}
		
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife traded " + ratio + " " + inputResource.toString() + " with the bank for 1 " + outputResource.toString() + "because nobody likes him enough to trade with him");
		}
		else{
			line.setMessage(username + " traded " + ratio + " " + inputResource.toString() + " with the bank for 1 " + outputResource.toString());
		}
//		line.setMessage(username + " traded " + ratio + " " + inputResource.toString() + " with the bank for 1 " + outputResource.toString());
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

}
