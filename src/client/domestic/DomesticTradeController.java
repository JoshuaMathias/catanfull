package client.domestic;

import java.util.ArrayList;

import shared.definitions.*;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TradeOffer;
import client.base.*;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import client.misc.*;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private ClientFacade clientFacade;
	
	//what player has
	private ResourceList resources;
	
	//offer
	private int brick = 0;
	private int wheat = 0;
	private int wood = 0;
	private int ore = 0;
	private int sheep = 0;
	
	private String dirbrick = "";
	private String dirwheat = "";
	private String dirwood = "";
	private String dirore = "";
	private String dirsheep = "";
	
	private int traderIndex = -1; 
	
	private String trader = "";
	
	private PlayerInfo[] opponents;
	
	
	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		
		clientFacade = ClientFacade.getSingleton();
		clientFacade.setDomesticTradeController(this);
		
		getTradeOverlay().setCancelEnabled(true);
		
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {
				
		if(clientFacade.getTurnTracker().getCurrentTurn()==clientFacade.getPlayerIndex()&&
				clientFacade.getTurnTracker().getStatus().equals("Playing"))
		{
			PlayerInfo[] dummy = new PlayerInfo[0];
			getTradeOverlay().setPlayers(dummy);
			getTradeOverlay().reset();
			traderIndex =-1;
			//Can make trade
			
			getTradeOverlay().setStateMessage("Set the trade you want to make");
			
			ArrayList<Player> playerList = clientFacade.getPlayers();
			ArrayList<PlayerInfo> newList =new ArrayList<PlayerInfo>();
			
			for (int i = 0; i<playerList.size();i++ )
			{
				Player temp = playerList.get(i);
				if(!clientFacade.getName().equals(temp.getName()))
				{
					PlayerInfo input = new PlayerInfo();
					
					input.setColorEnum(temp.getColor());
					input.setPlayerIndex(temp.getPlayerIndex());
					input.setName(temp.getName());
					input.setId(temp.getPlayerID());
					
					newList.add(input);
				}
			}
			
			opponents = new PlayerInfo[newList.size()];
			newList.toArray(opponents);
			
			resources = clientFacade.getPlayer().getResources();
			getTradeOverlay().setPlayers(opponents);
			
			getTradeOverlay().setResourceSelectionEnabled(true);
			getTradeOverlay().setPlayerSelectionEnabled(true);
			getTradeOverlay().setTradeEnabled(false);

			getTradeOverlay().showModal();
		}
		else if(clientFacade.getTurnTracker().getCurrentTurn()!=clientFacade.getPlayerIndex()&&
				clientFacade.getTurnTracker().getStatus().equals("Playing")
				&&clientFacade.getTradeOffer()==null) //Possible problem for this condition here. other players not
		{												//involved in the trade wont be able to click on the trade 
			PlayerInfo[] dummy = new PlayerInfo[0];		//if a trade is going down.
			getTradeOverlay().setPlayers(dummy);
			getTradeOverlay().reset();
			//Can't make trade
			
			getTradeOverlay().setPlayerSelectionEnabled(false);
			getTradeOverlay().setResourceSelectionEnabled(false);
			getTradeOverlay().setCancelEnabled(true);
	
			getTradeOverlay().setStateMessage("Not your turn");
			getTradeOverlay().showModal();
		}		
		
		if(clientFacade.getTradeOffer()!=null
				&&clientFacade.getTradeOffer().getReceiver()==clientFacade.getPlayerIndex()
				&&clientFacade.getTurnTracker().getStatus().equals("Playing"))
		{
			ResourceList temp = clientFacade.getTradeOffer().getOffer();
			temp.setBrick(-1*temp.getBrick());
			temp.setWood(-1*temp.getWood());
			temp.setSheep(-1*temp.getSheep());
			temp.setWheat(-1*temp.getWheat());
			temp.setOre(-1*temp.getOre());
			clientFacade.getTradeOffer().setOffer(temp);
			
			getAcceptOverlay().reset();
			TradeOffer bid = clientFacade.getTradeOffer();
			ResourceList goods = bid.getOffer();
			ResourceList myresources = clientFacade.getPlayer().getResources();
			boolean cantrade = true;
			
			for(ResourceType rt: ResourceType.values())
			{	
				int amount = goods.getResourceAmount(rt);
				if(amount<0)
				{
					getAcceptOverlay().addGetResource(rt, -1*amount);//the negative values
				}
				else if(amount>0)
				{
					getAcceptOverlay().addGiveResource(rt, amount);//the positive values
					if(amount>myresources.getResourceAmount(rt))
					{
						cantrade = false;
					}
				}
			}
			
			getAcceptOverlay().setPlayerName(clientFacade.getPlayers().get(clientFacade.getTurnTracker().getCurrentTurn()).getName());
			getAcceptOverlay().setAcceptEnabled(cantrade);
			getAcceptOverlay().showModal();
		}		
	}
	
	public void checker()
	{
		if(clientFacade.getTradeOffer()==null&&getWaitOverlay().isModalShowing())
		{
			getWaitOverlay().closeModal();
			getAcceptOverlay().closeModal();
		}
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) 
	{
		switch(resource)
		{
			case wheat:
				if(wheat>0)
					{wheat--;}
				break;
			case brick:
				if(brick>0)
					{brick--;}
				break;
			case wood:
				if(wood>0)
					{wood--;}
				break;
			case sheep:
				if(sheep>0)
					{sheep--;}
				break;
			case ore:
				if(ore>0)
					{ore--;}
				break;
		}
		
		System.out.println("");
		System.out.println(sheep);
		System.out.println(wood);
		System.out.println(wheat);
		System.out.println(brick);
		System.out.println(ore);
		
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.wheat, 
				showUpArrow(ResourceType.wheat), showDownArrow(ResourceType.wheat));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.sheep, 
				showUpArrow(ResourceType.sheep), showDownArrow(ResourceType.sheep));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.wood, 
				showUpArrow(ResourceType.wood), showDownArrow(ResourceType.wood));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ore, 
				showUpArrow(ResourceType.ore), showDownArrow(ResourceType.ore));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.brick, 
				showUpArrow(ResourceType.brick), showDownArrow(ResourceType.brick));
		
		display();
	}
	
	@Override
	public void increaseResourceAmount(ResourceType resource) 
	{	
		switch(resource)
		{
			case wheat:
				if((dirwheat.equals("-")&&wheat<resources.getWheat())||dirwheat.equals("+"))
				{
					wheat++;
				}
				getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.wheat, 
						showUpArrow(ResourceType.wheat), showDownArrow(ResourceType.wheat));
				getTradeOverlay().setResourceAmount(ResourceType.wheat, Integer.toString(wheat));
				break;
			case brick:
				if((dirbrick.equals("-")&&brick<resources.getBrick())||dirbrick.equals("+"))
				{
					brick++;
				}
				getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.brick, 
						showUpArrow(ResourceType.brick), showDownArrow(ResourceType.brick));
				getTradeOverlay().setResourceAmount(ResourceType.brick, Integer.toString(brick));
				break;
			case wood:
				if((dirwood.equals("-")&&wood<resources.getWood())||dirwood.equals("+"))
				{
					wood++;
				}
				getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.wood, 
						showUpArrow(ResourceType.wood), showDownArrow(ResourceType.wood));
				getTradeOverlay().setResourceAmount(ResourceType.wood, Integer.toString(wood));
				break;
			case sheep:
				if((dirsheep.equals("-")&&sheep<resources.getSheep())||dirsheep.equals("+"))
				{
					sheep++;
				}
				getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.sheep, 
						showUpArrow(ResourceType.sheep), showDownArrow(ResourceType.sheep));
				getTradeOverlay().setResourceAmount(ResourceType.sheep, Integer.toString(sheep));
				break;
			case ore:
				if((dirore.equals("-")&&ore<resources.getOre())||dirore.equals("+"))
				{
					ore++;
				}
				getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ore, 
						showUpArrow(ResourceType.ore), showDownArrow(ResourceType.ore));
				getTradeOverlay().setResourceAmount(ResourceType.ore, Integer.toString(ore));
				break;
		}
		
		display();
	}
	
	public boolean showUpArrow(ResourceType resource)
	{
		switch(resource)
		{
			case wheat:
				if(wheat>=resources.getWheat()&&dirwheat.equals("-"))
					return false;
				break;
			case brick:
				if(brick==resources.getBrick()&&dirbrick.equals("-"))
					return false;
				break;
			case wood:
				if(wood==resources.getWood()&&dirwood.equals("-"))
					return false;
				break;
			case sheep:
				if(sheep==resources.getSheep()&&dirsheep.equals("-"))
					return false;
				break;
			case ore:
				if(ore==resources.getOre()&&dirore.equals("-"))
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

	

	@Override
	public void sendTradeOffer() {
		
		if(dirore.equals("-"))
		{
			ore=ore*-1;
		}
		if(dirwheat.equals("-"))
		{
			wheat=wheat*-1;
		}
		if(dirbrick.equals("-"))
		{
			brick=brick*-1;
		}
		if(dirwood.equals("-"))
		{
			wood=wood*-1;
		}
		if(dirsheep.equals("-"))
		{
			sheep=sheep*-1;
		}
		
		System.out.println("brick: "+brick);
		System.out.println("sheep: "+sheep);
		System.out.println("ore: "+ore);
		System.out.println("wood: "+wood);
		System.out.println("wheat: "+wheat);
		
		ResourceList offer = new ResourceList(-1*brick, -1*ore, -1*sheep, -1*wheat, -1*wood); //This is a stupid quick fix
		clientFacade.offerTrade(offer, traderIndex);
		getTradeOverlay().closeModal();
		
		brick = 0;
		wood = 0;
		sheep = 0; 
		ore = 0;
		wheat = 0;
		
		getWaitOverlay().setMessage("Waiting for "+trader);
		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		traderIndex = playerIndex;
		for(int i = 0;i< opponents.length;i++)
		{
			PlayerInfo temp = opponents[i];
			if(temp.getPlayerIndex()==traderIndex)
			{
				trader = temp.getName();
				break;
			}
		}
		
		display();
	}

	@Override
	public void setResourceToReceive(ResourceType resource) 
	{
		//What you get(+) Should be a -
		switch(resource)
		{
			case wheat:
				dirwheat = "+";
				break;
			case brick:
				dirbrick = "+";
				break;
			case wood:
				dirwood = "+";
				break;
			case sheep:
				dirsheep = "+";
				break;
			case ore:
				dirore = "+";
				break;
		}
		
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.wheat, 
				showUpArrow(ResourceType.wheat), showDownArrow(ResourceType.wheat));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.sheep, 
				showUpArrow(ResourceType.sheep), showDownArrow(ResourceType.sheep));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.wood, 
				showUpArrow(ResourceType.wood), showDownArrow(ResourceType.wood));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ore, 
				showUpArrow(ResourceType.ore), showDownArrow(ResourceType.ore));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.brick, 
				showUpArrow(ResourceType.brick), showDownArrow(ResourceType.brick));
		
		display();
	}

	@Override
	public void setResourceToSend(ResourceType resource) 
	{
		//What is offered (-) Should be a +
		switch(resource)
		{
			case wheat:
				if(wheat>resources.getWheat())
				{
					wheat = resources.getWheat();
					getTradeOverlay().setResourceAmount(ResourceType.wheat, Integer.toString(wheat));
				}
				dirwheat = "-";
				break;
			case brick:
				if(brick>resources.getBrick())
				{
					brick = resources.getBrick();
					getTradeOverlay().setResourceAmount(ResourceType.brick, Integer.toString(brick));
				}
				dirbrick = "-";
				break;
			case wood:
				if(wood>resources.getWood())
				{
					wood = resources.getWood();
					getTradeOverlay().setResourceAmount(ResourceType.wood, Integer.toString(wood));
				}
				dirwood = "-";
				break;
			case sheep:
				if(sheep>resources.getSheep())
				{
					sheep = resources.getSheep();
					getTradeOverlay().setResourceAmount(ResourceType.sheep, Integer.toString(sheep));
				}
				dirsheep = "-";
				break;
			case ore:
				if(ore>resources.getOre())
				{
					ore = resources.getOre();
					getTradeOverlay().setResourceAmount(ResourceType.ore, Integer.toString(ore));
				}
				dirore = "-";
				break;
		}
		
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.wheat, 
				showUpArrow(ResourceType.wheat), showDownArrow(ResourceType.wheat));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.sheep, 
				showUpArrow(ResourceType.sheep), showDownArrow(ResourceType.sheep));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.wood, 
				showUpArrow(ResourceType.wood), showDownArrow(ResourceType.wood));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ore, 
				showUpArrow(ResourceType.ore), showDownArrow(ResourceType.ore));
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.brick, 
				showUpArrow(ResourceType.brick), showDownArrow(ResourceType.brick));
		
		display();
	}

	@Override
	public void unsetResource(ResourceType resource) {	
		switch(resource)
		{
			case wheat:
				wheat=0;
				dirwheat ="";
				break;
			case brick:
				brick=0;
				dirbrick = "";
				break;
			case wood:
				wood=0;
				dirwood = "";
				break;
			case sheep:
				sheep=0;
				dirsheep = "";
				break;
			case ore:
				ore=0;
				dirore = "";
				break;
		}
		display();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		//This is a rig to reverse the offer amounts for + and - because I thought it was the other way around when
		//I was designing things earlier. So this is necessary if I dont want to go back through the whole thing.
		
		System.out.println("Before:");
		System.out.println("brick: "+clientFacade.getTradeOffer().getOffer().getBrick());
		System.out.println("sheep: "+clientFacade.getTradeOffer().getOffer().getSheep());
		System.out.println("ore: "+clientFacade.getTradeOffer().getOffer().getOre());
		System.out.println("wood: "+clientFacade.getTradeOffer().getOffer().getWood());
		System.out.println("wheat: "+clientFacade.getTradeOffer().getOffer().getWheat());
		System.out.println("");
		
		System.out.println("After:");
		System.out.println("brick: "+clientFacade.getTradeOffer().getOffer().getBrick());
		System.out.println("sheep: "+clientFacade.getTradeOffer().getOffer().getSheep());
		System.out.println("ore: "+clientFacade.getTradeOffer().getOffer().getOre());
		System.out.println("wood: "+clientFacade.getTradeOffer().getOffer().getWood());
		System.out.println("wheat: "+clientFacade.getTradeOffer().getOffer().getWheat());
		System.out.println("");
		
		clientFacade.acceptTrade(willAccept);
		getAcceptOverlay().closeModal();
		getWaitOverlay().closeModal();
		getTradeOverlay().closeModal();
	}

	public void display()
	{
		if(ore==0&&wheat==0&&brick==0&&sheep==0&&wood==0)
		{
			getTradeOverlay().setStateMessage("Set the trade you want to make");
			getTradeOverlay().setTradeEnabled(false);
		}
		else if(traderIndex==-1)
		{
			getTradeOverlay().setStateMessage("Select player");
			getTradeOverlay().setTradeEnabled(false);
		}
		else
		{
			getTradeOverlay().setStateMessage("Trade!");
			getTradeOverlay().setTradeEnabled(true);
		}
		
	}
	
	public void initialize()
	{		
		getTradeOverlay().setCancelEnabled(true);
		
		getTradeOverlay().setPlayers(opponents);
		getTradeOverlay().setPlayerSelectionEnabled(true);
		getTradeOverlay().setResourceAmount(ResourceType.sheep, Integer.toString(sheep));
		getTradeOverlay().setResourceAmount(ResourceType.wood, Integer.toString(wood));
		getTradeOverlay().setResourceAmount(ResourceType.ore, Integer.toString(ore));
		getTradeOverlay().setResourceAmount(ResourceType.brick, Integer.toString(brick));
		getTradeOverlay().setResourceAmount(ResourceType.wheat, Integer.toString(wheat));

		getTradeOverlay().setResourceSelectionEnabled(true);
		getTradeOverlay().setTradeEnabled(false);
	}
}

