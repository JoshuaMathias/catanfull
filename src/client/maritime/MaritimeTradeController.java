package client.maritime;

import shared.definitions.*;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import client.base.*;
import client.facade.ClientFacade;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	private ClientFacade clientFacade;
	private ResourceType getResource;
	private ResourceType giveResource;
	private int ratio = -1;
	private ResourceType[] allResources = {
			ResourceType.brick,
			ResourceType.sheep,
			ResourceType.ore,
			ResourceType.wheat,
			ResourceType.wood
	};
	private ResourceType[] enabledResources = new ResourceType[5];
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);
		
		clientFacade = ClientFacade.getSingleton();

		setTradeOverlay(tradeOverlay);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
		TurnTracker turnTracker = clientFacade.getClientModel().getTurnTracker();
		getTradeOverlay().setTradeEnabled(false);
		if(clientFacade.getPlayerIndex() != turnTracker.getCurrentTurn()){
			getTradeOverlay().hideGiveOptions();
			getTradeOverlay().setStateMessage("Not Your Turn!");
		}
		else if(!turnTracker.getStatus().equals("Playing")){
			getTradeOverlay().hideGiveOptions();
			getTradeOverlay().setStateMessage("Can't trade until finished 2 turns!");
		}
		else{
			getTradeOverlay().setStateMessage("Choose which resource to give");
			if(clientFacade.canOfferBankTrade(ResourceType.sheep) != -1){enabledResources[0] = ResourceType.sheep;}
			if(clientFacade.canOfferBankTrade(ResourceType.brick) != -1){enabledResources[1] = ResourceType.brick;}
			if(clientFacade.canOfferBankTrade(ResourceType.wood) != -1){enabledResources[2] = ResourceType.wood;}
			if(clientFacade.canOfferBankTrade(ResourceType.ore) != -1){enabledResources[3] = ResourceType.ore;}
			if(clientFacade.canOfferBankTrade(ResourceType.wheat) != -1){enabledResources[4] = ResourceType.wheat;}
			getTradeOverlay().showGiveOptions(enabledResources);
		}
		
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
		clientFacade.maritimeTrade(ratio, giveResource, getResource);
		reset();
		getTradeOverlay().closeModal();
		reset();
		
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
		reset();
	}

	@Override
	public void setGetResource(ResourceType resource) {

		getResource = resource;
		System.out.println("Resource to GET: " + resource);
		getTradeOverlay().selectGetOption(resource, 1);
		ResourceList bank = clientFacade.getBank();
		if(bank.hasOne(resource)){
			getResource = resource;
			getTradeOverlay().setStateMessage("Trade!");
			getTradeOverlay().setTradeEnabled(true);
		}
		else{
			getTradeOverlay().setStateMessage("Bank has no more of that resource");
		}
	}


	@Override
	public void setGiveResource(ResourceType resource) {
		giveResource = resource;
		System.out.println("Resource to GIVE: " + resource);
		ratio = clientFacade.canOfferBankTrade(resource);
		if (ratio != -1){
			getTradeOverlay().setStateMessage("Choose which resource to get");
			getTradeOverlay().selectGiveOption(resource, ratio);
			getTradeOverlay().showGetOptions(allResources);
		}
		else{
			getTradeOverlay().setStateMessage("Not enough of that resource");
		}
	}
		

	@Override
	public void unsetGetValue() {
		getResource = null;
		getTradeOverlay().showGetOptions(allResources);
	}

	@Override
	public void unsetGiveValue() {
		giveResource = null;
		ratio = -1;
		getTradeOverlay().showGiveOptions(enabledResources);
		getTradeOverlay().hideGetOptions();
	}

	public void reset(){
		getResource = null;
		giveResource = null;
		ratio = -1;
		enabledResources = new ResourceType[5];
	}
	
}

