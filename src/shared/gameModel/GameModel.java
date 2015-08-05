package shared.gameModel;

import java.io.Serializable;
import java.util.ArrayList;

import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * Class represents model of game on client side
 * @author Ife's Group
 *
 */
public class GameModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2329752229474268973L;
	private String gameName;
	private int gameID;
	private DevCardList deck = new DevCardList(true);
	private ResourceList bank = new ResourceList(19,19,19,19,19);
	private MessageList chat = new MessageList();
	private MessageList log = new MessageList();
	private Map map;
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<Integer> discardingPlayersIndeces = null;
	private TradeOffer tradeOffer = null;
	private TurnTracker turnTracker = new TurnTracker();
	private int version = -1;
	private int winner = -1;
	private int primaryKey = -1;
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public DevCardList getDeck() {
		return deck;
	}

	public void setDeck(DevCardList deck) {
		this.deck = deck;
	}

	public ResourceList getBank() {
		return bank;
	}

	public void setBank(ResourceList bank) {
		this.bank = bank;
	}

	public MessageList getChat() {
		return chat;
	}

	public void setChat(MessageList chat) {
		this.chat = chat;
	}

	public MessageList getLog() {
		return log;
	}

	public void setLog(MessageList log) {
		this.log = log;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Integer> getDiscardingPlayersIndeces() {
		return discardingPlayersIndeces;
	}

	public void setDiscardingPlayersIndeces(
			ArrayList<Integer> discardingPlayersIndeces) {
		this.discardingPlayersIndeces = discardingPlayersIndeces;
	}

	public TradeOffer getTradeOffer() {
		return tradeOffer;
	}

	public void setTradeOffer(TradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}

	public TurnTracker getTurnTracker() {
		return turnTracker;
	}

	public void setTurnTracker(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public void incrementVersion(){
		this.version++;
		this.winner = this.checkVictoryPoints();
		this.checkForBonusPoints();
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void addPlayer(Player player){
		player.setPlayerIndex(players.size());
		players.add(player);
	}
	
	public void checkForBonusPoints(){
		// increment/decrement 2 points as appropriate
		int longestRoad = this.checkLongestRoad();
		if(longestRoad != -1){
			int previousLongestRoad = turnTracker.getLongestRoad();
			turnTracker.setLongestRoad(longestRoad);
			if(previousLongestRoad != longestRoad){
				Player player = this.getPlayers().get(longestRoad);
				player.setVictoryPoints(player.getVictoryPoints() + 2);
				if(previousLongestRoad != -1){
					Player previousPlayer = this.getPlayers().get(previousLongestRoad);
					previousPlayer.setVictoryPoints(previousPlayer.getVictoryPoints() - 2);
				}
			}
		}
		
		// increment/decrement 2 points as appropriate
		int largestArmy = this.checkLargestArmy();
		if(largestArmy != -1){
			int previousLargestArmy = turnTracker.getLargestArmy();
			turnTracker.setLargestArmy(largestArmy);
			if(previousLargestArmy != largestArmy){
				Player player = this.getPlayers().get(largestArmy);
				player.setVictoryPoints(player.getVictoryPoints() + 2);
				if(previousLargestArmy != -1){
					Player previousPlayer = this.getPlayers().get(previousLargestArmy);
					previousPlayer.setVictoryPoints(previousPlayer.getVictoryPoints() - 2);
				}
			}
		}
	}
	
	
	/**
	 * checks whether a player has the longest road
	 * @pre none
	 * @post returns integer of the index of the player with the longest road. Player has longest road if road length is 5 and more than any other player. If nobody has longest road, returns -1
	 */
	public int checkLongestRoad() {
		
		int previousLongestIndex = turnTracker.getLongestRoad();
		int playerIndex = 0;
		int lowestRoadCount = players.get(0).getRoads();
		
		for (int i = 1; i < 4; i++){
			Player player = players.get(i);
			if(player != null){
				int toCompare = player.getRoads();
				if (toCompare < lowestRoadCount){
					lowestRoadCount = toCompare;
					playerIndex = i;
				}
			}
		}
		
		if(lowestRoadCount <= 10){
			if(previousLongestIndex == -1){
				return playerIndex;
			}
			else if(players.get(previousLongestIndex).getRoads() == players.get(playerIndex).getRoads()){
				return previousLongestIndex;
			}
			else{
				return playerIndex;
			}
		}
		else{
			return -1;
		}
	}
	
	/**
	 * checks whether a player has the largest army
	 * @pre none
	 * @post returns integer representing the index of the player with the largest army. Player has largest army if they have played at least 3 soldier cards and more soldier cards than any other player. If nobody has largest army, returns -1
	 */
	public int checkLargestArmy() {
		
		int previousLargestIndex = turnTracker.getLargestArmy();
		int playerIndex = 0;
		int highestSoldierCount = players.get(0).getSoldiers();
		
		for (int i = 1; i < 4; i++){
			Player player = players.get(i);
			if(player != null){
			
				int toCompare = player.getSoldiers();
				if (toCompare > highestSoldierCount){
					highestSoldierCount = toCompare;
					playerIndex = i;
				}
			}
		}
		
		if(highestSoldierCount >= 3){
			if(previousLargestIndex == -1){
				return playerIndex;
			}
			else if(players.get(previousLargestIndex).getSoldiers() == players.get(playerIndex).getSoldiers()){
				return previousLargestIndex;
			}
			else{
				return playerIndex;
			}
		}
		else{
			return -1;
		}
	}
	
	/**
	 * checks to see if a player has 10 victory points
	 * @pre none
	 * @post returns integer representing the index of the player who has 10 or more victory points. If no player has 10 or more victory points, returns -1
	 */
	public int checkVictoryPoints() {
		
		int playerIndex = -1;
		
		for(int i = 0; i < 4 ; i++){
			if(players.get(i).getVictoryPoints() >= 10){
				return i;
			}
		}
		
		return playerIndex;
	}
	
//CanDo Methods Below********************************************************************************
	
	/**
	 * 
	 * Checks to see if the player is allowed to roll the dice
	 * 
	 * @pre none
	 * @post Must be player's turn, and player hasn't rolled yet- return true. Otherwise returns false.   
	 */
	public boolean canRollDice(int playerIndex) {
		
		boolean can = false;
		String status = turnTracker.getStatus();
		
		if (playerIndex == turnTracker.getCurrentTurn() && status.equals("Rolling")){
			can = true;
		}
		
		return can;
	}

	/**
	 * 
	 * Tests whether players can trade a number of resource cards of one type for one resource card of another
	 * type
	 * @pre none
	 * @post If player hex is next to generic harbor, allowed 3:1 at resource of their choice. Return true
	 * If player owns resource harbor, they are allowed to trade two cards indicated by Harbor type for 
	 * resource of their choice. Return true
	 * Player always allowed 4:1 with Bank for resource of their choice. Return true
	 * 
	 * Otherwise return false
	 */
	public int canBankTrade(int playerIndex, PortType offer, PortType request) {//returns -1 if cannot trade, otherwise returns ration for trade
		
//		boolean can = false;
		players.get(playerIndex).getResources();
		int whoseTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		boolean twoToOne = false;
		boolean threeToOne = false;
	
		int ratio = 4;
		if(whoseTurn == playerIndex && status.equals("Playing") && offer != PortType.three && request != PortType.three){
	
			ArrayList<VertexObject> playerSettlementsCities = map.getPlayerSettlementsCities(playerIndex);
			for(VertexObject settlementCity: playerSettlementsCities){
				int portRatio = map.matchSettlementToPortRatio(settlementCity.getLocation(), offer);
				if(portRatio == 2){
					twoToOne = true;
					break;
				}
				else if(portRatio == 3){
					threeToOne = true;
				}
			}
			
			//int ratio = 4;
			if(twoToOne){
				ratio = 2;
			}
			else if(threeToOne){
				ratio = 3;
			}
			
			ResourceList playerResources = players.get(playerIndex).getResources();
			switch(request){
				case brick:
					if(bank.getBrick() < 1){
						return -1;
					}
					break;
				case ore:
					if(bank.getOre() < 1){
						return -1;
					}
					break;
				case sheep:
					if(bank.getSheep() < 1){
						return -1;
					}
					break;
				case wheat:
					if(bank.getWheat() < 1){
						return -1;
					}
					break;
				case wood:
					if(bank.getWood() < 1){
						return -1;
					}
					break;
				default: //should never get here Throw Exception
					break;
			}
			
			switch(offer){
			case brick:
				if(playerResources.getBrick() < ratio){
					return -1;
				}
				break;
			case ore:
				if(playerResources.getOre() < ratio){
					return -1;
				}
				break;
			case sheep:
				if(playerResources.getSheep() < ratio){
					return -1;
				}
				break;
			case wheat:
				if(playerResources.getWheat() < ratio){
					return -1;
				}
				break;
			case wood:
				if(playerResources.getWood() < ratio){
					return -1;
				}
				break;
			default: //should never get here Throw Exception
				break;
		}
			
//			can = true;
		}
		return ratio; //cannot trade
		
	}
	
public int canOfferBankTrade(int playerIndex, ResourceType resourceOffer) {
		
		players.get(playerIndex).getResources();
		int whoseTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		boolean twoToOne = false;
		boolean threeToOne = false;
		
		PortType offer;
		
		switch(resourceOffer){
		case brick:
			offer = PortType.brick;
			break;
		case ore:
			offer = PortType.ore;
			break;
		case sheep:
			offer = PortType.sheep;
			break;
		case wheat:
			offer = PortType.wheat;
			break;
		case wood:
			offer = PortType.wood;
			break;
		default:
			offer = PortType.three;//should not be reachable
			break;
		
		}
		
		
		if(whoseTurn == playerIndex && status.equals("Playing") && offer != PortType.three){
			ArrayList<VertexObject> playerSettlementsCities = map.getPlayerSettlementsCities(playerIndex);
			for(VertexObject settlementCity: playerSettlementsCities){
				int portRatio = map.matchSettlementToPortRatio(settlementCity.getLocation(), offer);
				if(portRatio == 2){
					twoToOne = true;
					break;
				}
				else if(portRatio == 3){
					threeToOne = true;
				}
			}
			
			int ratio = 4;
			if(twoToOne){
				ratio = 2;
			}
			else if(threeToOne){
				ratio = 3;
			}
			
			ResourceList playerResources = players.get(playerIndex).getResources();
			switch(offer){
				case brick:
					if(playerResources.getBrick() < ratio){
						return -1;
					}
					break;
				case ore:
					if(playerResources.getOre() < ratio){
						return -1;
					}
					break;
				case sheep:
					if(playerResources.getSheep() < ratio){
						return -1;
					}
					break;
				case wheat:
					if(playerResources.getWheat() < ratio){
						return -1;
					}
					break;
				case wood:
					if(playerResources.getWood() < ratio){
						return -1;
					}
					break;
				default: //should never get here Throw Exception
					break;
			}
			
			return ratio;
		}
		
		return -1; //cannot trade
		
	}
	
	/**
	 * 
	 * Players are allowed to offer trades with other players 
	 * 
	 * @pre none
	 * @post players turn, player has the number of resources they are attempting to trade return true. Otherwise return false. 
	 */
	public boolean canOfferTrade(TradeOffer tradeOffer) {
	
		int playerIndex = tradeOffer.getSender();
		ResourceList proposedTradeList = tradeOffer.getOffer();
		String status = turnTracker.getStatus();
		int whoseTurn = turnTracker.getCurrentTurn();
	
		if(status.equals("Playing") && whoseTurn == playerIndex) {
		
			ResourceList playerResourceList = players.get(playerIndex).getResources();
			boolean test = true;
		
			if(proposedTradeList.getBrick() > playerResourceList.getBrick()) {
				return false;
			}
			
			if(proposedTradeList.getSheep() > playerResourceList.getSheep()) {
				return false;
			}
			
			if(proposedTradeList.getOre() > playerResourceList.getOre()) {
				return false;
			}
			
			if(proposedTradeList.getWood() > playerResourceList.getWood()) {
				return false;
			}
			
			if(proposedTradeList.getWheat() > playerResourceList.getWheat()) {
				return false;
			}
			return test;
		} 
		else{
			return false;
		}
    }

	/**
	 * 
	 * Deciding if the player is able to accept the trade offered to them
	 * 
	 * @pre none
	 * @post A trade is offered to the player, the player has the resources the other player is trading for, return true
	 * Otherwise return false
	 * 
	 */
	public boolean canAcceptTrade(int playerIndex, TradeOffer tradeOffer) {
	
		int receiver = tradeOffer.getReceiver();
		ResourceList proposedTradeList = tradeOffer.getOffer();
		
		if(playerIndex == receiver){
			
			ResourceList playerResourceList = players.get(playerIndex).getResources();
			boolean test = true;
			
			if(proposedTradeList.getBrick() < 0) {
				if(Math.abs(proposedTradeList.getBrick()) > playerResourceList.getBrick()){
					return false;
				}
			}
			
			if(proposedTradeList.getSheep() < 0) {
				if(Math.abs(proposedTradeList.getSheep()) > playerResourceList.getSheep()){
					return false;
				}
			}
			
			if(proposedTradeList.getOre() < 0) {
				if(Math.abs(proposedTradeList.getOre()) > playerResourceList.getOre()){
					return false;
				}
			}
			
			if(proposedTradeList.getWood() < 0) {
				if(Math.abs(proposedTradeList.getWood()) > playerResourceList.getWood()){
					return false;
				}
			}
			
			if(proposedTradeList.getWheat() < 0) {
				if(Math.abs(proposedTradeList.getWheat()) > playerResourceList.getWheat()){
					return false;
				}
			}
			return test;
		}
		else{
			return false;
		}
	}

	/**
	 *
	 * A player is attempting to buy a development card
	 * 
	 * @pre none
	 * @post A player must have the resources needed to buy the development card, the bank needs
	 * to have the development card, and must be players turn- return true
	 * Otherwise return false
	 */
	public boolean canBuyDevCard(int playerIndex) {
		
		int whoseTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		
		boolean can = false;
		if(playerIndex == whoseTurn && status.equals("Playing")){
			Player player = players.get(playerIndex);
			ResourceList playersResources= player.getResources();
			if (playersResources.getSheep() > 0 && playersResources.getOre() > 0 && playersResources.getWheat() > 0){
				can = deck.canBuyDevCard();
			}
		}
		
		return can;
	}

	public boolean hasEnoughForSettlement(ResourceList playersResources, int playerIndex){
		if(playerIndex == turnTracker.getCurrentTurn()){
			String status = turnTracker.getStatus();
			switch(status){
			case "Playing":
				if(playersResources.getBrick() < 1 || playersResources.getSheep() < 1 || playersResources.getWheat() < 1 || playersResources.getWood() < 1){
					return false;
				}
				else{
					return true;
				}
			case "FirstRound":
				return true;
			case "SecondRound":
				return true;
			default:
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * 
	 * Player attempts to build settlement
	 * 
	 * @pre none
	 * @post Must be players turn, must have the required resources to build it, must have a settlement left, must be appropriately placed on the map - return true. Otherwise return false.
	 */
	public boolean canBuildSettlement(VertexObject settlement) {
	
		int playerIndex = settlement.getOwner();
		int whoseTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		
		
		if (playerIndex == whoseTurn){
			Player player = players.get(playerIndex);
			VertexLocation settlementSpot = settlement.getLocation().getNormalizedLocation();
			switch(status){
				case "Playing":
					ResourceList playersResources = players.get(playerIndex).getResources();
					if(playersResources.getBrick() < 1 || playersResources.getSheep() < 1 || playersResources.getWheat() < 1 || playersResources.getWood() < 1){
						return false;
					}
					
					if(player.getSettlements() <= 0) {
						return false;
					}
					
					if (map.isSpotTaken(settlementSpot)){
						return false;
					}
					
					if (map.isSpotNeighbored(settlementSpot)){
						return false;
					}
					
					if (isWater(settlement)){
						return false;
					}
					
					
					return map.hasNeighboringOwnRoad(settlement);
				case "FirstRound":
					if(player.getSettlements() >= 5){
						if (map.isSpotTaken(settlementSpot)){
							return false;
						}
						else if (map.isSpotNeighbored(settlementSpot)){
							return false;
						}
						else if (isWater(settlement)){
							return false;
						}
						else{
							return true;
						}
					}
					else{
						return false;
					}
				case "SecondRound":
					if(player.getSettlements() >= 4){
						if (map.isSpotTaken(settlementSpot)){
							return false;
						}
						else if (map.isSpotNeighbored(settlementSpot)){
							return false;
						}
						else if (isWater(settlement)){
							return false;
						}
						else{
							return true;
						}
					}
					else{
						return false;
					}
				default:
					return false;
			}
		}
		return false;
	}
	
	private boolean isWater(VertexObject settlementCity){
		VertexLocation spot = settlementCity.getLocation().getNormalizedLocation();
		int x = spot.getHexLoc().getX();
		int y = spot.getHexLoc().getY();
		VertexDirection dir = spot.getDir();
		if(x > 3 || x < -3 || y > 3){return true;}
		if(x == 3 && dir == VertexDirection.NE){return true;}
		if(x == -3 && dir == VertexDirection.NW){return true;}
		if(y == -3){return true;}
		if(x == -1 && y == -2){return true;}
		if(x == -2 && y == -1){return true;}
		if(x == -3 && y == 0){return true;}
		if(x == 1 && y == 3){return true;}
		if(x == 2 && y == 2){return true;}
		if(x == 3 && y > 0){return true;}
		
		return false;
	}
	
	public boolean hasEnoughForCity(ResourceList playersResources, int playerIndex){
		if(playerIndex == turnTracker.getCurrentTurn()){
			String status = turnTracker.getStatus();
			switch(status){
			case "Playing":
				if(playersResources.getOre() < 3 || playersResources.getWheat() < 2) {
					return false;
				}
				else{
					return true;
				}
			default:
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	/**
	 * 
	 * Player attempts to build city
	 * 
	 * @pre none
	 * @post Must be players turn, must have the required resources to build it, settlement must already be built in the spot, must have a city left, must be appropriately placed on the map - return true. Otherwise return false.
	 */
	public boolean canBuildCity(VertexObject city) {
	
		int playerIndex = city.getOwner();
		int whoseTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		
		boolean can = false;
		
		if (playerIndex == whoseTurn && status.equals("Playing")){
			ResourceList playersResources = players.get(playerIndex).getResources();
			if(playersResources.getOre() < 3 || playersResources.getWheat() < 2) {
				return false;
			}
			
			if(players.get(playerIndex).getSettlements() >= 5) {
				return false;
			}
			
			VertexLocation citySpot = city.getLocation();
			
			can = map.isSpotMySettlement(citySpot, playerIndex);
		}
		return can;
	}
	
	
	public boolean hasEnoughForRoad(ResourceList playerResources, int playerIndex){
		if(playerIndex == turnTracker.getCurrentTurn()){
			String status = turnTracker.getStatus();
			switch(status){
			case "Playing":
				if(playerResources.getBrick() < 1 || playerResources.getWood() < 1){
					return false;
				}
				else{
					return true;
				}
			case "FirstRound":
				return true;
			case "SecondRound":
				return true;
			default:
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	/**
	 * 
	 * Player attempts to build a road
	 * 
	 * @pre none
	 * @post Must be a players turn, player must have required resources to build road, road must be appropriately placed on map, player must have a road left to build- return true. Otherwise false.
	 */
	public boolean canBuildRoad(Road road) {
	
		int playerIndex = road.getOwner();
		int whoseTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		
		if(playerIndex == whoseTurn){
			Player player = players.get(playerIndex);
			switch(status){
				case "Playing":
//					ResourceList playerResources = player.getResources();
//					if(playerResources.getBrick() < 1 || playerResources.getWood() < 1){
//						return false;
//					}
					
					if(player.getRoads() <= 0){
						return false;
					}
					
					if(map.isRoadHere(road.getLocation())){
						return false;
					}
					
					if(isWater(road)){
						return false;
					}
					
					return (map.hasNeighboringOwnRoad(road) || map.hasNeighboringOwnSettlement(road));
				case "FirstRound":
					if(player.getRoads() >= 15){
						if(map.isRoadHere(road.getLocation())){
							System.out.println("Road Already HERE");
							return false;
						}
						
						if(isWater(road)){
							return false;
						}
						
						return (map.hasNeighboringOwnRoad(road) || map.hasNeighboringOwnSettlement(road));
					}
					break;
				case "SecondRound":
					if(player.getRoads() >= 14){
						if(map.isRoadHere(road.getLocation())){
							return false;
						}
						
						if(isWater(road)){
							return false;
						}
						
						return (map.hasNeighboringOwnRoad(road) || map.hasNeighboringOwnSettlement(road));
					}
					break;
				default:
					return false;
			}
		}
		return false;
	}

	private boolean isWater(Road road){
		EdgeLocation edge = road.getLocation().getNormalizedLocation();
		int x = edge.getHexLoc().getX();
		int y = edge.getHexLoc().getY();
		EdgeDirection dir = edge.getDir();
		
		if(dir == EdgeDirection.N || dir == EdgeDirection.S){
			if(x == 3 || x == -3){return true;}
		}
		if(dir == EdgeDirection.NW || dir == EdgeDirection.SE){
			if(y == 3 || y == -3){return true;}
		}
		if(dir == EdgeDirection.NE || dir == EdgeDirection.SW){
			if(x == -3 && y == 0){return true;}
			if(x == -2 && y == -1){return true;}
			if(x == -1 && y == -2){return true;}
			if(x == 0 && y == -3){return true;}
			if(x == 0 && y == 3){return true;}
			if(x == 1 && y == 2){return true;}
			if(x == 2 && y == 1){return true;}
			if(x == 3 && y == 0){return true;}
		}
		
		return false;
	}
	
	/**
	 * 
	 * Player attempts to play a development card 
	 * 
	 * @pre none
	 * @post Player must have the development card, must be players turn, card can't have been received on the players turn- return true. Otherwise return false.
	 */
	public boolean canPlayDevCard(int playerIndex, DevCardType cardType) {
		
		int whoseTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		
		boolean can = true;
		
		Player player = players.get(playerIndex);
		
		if (playerIndex == whoseTurn && status.equals("Playing") && player.isPlayedDevCard() == false) {
			
			if(cardType == DevCardType.MONUMENT && player.hasMonumentCard()){
				if(player.canPlayMonument()){
					return true;
				}
				return false;
			}
			
			DevCardList playersDevCardList = player.getOldDevCards();
			
			if(playersDevCardList.size() < 1) {
				return false;
			}
			can = playersDevCardList.canPlayDevCard(bank, player, cardType);
		
		} else {
			return false;
		}		
		return can;
	}
	
	/**
	 * 
	 * Player attempts to place a robber on a hex
	 * 
	 * @pre none
	 * @post Must be players turn, player rolls a seven, must be appropriately placed- return true. Otherwise return false.
	 */
	public boolean canPlaceRobber(int playerIndex, int diceRoll, HexLocation hexLoc) {
	
		int whoseTurn = turnTracker.getCurrentTurn();
		
		if(whoseTurn == playerIndex) {
			if(hexLoc.equals(map.getRobberLocation())) {
				return false;
			}
			
			return map.canPlaceRobber(hexLoc);
			
			
		} else {
			return false;
		}
	}
	

	/**
	 * 
	 * Player attempts to steal a resource card
	 * 
	 * @pre none
	 * @post Must be players turn, player must roll a seven, target player must have a resource card- return true. Otherwise return false
	 */
	public boolean canStealResourceCard(int playerIndex, int diceRoll, int targetPlayer) {
	
		int whoseTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		
		boolean canSteal = false;
		
		if(whoseTurn == playerIndex) {//&& status.equals("Robbing")
			
			ResourceList targetResourceList = players.get(targetPlayer).getResources();
			
			if(targetResourceList.isEmpty() == true) {
				return false;
			}
			System.out.println("about to check if is touching robber");
			canSteal = map.isTouchingRobber(targetPlayer);
	
		}
		return canSteal;
	}
	
	/**
	 * 
	 * Players discard half their cards to be placed back in the bank. 
	 * If odd number, round down.
	 * 
	 * @pre none
	 * @post A seven must have been rolled, and a player has to have eight or more cards- return true. Otherwise return false.
	 */
	public boolean mustDiscardHalfCards(int diceRoll, int playerIndex) {
		
		if(diceRoll != 7) {
			return false;
		}
		
		ResourceList playerResourceList = players.get(playerIndex).getResources();
		
		if(playerResourceList.getTotal() > 7 && turnTracker.getStatus().equals("Discarding")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean mustDiscard(int playerIndex){
		Player player = players.get(playerIndex);
		ResourceList playerResourceList = player.getResources();
		
		if(playerResourceList.getTotal() > 7 && turnTracker.getStatus().equals("Discarding") && player.isDiscarded() == false) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canEndTurn(int playerIndex){
		String status = turnTracker.getStatus();
		Player player = players.get(playerIndex);
		int settlements = player.getSettlements();
		int roads = player.getRoads();
		
		switch(status){
		case "FirstRound":
			return (settlements == 4 && roads == 14);
		case "SecondRound":
			return (settlements == 3 && roads == 13);
		case "Playing":
			return true;
		default:
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bank == null) ? 0 : bank.hashCode());
		result = prime * result + ((chat == null) ? 0 : chat.hashCode());
		result = prime * result + ((deck == null) ? 0 : deck.hashCode());
		result = prime * result + ((log == null) ? 0 : log.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result
				+ ((tradeOffer == null) ? 0 : tradeOffer.hashCode());
		result = prime * result
				+ ((turnTracker == null) ? 0 : turnTracker.hashCode());
		result = prime * result + version;
		result = prime * result + winner;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameModel other = (GameModel) obj;
		if (bank == null) {
			if (other.bank != null)
				return false;
		} else if (!bank.equals(other.bank))
			return false;
		if (chat == null) {
			if (other.chat != null)
				return false;
		} else if (!chat.equals(other.chat))
			return false;
		if (deck == null) {
			if (other.deck != null)
				return false;
		} else if (!deck.equals(other.deck))
			return false;
		if (log == null) {
			if (other.log != null)
				return false;
		} else if (!log.equals(other.log))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		if (tradeOffer == null) {
			if (other.tradeOffer != null)
				return false;
		} else if (!tradeOffer.equals(other.tradeOffer))
			return false;
		if (turnTracker == null) {
			if (other.turnTracker != null)
				return false;
		} else if (!turnTracker.equals(other.turnTracker))
			return false;
		if (version != other.version)
			return false;
		if (winner != other.winner)
			return false;
		return true;
	}
	
	
}
