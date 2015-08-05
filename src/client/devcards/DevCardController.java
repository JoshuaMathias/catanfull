package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.gameModel.DevCardList;
import client.base.*;
import client.facade.ClientFacade;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	private ClientFacade clientFacade;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		
		clientFacade = ClientFacade.getSingleton();
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		if(clientFacade.canBuyDevCard()){
			getBuyCardView().showModal();
		}
		else{
			System.out.println("Can't Buy Dev Card");
		}
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		clientFacade.buyDevCard();
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		getPlayCardView().reset();
		DevCardList playerDevCards = clientFacade.getPlayerDevCards();
		
		//Set Number of each card
		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, playerDevCards.getMonopoly());
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, playerDevCards.getMonument());
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, playerDevCards.getRoadBuilding());
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, playerDevCards.getSoldier());
		getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, playerDevCards.getYearOfPlenty());
		
		DevCardList playerOldDevCards = clientFacade.getPlayerOldDevCards();
		
		//Set enabled or disabled
		int currentTurn = clientFacade.getTurnTracker().getCurrentTurn();
		boolean isPlayerTurn = (currentTurn == clientFacade.getPlayerIndex());
		boolean playing = clientFacade.getTurnTracker().getStatus().equals("Playing");
		boolean playedDevCard = clientFacade.getPlayer().isPlayedDevCard();
		
		if(isPlayerTurn && playing && !playedDevCard){
			if(playerOldDevCards.getMonopoly() < 1){
				getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
			}
			
			if(playerOldDevCards.getRoadBuilding() < 1){
				getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
			}
			
			if(playerOldDevCards.getSoldier() < 1){
				getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
			}
			
			if(playerOldDevCards.getYearOfPlenty() < 1){
				getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
			}
			
//			if(playerDevCards.getMonument() < 1){
//				getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
//			}
//			else if(clientFacade.canPlayDevCard(DevCardType.MONUMENT)){
//				getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
//			}
		}
		else{
			getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
			getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
			getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
			getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
		}

		if(isPlayerTurn && playing){
			if(clientFacade.canPlayDevCard(DevCardType.MONUMENT) && (playerDevCards.getMonument() > 0)){
				getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
			}
		}
		else{
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
		}
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {
		getPlayCardView().reset();
		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		if(clientFacade.canPlayDevCard(DevCardType.MONOPOLY)){
			clientFacade.monopoly(clientFacade.convertResourceType(resource), clientFacade.getPlayerIndex());
		}
	}

	@Override
	public void playMonumentCard() {
		clientFacade.monument(clientFacade.getPlayerIndex());
	}

	@Override
	public void playRoadBuildCard() {
//		if(clientFacade.canPlayDevCard(cardType));
		System.out.println("Play Road Card");
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		if(clientFacade.canPlayDevCard(DevCardType.SOLDIER)){
			soldierAction.execute();
		}
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		if(clientFacade.canPlayDevCard(DevCardType.YEAR_OF_PLENTY)){
			clientFacade.yearOfPlenty(clientFacade.convertResourceType(resource1), clientFacade.convertResourceType(resource2));
		}
	}

}

