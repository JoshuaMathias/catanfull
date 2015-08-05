package client.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.facade.ClientFacade;
import client.join.PlayerWaitingController.updateTask;
import client.misc.*;
import client.serverproxy.GamesList;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private ClientFacade clientFacade;
	private int interval;
	private Timer timer;
	private boolean joined=false;
	private ArrayList<GameInfo> games=new ArrayList<GameInfo>();
	private boolean started = false;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		clientFacade = ClientFacade.getSingleton();
		clientFacade.setJoinGameController(this);
		interval=3;
		timer=new Timer();
		timer.schedule(new updateTask(), 0, interval*1000);
	}
	
	public class updateTask extends TimerTask {

		@Override
		public void run() {
			if(started&&changeMade()&&!joined)
			{
				getJoinGameView().closeModal();
				start();
			}
		}
	}

	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	@Override
	public void start() {

		started=true;
		GamesList hub = clientFacade.gamesList();
		games = hub.getGames();
		for(int i= 0;i<games.size();i++)
		{
			ArrayList <PlayerInfo> newlist = new ArrayList<PlayerInfo>();
			for(int j = 0;j<4;j++)
			{
				
				if(!games.get(i).getPlayers().get(j).getName().isEmpty())
				{
					newlist.add(games.get(i).getPlayers().get(j));
				}
			}
			games.get(i).setPlayers(newlist);
		}
		
		GameInfo[] gamesarray = new GameInfo[games.size()];
		games.toArray(gamesarray);
		
		PlayerInfo localPlayer = new PlayerInfo ();
		localPlayer.setName(clientFacade.getName());

		getJoinGameView().setGames(gamesarray, localPlayer);
		getJoinGameView().showModal();
	}
	
	public boolean changeMade()
	{
		boolean result = false;
		ArrayList <GameInfo> updated = clientFacade.gamesList().getGames();
		
		if(games.size()<updated.size())
		{
			result = true;
		}
		else if(countPlayers(games)<countPlayers(updated))
		{
			result = true;
		}
		return result;
	}
	
	public int countPlayers(ArrayList <GameInfo> target)
	{
		int count = 0;
		for(int i = 0; i<target.size(); i++)
		{
			GameInfo temp = target.get(i);
			List <PlayerInfo> curr = temp.getPlayers();
			for(int j= 0; j<curr.size();j++)
			{
				PlayerInfo tempPlayer = curr.get(j);
				if(!tempPlayer.getName().isEmpty())
				{
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		clientFacade.createGame(getNewGameView().getRandomlyPlaceHexes(), 
				getNewGameView().getRandomlyPlaceNumbers(), getNewGameView().getUseRandomPorts(),
				getNewGameView().getTitle());
		getNewGameView().closeModal();
		start();
	}

	@Override
	public void startJoinGame(GameInfo game) {
		System.out.println("Game id you are looking at: "+game.getId());
			//Necessary because json fills the players list with empty players
			ArrayList <PlayerInfo> newlist = new ArrayList<PlayerInfo>();
			for(int j = 0;j<game.getPlayers().size();j++)
			{
				if(!game.getPlayers().get(j).getName().isEmpty())
				{
					newlist.add(game.getPlayers().get(j));
				}
			}
			game.setPlayers(newlist);
			
			//Greying out the color options already taken by other players
			for(int i = 0; i<game.getPlayers().size();i++)
			{
				PlayerInfo temp = game.getPlayers().get(i);
				if(!temp.getName().equals(clientFacade.getName()))
				{
					System.out.println(temp.getColorEnum()+temp.getName());
					getSelectColorView().setColorEnabled(temp.getColorEnum(), false);
				}
			}
			clientFacade.setCurrentGameId(game.getId());
			getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
	
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		if (clientFacade.joinGame(Integer.toString(clientFacade.getCurrentGameId()), ClientFacade.convertColor(color))) {
			// If join succeeded
			joined=true;
			clientFacade.setStopToTrue();
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
			joinAction.execute();
		} else {
			getMessageView().setMessage("Join game failed.");
			getMessageView().setTitle("Join game failed");
			getMessageView().showModal();
		}
	}
	
	public void colorReset(){
		
		for(CatanColor color: CatanColor.values()){
			getSelectColorView().setColorEnabled(color, true);
		}
	}

}

