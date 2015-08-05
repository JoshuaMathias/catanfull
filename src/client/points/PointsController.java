package client.points;

import java.util.ArrayList;

import shared.gameModel.GameModel;
import shared.gameModel.Player;
import client.base.*;
import client.facade.ClientFacade;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

	private IGameFinishedView finishedView;
	private ClientFacade clientFacade;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		clientFacade = ClientFacade.getSingleton();
		
		initFromModelFirst();
		
		clientFacade.setPointsController(this);
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}
	
	public void initFromModel() {
		
		int victoryPoints = clientFacade.getPlayer().getVictoryPoints(); 
		getPointsView().setPoints(victoryPoints);
	}
	
	public void weHaveAWinner(int playerID, int winnerID) {
		
		ArrayList<Player> players=clientFacade.getPlayers();
		
		if(playerID == winnerID) {
			getFinishedView().setWinner(players.get(clientFacade.getPlayerIndex()).getName(), true);
		} 
		else {
			int winnerIndex = -1;
			for(Player player: players){
				if(player.getPlayerID() == winnerID){
					winnerIndex = player.getPlayerIndex();
				}
			}
			getFinishedView().setWinner(players.get(winnerIndex).getName(), false);
		}
		getFinishedView().showModal();
	}

	private void initFromModelFirst() {
		//<temp>		
		getPointsView().setPoints(0);
		//</temp>
	}
	
}

