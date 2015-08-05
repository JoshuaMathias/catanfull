package client.communication;

import java.util.*;

import client.base.*;
import client.facade.ClientFacade;
import shared.definitions.*;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.MessageList;
import shared.gameModel.Player;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	private ClientFacade clientFacade;
	
	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		clientFacade = ClientFacade.getSingleton();
		clientFacade.setGameHistoryController(this);
		
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	public void initFromModel(GameModel clientModel) {
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		ArrayList<Player> players = clientModel.getPlayers();
		
		MessageList messages = clientModel.getLog();
		ArrayList<MessageLine> lines = messages.getLines();
		
		for(MessageLine line: lines) {
			
			for(Player player : players) {
				
				
				if(player != null && player.getName().equals(line.getSource())) {
					
					String message = line.getMessage();
					CatanColor color = player.getColor();
					entries.add(new LogEntry(color, message));
				}
			}
		}
		
		
		getView().setEntries(entries);
	}
	
	private void initFromModel() {
		
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.brown, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.orange, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.brown, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.orange, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.brown, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.orange, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.brown, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.orange, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		
		getView().setEntries(entries);
	
		//</temp>
	}
	
}

