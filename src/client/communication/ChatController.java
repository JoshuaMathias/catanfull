package client.communication;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.CatanColor;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.MessageList;
import shared.gameModel.Player;
import client.base.*;
import client.facade.ClientFacade;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	private ClientFacade clientFacade; 
	
	public ChatController(IChatView view) {
		
		super(view);
		clientFacade = ClientFacade.getSingleton();
		clientFacade.setChatController(this);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		clientFacade.sendChat(message);
	}
	
	public void initFromModel(GameModel clientModel) {
		
		ArrayList<Player> players = clientModel.getPlayers();
		MessageList messages = clientModel.getChat();
		
		ArrayList<MessageLine> messageArray = messages.getLines();
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		
		for(MessageLine message : messageArray) {
		
			String username = message.getSource();
			
			for(Player player: players) {
				
				if(player != null && player.getName().equals(username)) {
					
					CatanColor color = player.getColor();
					String messageLine = message.getMessage();
					
					entries.add(new LogEntry(color, messageLine));
				}
			}
		}
		
		getView().setEntries(entries);
	}

}

