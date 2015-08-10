package server.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.definitions.ResourceType;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.locations.HexLocation;

/**
 * 
 * @author Ife's Group
 * 
 */
public class RobPlayerCommand implements Command, Serializable {

	/**
	 * 
	 */
	private String className = "RobPlayerCommand";
	private static final long serialVersionUID = -7454001069108204735L;
	private int playerIndex;
	private int victimIndex;
	private HexLocation robber;
	private transient GameModel serverModel;

	private Player player;
	private Player victim;
	private int gameID;

	public RobPlayerCommand(int playerIndex, int victimIndex,
			HexLocation robber, GameModel serverModel) {
		super();
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.robber = robber;
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
		
		player = serverModel.getPlayers().get(playerIndex);
		if (victimIndex != -1) {
			Random random = new Random();

			victim = serverModel.getPlayers().get(victimIndex);
			ResourceList victimResources = victim.getResources();

			player = serverModel.getPlayers().get(playerIndex);
			ResourceList playerResources = player.getResources();

			ArrayList<ResourceType> availableVictimResources = new ArrayList<>();
			for(int i = 0; i < victimResources.getWood(); i++){
				availableVictimResources.add(ResourceType.wood);
			}
			for(int i = 0; i < victimResources.getWheat(); i++){
				availableVictimResources.add(ResourceType.wheat);
			}
			for(int i = 0; i < victimResources.getSheep(); i++){
				availableVictimResources.add(ResourceType.sheep);
			}
			for(int i = 0; i < victimResources.getBrick(); i++){
				availableVictimResources.add(ResourceType.brick);
			}
			for(int i = 0; i < victimResources.getOre(); i++){
				availableVictimResources.add(ResourceType.ore);
			}
			Collections.shuffle(availableVictimResources);
			
			switch(availableVictimResources.get(0)){
			case brick:
				victimResources.setBrick(victimResources.getBrick() - 1);
				playerResources.setBrick(playerResources.getBrick() + 1);
				break;
			case ore:
				victimResources.setOre(victimResources.getOre() - 1);
				playerResources.setOre(playerResources.getOre() + 1);
				break;
			case sheep:
				victimResources.setSheep(victimResources.getSheep() - 1);
				playerResources.setSheep(playerResources.getSheep() + 1);
				break;
			case wheat:
				victimResources.setWheat(victimResources.getWheat() - 1);
				playerResources.setWheat(playerResources.getWheat() + 1);
				break;
			case wood:
				victimResources.setWood(victimResources.getWood() - 1);
				playerResources.setWood(playerResources.getWood() + 1);
				break;
			default:
				break;
			}
			
//			boolean noneOfThatResource = false;
//			do {
//				int resourceToSteal = random.nextInt(5);
//				switch (resourceToSteal) {
//				case 0:
//					if (victimResources.getWood() < 1) {
//						noneOfThatResource = true;
//					} else {
//						victimResources.setWood(victimResources.getWood() - 1);
//						playerResources.setWood(playerResources.getWood() + 1);
//						noneOfThatResource = false;
//					}
//					break;
//				case 1:
//					if (victimResources.getWheat() < 1) {
//						noneOfThatResource = true;
//					} else {
//						victimResources
//								.setWheat(victimResources.getWheat() - 1);
//						playerResources
//								.setWheat(playerResources.getWheat() + 1);
//						noneOfThatResource = false;
//					}
//					break;
//				case 2:
//					if (victimResources.getOre() < 1) {
//						noneOfThatResource = true;
//					} else {
//						victimResources.setOre(victimResources.getOre() - 1);
//						playerResources.setOre(playerResources.getOre() + 1);
//						noneOfThatResource = false;
//					}
//					break;
//				case 3:
//					if (victimResources.getBrick() < 1) {
//						noneOfThatResource = true;
//					} else {
//						victimResources
//								.setBrick(victimResources.getBrick() - 1);
//						playerResources
//								.setBrick(playerResources.getBrick() + 1);
//						noneOfThatResource = false;
//					}
//					break;
//				case 4:
//					if (victimResources.getSheep() < 1) {
//						noneOfThatResource = true;
//					} else {
//						victimResources
//								.setSheep(victimResources.getSheep() - 1);
//						playerResources
//								.setSheep(playerResources.getSheep() + 1);
//						noneOfThatResource = false;
//					}
//					break;
//				}
//			} while (noneOfThatResource);

			MessageLine line = new MessageLine();
			String username = player.getName();
			if (username.toLowerCase().equals("ife")
					|| username.toLowerCase().equals("ogeorge")) {
				line.setMessage("Ife's only chance of winning is by robbing from "
						+ victim.getName());
			} else {
				line.setMessage(username + " moved the robber and robbed from "
						+ victim.getName());
			}
			// line.setMessage(username + " moved the robber and robbed from " +
			// victim.getName());
			line.setSource(username);
			serverModel.getLog().addLine(line);
		} else {
			if (player != null) {
				MessageLine line = new MessageLine();
				String username = player.getName();
				line.setMessage(username
						+ " moved the robber, but did not rob from anybody");
				line.setSource(username);
				serverModel.getLog().addLine(line);
			}
		}

		serverModel.getMap().setRobber(robber);

		serverModel.getTurnTracker().setStatus("Playing");
	}

}
