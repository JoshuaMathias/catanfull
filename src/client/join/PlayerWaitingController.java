package client.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import client.base.*;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import client.poller.ServerPoller.updateTask;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	private ClientFacade clientFacade;
	private int interval;
	private Timer timer;
	private PlayerInfo[] players = new PlayerInfo[0];
	private boolean stop = true;
	
	public PlayerWaitingController(IPlayerWaitingView view) {
		super(view);
		clientFacade=ClientFacade.getSingleton();
		clientFacade.setPlayerWaitingController(this);
		interval=3;
		timer=new Timer();
		timer.schedule(new updateTask(), 0, interval*1000);
	}
	
	public class updateTask extends TimerTask {

		@Override
		public void run() {
			
			if(stop&&clientFacade.getProxy().gotCookies())
			{  
				if(players.length==4)
				{
					stop = false;
					getView().closeModal();
					getView().closeModal();
					clientFacade.refresh(); //If there are any more problems comment out this line
				}
				else if(trueSize(clientFacade.gamesList().getGames().get(clientFacade.getCurrentGameId()).getPlayers())>players.length)
				{
					clientFacade.refresh();
					getView().closeModal();
					start();
				}
			}
			if(getView().isModalShowing()&&clientFacade.getTurnTracker().getStatus().equals("Playing"))
			{
				getView().closeModal();
			}
		}
	}

	@Override
	public IPlayerWaitingView getView() {
		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
		
		List<PlayerInfo> playersList = clientFacade.gamesList().getGames().get(clientFacade.getCurrentGameId()).getPlayers();
		
		//Takes out empty players
		ArrayList<PlayerInfo> newList = new ArrayList<PlayerInfo>();
		for(int i = 0; i< playersList.size();i++)
		{
			PlayerInfo temp = playersList.get(i);
			if(!temp.getName().isEmpty())
			{
				newList.add(temp);
			}
		}
		
		System.out.println("Size of players: "+ newList.size());
		
		PlayerInfo[] players = new PlayerInfo[newList.size()];
		this.players = players;
		newList.toArray(players);
		
		getView().setPlayers(players);
		getView().showModal();
	}
	
	public int trueSize(List <PlayerInfo> list)
	{
		int count = 0;
		for(int i = 0; i< list.size();i++)
		{
			if(!list.get(i).getName().isEmpty())
			{
				count++;
			}
		}
		return count;
	}

	@Override
	public void addAI() {

		// TEMPORARY
		//getView().closeModal();
	}

	public void setStopToTrue(){
		stop = true;
	}
	
}

