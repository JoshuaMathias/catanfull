package client.discard;

import shared.definitions.*;
import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import client.base.*;
import client.facade.ClientFacade;
import client.misc.*;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
	private ClientFacade clientFacade;
	private ResourceList resources;
	
	private int brick =0;
	private int wheat = 0;
	private int sheep = 0;
	private int ore = 0;
	private int wood = 0;
	
	private int pickedCount = 0;
	
	private int discardAmount = 0;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		clientFacade = ClientFacade.getSingleton();
		clientFacade.setDiscardController(this);
		
		this.waitView = waitView;
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource)
		{
			case wheat:
				if(wheat<resources.getWheat()&&pickedCount<discardAmount)
					{wheat++;pickedCount++;}
				break;
			case brick:
				if(brick<resources.getBrick()&&pickedCount<discardAmount)
					{brick++;pickedCount++;}
				break;
			case wood:
				if(wood<resources.getWood()&&pickedCount<discardAmount)
					{wood++;pickedCount++;}
				break;
			case sheep:
				if(sheep<resources.getSheep()&&pickedCount<discardAmount)
					{sheep++;pickedCount++;}
				break;
			case ore:
				if(ore<resources.getOre()&&pickedCount<discardAmount)
					{ore++;pickedCount++;}
				break;
		}
		
		display();
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		switch(resource)
		{
			case wheat:
				if(wheat>0)
					{wheat--;pickedCount--;}
				break;
			case brick:
				if(brick>0)
					{brick--;pickedCount--;}
				break;
			case wood:
				if(wood>0)
					{wood--;pickedCount--;}
				break;
			case sheep:
				if(sheep>0)
					{sheep--;pickedCount--;}
				break;
			case ore:
				if(ore>0)
					{ore--;pickedCount--;}
				break;
		}
		
		display();
	}

	@Override
	public void discard() {
		ResourceList resourcesToDiscard = new ResourceList(brick, ore, sheep, wheat, wood);
		getDiscardView().closeModal();
		clientFacade.discardCards(resourcesToDiscard);
		//make discard call to server
	}
	
	public boolean showUpArrow(ResourceType resource)
	{
		switch(resource)
		{
			case wheat:
				if(wheat==resources.getWheat()||pickedCount>=discardAmount)
					return false;
				break;
			case brick:
				if(brick==resources.getBrick()||pickedCount>=discardAmount)
					return false;
				break;
			case wood:
				if(wood==resources.getWood()||pickedCount>=discardAmount)
					return false;
				break;
			case sheep:
				if(sheep==resources.getSheep()||pickedCount>=discardAmount)
					return false;
				break;
			case ore:
				if(ore==resources.getOre()||pickedCount>=discardAmount)
					return false;
				break;
		}
		return true;
	}
	
	public boolean showDownArrow(ResourceType resource)
	{
		switch(resource)
		{
			case wheat:
				if(wheat==0)
					return false;
				break;
			case brick:
				if(brick==0)
					return false;
				break;
			case wood:
				if(wood==0)
					return false;
				break;
			case sheep:
				if(sheep==0)
					return false;
				break;
			case ore:
				if(ore==0)
					return false;
				break;
		}
		return true;
	}
	
	public void display()
	{
		getDiscardView().setResourceDiscardAmount(ResourceType.wheat, wheat );
		getDiscardView().setResourceDiscardAmount(ResourceType.sheep,sheep );
		getDiscardView().setResourceDiscardAmount(ResourceType.wood, wood);
		getDiscardView().setResourceDiscardAmount(ResourceType.ore, ore );
		getDiscardView().setResourceDiscardAmount(ResourceType.brick,brick );
		
		getDiscardView().setStateMessage(pickedCount+"/"+discardAmount);
		
		if(pickedCount!=discardAmount)
		{
			getDiscardView().setDiscardButtonEnabled(false);
		}
		else
		{
			getDiscardView().setDiscardButtonEnabled(true);
		}
		
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.wheat, 
				showUpArrow(ResourceType.wheat), showDownArrow(ResourceType.wheat));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.sheep, 
				showUpArrow(ResourceType.sheep), showDownArrow(ResourceType.sheep));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.wood, 
				showUpArrow(ResourceType.wood), showDownArrow(ResourceType.wood));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ore, 
				showUpArrow(ResourceType.ore), showDownArrow(ResourceType.ore));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.brick, 
				showUpArrow(ResourceType.brick), showDownArrow(ResourceType.brick));
		
		getDiscardView().showModal();
	}

	public void beginDiscarding(){
		
		brick =0;
		wheat = 0;
		sheep = 0;
		ore = 0;
		wood = 0;
		
		pickedCount = 0;
		
		GameModel clientModel = clientFacade.getClientModel();
		Player player = clientModel.getPlayers().get(clientFacade.getPlayerIndex());
		resources = player.getResources();
	
		getDiscardView().setResourceMaxAmount(ResourceType.wheat, resources.getWheat());
		getDiscardView().setResourceMaxAmount(ResourceType.sheep, resources.getSheep());
		getDiscardView().setResourceMaxAmount(ResourceType.wood, resources.getWood());
		getDiscardView().setResourceMaxAmount(ResourceType.ore, resources.getOre());
		getDiscardView().setResourceMaxAmount(ResourceType.brick, resources.getBrick());
		
		int totalResources = resources.getWheat()+resources.getSheep()+resources.getWood()+
				resources.getOre()+resources.getBrick();
		discardAmount = totalResources/2;
		
		getDiscardView().setStateMessage(pickedCount+"/"+discardAmount);
		getDiscardView().setDiscardButtonEnabled(false);
		
		getDiscardView().setResourceDiscardAmount(ResourceType.wheat, wheat );
		getDiscardView().setResourceDiscardAmount(ResourceType.sheep,sheep );
		getDiscardView().setResourceDiscardAmount(ResourceType.wood, wood);
		getDiscardView().setResourceDiscardAmount(ResourceType.ore, ore );
		getDiscardView().setResourceDiscardAmount(ResourceType.brick,brick );
		
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.wheat, 
				showUpArrow(ResourceType.wheat), showDownArrow(ResourceType.wheat));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.sheep, 
				showUpArrow(ResourceType.sheep), showDownArrow(ResourceType.sheep));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.wood, 
				showUpArrow(ResourceType.wood), showDownArrow(ResourceType.wood));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ore, 
				showUpArrow(ResourceType.ore), showDownArrow(ResourceType.ore));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.brick, 
				showUpArrow(ResourceType.brick), showDownArrow(ResourceType.brick));
		
		getDiscardView().showModal();
	}
}

