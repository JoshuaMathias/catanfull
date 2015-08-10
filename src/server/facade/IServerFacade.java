package server.facade;

import java.util.ArrayList;

import server.User;
import server.command.AcceptTradeCommand;
import server.command.BuildCityCommand;
import server.command.BuildRoadCommand;
import server.command.BuildSettlementCommand;
import server.command.BuyDevCardCommand;
import server.command.CreateGameCommand;
import server.command.DiscardCardsCommand;
import server.command.FinishTurnCommand;
import server.command.JoinGameCommand;
import server.command.MaritimeTradeCommand;
import server.command.MonopolyCommand;
import server.command.MonumentCommand;
import server.command.OfferTradeCommand;
import server.command.RoadBuildingCommand;
import server.command.RobPlayerCommand;
import server.command.RollNumberCommand;
import server.command.SendChatCommand;
import server.command.SoldierCommand;
import server.command.YearOfPlentyCommand;
import server.facade.IServerFacade;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.Road;
import shared.gameModel.TradeOffer;
import shared.gameModel.VertexObject;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.GameInfo;
import client.data.PlayerInfo;

public interface IServerFacade {

    /**
     * Creates a AcceptTradeCommand object and executes it.
     * 
     * @param playerIndex
     * @param willAccept
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post Trade is accepted; resources are exchanged.
     */
    // needs trade!!!!!!!!!!!!!
    public boolean acceptTrade(int playerIndex, boolean willAccept, int gameID);

    /**
     * Creates a BuildCityCommand object and executes it.
     * 
     * @param playerIndex
     * @param vertexLocation
     * @param free
     * @pre playerIndex between 0 and 3 inclusive and not null, vertexLocation
     *      not null
     * @post City is placed. Player of given playerIndex loses 3 ore and 2
     *       wheat.
     */
    public boolean buildCity(int playerIndex, VertexLocation vertexLocation,
            int gameID);

    /**
     * Creates a BuildRoadCommand object and executes it.
     * 
     * @param playerIndex
     * @param roadLocation
     * @param free
     * @pre playerIndex between 0 and 3 inclusive and not null, roadLocation not
     *      null
     * @post Road is placed. Player of given playerIndex has 1 less brick and 1
     *       less wood.
     */
    public boolean buildRoad(int playerIndex, EdgeLocation roadLocation,
            boolean free, int gameID);

    /**
     * Creates a BuildSettlementCommand object and executes it.
     * 
     * @param playerIndex
     * @param vertexLocation
     * @param free
     * @pre playerIndex between 0 and 3 inclusive and not null, vertexLocation
     *      not null
     * @post Settlement for player of given playerIndex is placed. Player loses
     *       1 wood, 1 brick, 1 wool, and 1 wheat.
     */
    public boolean buildSettlement(int playerIndex,
            VertexLocation vertexLocation, boolean free, int gameID);

    /**
     * Creates a BuildSettlementCommand object and executes it.
     * 
     * @param playerIndex
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post The given player receives a random dev card and loses 1 ore, 1
     *       wool, and 1 wheat.
     */
    public boolean buyDevCard(int playerIndex, int gameID);

    /**
     * Creates a CreateGameCommand object and executes it.
     * 
     * @param randomTiles
     * @param randomNumbers
     * @param randomPorts
     * @param gameName
     * @pre A game is created and added to the games list using the given
     *      parameters.
     */
    public GameInfo createGame(boolean randomTiles, boolean randomNumbers,
            boolean randomPorts, String gameName);

    /**
     * Creates a DiscardCardsCommand object and executes it.
     * 
     * @param playerIndex
     * @param discardedCards
     * @pre playerIndex between 0 and 3 inclusive and not null, discardedCards
     *      not null
     * @post The player no longer has the specified discardedCards.
     */
    public boolean discardCards(int playerIndex, ResourceList discardedCards,
            int gameID);

    /**
     * Creates a FinishTurnCommand object and executes it.
     * 
     * @param sender
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post The player's turn has ended and the next player's turn has begun.
     */
    public boolean finishTurn(int gameID);

    /**
     * Retures the list of games as GameInfo objects.
     * 
     * @return The list of games as a GamesList.
     * @pre There is at least 1 game.
     * @post The games list is returned.
     */
    public ArrayList<GameInfo> GamesList();
    public CatanColor convertColorToEnum(String color);
    public ResourceType convertToResourceType(String resource);
    public int getIndexFromUser(GameModel game, User user);
    
    public int getNumPlayers(GameModel game);

    /**
     * Creates a JoinGameCommand object and executes it.
     * 
     * @param gameID
     * @param color
     * @pre A game with the given ID exists. The given color is available. There
     *      aren't 4 other players already in the game.
     * @post The player has joined the game.
     */
    public boolean joinGame(String gameId, String color, User user);

    /**
     * Creates a LogInCommand object and executes it.
     * 
     * @param username
     * @param password
     * @pre A user with the given username and password exists.
     * @post The player is logged in as the user with the given username.
     */
    public int logIn(String username, String password);

    /**
     * Creates a MaritimeTradeCommand object and executes it.
     * 
     * @param playerIndex
     * @param ratio
     * @param inputResources
     * @param outputResource
     * @pre The given resources are available. The player has a settlement or
     *      city by a port of the given ratio that applies for the given
     *      resources.
     * @post The given resources are exchanged between the player and the bank.
     */
    public boolean maritimeTrade(int playerIndex, int ratio,
            String inputResourceStr, String outputResourceStr, int gameID);
   

    /**
     * Creates a MonumentCommand object and executes it.
     * 
     * @param playerIndex
     * @pre The player of the given playerIndex has a monument card and it is
     *      their turn.
     * @post The player gains a victory point.
     */
    public boolean monument(int playerIndex, int gameID);

    /**
     * Creates a OfferTradeCommand object and executes it.
     * 
     * @param playerIndex
     * @param offer
     * @param receiver
     * @pre playerIndex and receiver between 0 and 3 inclusive and not null;
     *      offer not null.
     * @post The trade is offered from the given player to the receiver.
     */
    public boolean offerTrade(int playerIndex, ResourceList offer,
            int receiver, int gameID);

    /**
     * Creates a RegisterCommand object and executes it.
     * 
     * @param username
     * @param password
     * @pre A player with the given username does not already exist.
     * @post A player with the given username and password is added to the list
     *       of users.
     */
    public int register(String username, String password);

    /**
     * Creates a RoadBuildingCommand object and executes it.
     * 
     * @param sender
     * @param spot1
     * @param spot2
     * @pre playerIndex between 0 and 3 inclusive, playerIndex and spot1 and
     *      spot2 are not null
     * @post Two roads are placed for free for the player of the given
     *       playerIndex.
     */
    public boolean roadBuilding(int playerIndex, EdgeLocation spot1,
            EdgeLocation spot2, int gameID);

    /**
     * Creates a RobPlayerCommand object and executes it.
     * 
     * @param playerIndex
     * @param victimIndex
     * @param location
     * @pre The player of the given victimIndex has a resource to be robbed.
     * @post The victim loses 1 random resource, which the robbing player gains.
     */

    public boolean robPlayer(int playerIndex, int victimIndex,
            HexLocation location, int gameID);

    /**
     * Creates a RollNumberCommand object and executes it.
     * 
     * @param sender
     * @param number
     * @pre playerIndex and number != null, number between 2 and 12 inclusive,
     *      playerIndex between 0 and 3 inclusive
     * @post A random number between 2 and 12 is rolled. Player gain their
     *       corresponding resources.
     */
    public boolean rollNumber(int sender, int number, int gameID);

    /**
     * Creates a SendChatCommand object and executes it.
     */
    public boolean sendChat(int playerIndex, String content, int gameId);

    /**
     * Creates a SoldierCommand object and executes it.
     * 
     * @param playerIndex
     * @param victimIndex
     * @param location
     * @pre The player has a soldier card, and the location is not the current
     *      location of the robber.
     * @post The robber is moved to the given location.
     */
    public boolean soldier(int playerIndex, int victimIndex,
            HexLocation location, int gameID);
    /**
     * Creates a YearOfPlentyCommand object and executes it.
     * 
     * @param playerIndex
     * @param resource1
     * @param resource2
     * @pre playerIndex between 0 and 3 inclusive and not null, both resources
     *      must not be null and one of the key words for resources.
     * @post The player of the given playerIndex has received the specified
     *       resources from the other players.
     */
    public boolean yearOfPlenty(int playerIndex, String resource1Str,
            String resource2Str, int gameID);

    /**
     * Creates a MonopolyCommand object and executes it.
     * 
     */
    public boolean monopoly(int playerIndex, String resource, int gameID);

    public void addGameToList(GameModel serverModel);

    public ArrayList<GameModel> getGamesList();

    public boolean userExist(User user);

    public boolean gameExist(int gameIndex);

    public GameModel getGameModel(int gameId);

	public void restore();
}
