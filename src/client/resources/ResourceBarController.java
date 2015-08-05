package client.resources;

import java.util.*;

import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import client.base.*;
import client.facade.ClientFacade;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	private ClientFacade clientFacade;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		clientFacade = ClientFacade.getSingleton();
		clientFacade.setResourceBarController(this);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	public void initFromModel(GameModel clientModel){
		Player player = clientModel.getPlayers().get(clientFacade.getPlayerIndex());
		ResourceList playerResources = player.getResources();
		
		getView().setElementAmount(ResourceBarElement.BRICK, playerResources.getBrick());
		getView().setElementAmount(ResourceBarElement.SHEEP, playerResources.getSheep());
		getView().setElementAmount(ResourceBarElement.WOOD, playerResources.getWood());
		getView().setElementAmount(ResourceBarElement.ORE, playerResources.getOre());
		getView().setElementAmount(ResourceBarElement.WHEAT, playerResources.getWheat());
		
		getView().setElementAmount(ResourceBarElement.CITY, player.getCities());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, player.getSettlements());
		getView().setElementAmount(ResourceBarElement.ROAD, player.getRoads());
		getView().setElementAmount(ResourceBarElement.SOLDIERS, player.getSoldiers());
	}
}

