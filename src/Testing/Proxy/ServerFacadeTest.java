package Testing.Proxy;

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

public class ServerFacadeTest implements IServerFacade {

    private static IServerFacade serverFacade;
    private ArrayList<GameModel> gamesList = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    private ServerFacadeTest() {

    }

    public static IServerFacade getSingleton() {
        if (serverFacade == null) {
            serverFacade = new ServerFacadeTest();
        }
        return serverFacade;
    }
    
    public static void clearSingleton() {
        serverFacade = null;
    }

    /**
     * Creates a AcceptTradeCommand object and executes it.
     * 
     * @param playerIndex
     * @param willAccept
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post Trade is accepted; resources are exchanged.
     */
    // needs trade!!!!!!!!!!!!!
    public boolean acceptTrade(int playerIndex, boolean willAccept, int gameID) {
        System.out.println("acceptTrade called");
        GameModel serverModel = gamesList.get(gameID);
        if (serverModel.getTradeOffer() != null) {
//          if (serverModel.canAcceptTrade(playerIndex,
//                  serverModel.getTradeOffer())) {
                new AcceptTradeCommand(playerIndex, willAccept, serverModel)
                        .execute();
//                serverModel.incrementVersion();
                if (willAccept) {
                    return true;
                }
//          }
                //Hopefully there is another check for this same thing somewhere else
        }
//      serverModel.setTradeOffer(null);   !!!!!!!! This should fix the problem if all the commented out code in this function is uncommented
        return false;
    }

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
            int gameID) {
        System.out.println("buildCity called");
        GameModel serverModel = gamesList.get(gameID);
//      if (serverModel.canBuildCity(new VertexObject(playerIndex,
//              vertexLocation))) {
            new BuildCityCommand(playerIndex, vertexLocation, serverModel)
                    .execute();
            // serverModel.incrementVersion();
            return true;
//      }

//      return false;
    }

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
            boolean free, int gameID) {
        System.out.println("buildRoad called");
        GameModel serverModel = gamesList.get(gameID);
//      if (serverModel.canBuildRoad(new Road(playerIndex, roadLocation))) {
            new BuildRoadCommand(playerIndex, roadLocation, free, serverModel)
                    .execute();
            // serverModel.incrementVersion();
            return true;
//      }

//      return false;
    }

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
            VertexLocation vertexLocation, boolean free, int gameID) {
        GameModel serverModel = gamesList.get(gameID);
        System.out.println("buildSettlement called");
//      System.out.println("buildSettlement: playerIndex: "+playerIndex+" vertexLocation: "+vertexLocation+" free: "+free+" gameID: ");
//      if (serverModel.canBuildSettlement(new VertexObject(playerIndex,
//              vertexLocation.getNormalizedLocation()))) {
            new BuildSettlementCommand(playerIndex, vertexLocation, free,
                    serverModel).execute();
            // serverModel.incrementVersion();
            return true;
//      }

//      return false;
    }

    /**
     * Creates a BuildSettlementCommand object and executes it.
     * 
     * @param playerIndex
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post The given player receives a random dev card and loses 1 ore, 1
     *       wool, and 1 wheat.
     */
    public boolean buyDevCard(int playerIndex, int gameID) {
        System.out.println("buyDevCard called");
        GameModel serverModel = gamesList.get(gameID);
//      if (serverModel.canBuyDevCard(playerIndex)) {
            new BuyDevCardCommand(playerIndex, serverModel).execute();
            // serverModel.incrementVersion();
            return true;
//      }
//      return false;
    }

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
            boolean randomPorts, String gameName) {
        System.out.println("createGame called");
        new CreateGameCommand(randomTiles, randomNumbers, randomPorts, gameName)
                .execute();
        ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>(4);
        while (players.size() < 4)
            players.add(null);
        return new GameInfo(gamesList.size() - 1, gameName, players);
    }

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
            int gameID) {// Not sure if a canDo is needed
        System.out.println("discardCards called");
        GameModel serverModel = gamesList.get(gameID);
//      if (serverModel.mustDiscard(playerIndex)) {
            new DiscardCardsCommand(playerIndex, discardedCards, serverModel)
                    .execute();
            // serverModel.incrementVersion();
            return true;
//      }

//      return false;
    }

    /**
     * Creates a FinishTurnCommand object and executes it.
     * 
     * @param sender
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post The player's turn has ended and the next player's turn has begun.
     */
    public boolean finishTurn(int gameID) {
        GameModel serverModel = gamesList.get(gameID);
        System.out.println("finishTurn called");
//      if (serverModel.canEndTurn(serverModel.getTurnTracker()
//              .getCurrentTurn())) {
            new FinishTurnCommand(serverModel).execute();
            // serverModel.incrementVersion();
            return true;
//      }
//      return false;
    }

    /**
     * Retures the list of games as GameInfo objects.
     * 
     * @return The list of games as a GamesList.
     * @pre There is at least 1 game.
     * @post The games list is returned.
     */
    public ArrayList<GameInfo> GamesList() {
        ArrayList<GameInfo> infoList = new ArrayList<GameInfo>();
        for (int i = 0; i < gamesList.size(); i++) {
            System.out.println("GamesList called");
            ArrayList<Player> players = gamesList.get(i).getPlayers();
            ArrayList<PlayerInfo> playerInfos = new ArrayList<PlayerInfo>();
            for (int j = 0; j < 4; j++) {
                Player currentPlayer = null;
                if (j < players.size()) {
                    currentPlayer = players.get(j);
                } else {
                    currentPlayer = new Player();
                }
                playerInfos.add(new PlayerInfo(currentPlayer.getPlayerID(),
                        currentPlayer.getPlayerIndex(),
                        currentPlayer.getName(), currentPlayer.getColor()));
            }
            gamesList.get(i).getPlayers();
            GameInfo info = new GameInfo(i, gamesList.get(i).getGameName(),
                    playerInfos);
            infoList.add(info);
        }
        return infoList;
    }

    public CatanColor convertColorToEnum(String color) {
        switch (color) {
        case "red":
            return CatanColor.red;
        case "orange":
            return CatanColor.orange;
        case "yellow":
            return CatanColor.yellow;
        case "blue":
            return CatanColor.blue;
        case "green":
            return CatanColor.green;
        case "purple":
            return CatanColor.purple;
        case "puce":
            return CatanColor.puce;
        case "white":
            return CatanColor.white;
        case "brown":
            return CatanColor.brown;
        default:
            return CatanColor.white;
        }
    }

    public ResourceType convertToResourceType(String resource) {
        switch (resource) {
        case "brick":
            return ResourceType.brick;
        case "ore":
            return ResourceType.ore;
        case "sheep":
            return ResourceType.sheep;
        case "wheat":
            return ResourceType.wheat;
        case "wood":
            return ResourceType.wood;
        default:
            return ResourceType.brick;

        }
    }
    public int getIndexFromUser(GameModel game, User user) {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (user.getPlayerID() == game.getPlayers().get(i).getPlayerID()) {
                return i;
            }
        }
        return -1;
    }
    
    public int getNumPlayers(GameModel game) {
        int i = 0;
        if (game.getPlayers().size() > 3) {
            for (i = 0; i < 4; i++) {
                if (game.getPlayers().get(i) == null) {
                    return i;
                }
            }
        }
        return i;
    }

    /**
     * Creates a JoinGameCommand object and executes it.
     * 
     * @param gameID
     * @param color
     * @pre A game with the given ID exists. The given color is available. There
     *      aren't 4 other players already in the game.
     * @post The player has joined the game.
     */
    public boolean joinGame(String gameId, String color, User user) {
        System.out.println("joinGame called for user "+user.getName()+" for game "+gameId);
        int gameNum = Integer.parseInt(gameId);
//      if (gameNum >= gamesList.size()) {
//          return false;
//      }
        GameModel thisGame = gamesList.get(gameNum);
        
//      if (userExist(user)) {
            
            int index = getIndexFromUser(thisGame,user);
            int numPlayers = getNumPlayers(thisGame);
            if (numPlayers >= 4 && index == -1 ) {
                return false;
            }
            
            if (index!=-1 && numPlayers==4) {
                thisGame.getPlayers().get(index).setColor(convertColorToEnum(color));
            }
            if (thisGame.getPlayers().size() < 4) {
                JoinGameCommand joinGameCommand = new JoinGameCommand(
                        convertColorToEnum(color), user.getName(),
                        user.getPlayerID(), thisGame);
                joinGameCommand.execute();
            }
            return true;
//      } else {
//          return false;
//      }
    }

    /**
     * Creates a LogInCommand object and executes it.
     * 
     * @param username
     * @param password
     * @pre A user with the given username and password exists.
     * @post The player is logged in as the user with the given username.
     */
    public int logIn(String username, String password) {
        System.out.println("logIn called for user "+username);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(username)
                    && users.get(i).getPassword().equals(password)) {
                return i;
            }
        }
        return -1;
    }

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
            String inputResourceStr, String outputResourceStr, int gameID) {
        System.out.println("maritimeTrade called");
        ResourceType inputResource = convertToResourceType(inputResourceStr);
        ResourceType outputResource = convertToResourceType(outputResourceStr);
        GameModel serverModel = gamesList.get(gameID);
//      if (serverModel.canBankTrade(playerIndex,
//              resourceToPort(inputResource), resourceToPort(outputResource)) != -1) {
            new MaritimeTradeCommand(playerIndex, ratio, inputResource,
                    outputResource, serverModel).execute();
            // serverModel.incrementVersion();
            return true;
//      }
//      return false;
    }

    private PortType resourceToPort(ResourceType resource) {
        switch (resource) {
        case brick:
            return PortType.brick;
        case ore:
            return PortType.ore;
        case sheep:
            return PortType.sheep;
        case wheat:
            return PortType.wheat;
        case wood:
            return PortType.wood;
        default:
            return PortType.three;// should never get here

        }
    }

    /**
     * Creates a MonumentCommand object and executes it.
     * 
     * @param playerIndex
     * @pre The player of the given playerIndex has a monument card and it is
     *      their turn.
     * @post The player gains a victory point.
     */
    public boolean monument(int playerIndex, int gameID) {
        System.out.println("monument called");
        GameModel serverModel = gamesList.get(gameID);
//      if (serverModel.canPlayDevCard(playerIndex, DevCardType.MONUMENT)) {
            // might need to change in the canDo in GameModel
            new MonumentCommand(playerIndex, serverModel).execute();
            // serverModel.incrementVersion();
            return true;
//      }
//      return false;
    }

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
            int receiver, int gameID) {
        System.out.println("offerTrade called");
        GameModel serverModel = gamesList.get(gameID);
//      if (serverModel.canOfferTrade(new TradeOffer(playerIndex, receiver,
//              offer))) {
            new OfferTradeCommand(playerIndex, offer, receiver, serverModel)
                    .execute();
            // serverModel.incrementVersion();
            return true;
//      }
//      return false;
    }

    /**
     * Creates a RegisterCommand object and executes it.
     * 
     * @param username
     * @param password
     * @pre A player with the given username does not already exist.
     * @post A player with the given username and password is added to the list
     *       of users.
     */
    public int register(String username, String password) {
        System.out.println("register called for user "+username);
        for (User user : users) {
            if (user.getName().equals(username)) {
                return -1;
            }
        }
        User newUser = new User(username, password, users.size() - 1);
        users.add(newUser);
        System.out.println("Registration of " + username + " successful");
        return users.size() - 1;
    }

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
            EdgeLocation spot2, int gameID) {
        System.out.println("roadBuilding called");
        GameModel serverModel = gamesList.get(gameID);
//      if (serverModel.canPlayDevCard(playerIndex, DevCardType.ROAD_BUILD)) {
            new RoadBuildingCommand(playerIndex, spot1, spot2, serverModel)
                    .execute();
            // serverModel.incrementVersion();
            return true;
//      }
//      return false;
    }

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
            HexLocation location, int gameID) {
        System.out.println("robPlayer called");
        GameModel serverModel = gamesList.get(gameID);
        //if (serverModel.canPlaceRobber(playerIndex, 7, location)) {
            new RobPlayerCommand(playerIndex, victimIndex, location,
                    serverModel).execute();
            // serverModel.incrementVersion();
            System.out.println("Victim Index = " + victimIndex);
            return true;
        //}
    //  return false;

    }

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
    public boolean rollNumber(int sender, int number, int gameID) {
        System.out.println("rollNumber called");
        GameModel serverModel = gamesList.get(gameID);
        //if (serverModel.canRollDice(sender)) {
            new RollNumberCommand(sender, number, serverModel).execute();
            // serverModel.incrementVersion();
            return true;
        //}
        //return false;
    }

    /**
     * Creates a SendChatCommand object and executes it.
     */
    public boolean sendChat(int playerIndex, String content, int gameId) {
        System.out.println("sendChat called with message: "+content);
        GameModel serverModel = gamesList.get(gameId);
        if (!content.isEmpty()) {
            new SendChatCommand(content, playerIndex, serverModel).execute();
            // serverModel.incrementVersion();
            return true;
        }
        return false;
    }

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
            HexLocation location, int gameID) {
        System.out.println("soldier called");
        GameModel serverModel = gamesList.get(gameID);
        if (serverModel.canPlayDevCard(playerIndex, DevCardType.SOLDIER)) {
            new SoldierCommand(playerIndex, victimIndex, location, serverModel)
                    .execute();
            // serverModel.incrementVersion();
            return true;
        }
        return false;
    }

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
            String resource2Str, int gameID) {
        System.out.println("yearOfPlenty called");
        ResourceType resource1 = convertToResourceType(resource1Str);
        ResourceType resource2 = convertToResourceType(resource2Str);
        GameModel serverModel = gamesList.get(gameID);
        if (serverModel.canPlayDevCard(playerIndex, DevCardType.YEAR_OF_PLENTY)) {
            new YearOfPlentyCommand(playerIndex, resource1, resource2,
                    serverModel).execute();
            // serverModel.incrementVersion();
            return true;
        }
        return false;
    }

    /**
     * Creates a MonopolyCommand object and executes it.
     * 
     */
    public boolean monopoly(int playerIndex, String resource, int gameID) {
        System.out.println("monopoly called");
        ResourceType resourceType = convertToResourceType(resource);
        GameModel serverModel = gamesList.get(gameID);
        //TODO Can do
        //if(serverModel.canPlayDevCard(playerIndex, DevCardType.MONOPOLY)){
            new MonopolyCommand(playerIndex, resourceType, serverModel).execute();
            // serverModel.incrementVersion();
            return true;
        //}
        //return false;
    }

    public void addGameToList(GameModel serverModel) {
        gamesList.add(serverModel);
    }

    public ArrayList<GameModel> getGamesList() {
        return gamesList;
    }

    public boolean userExist(User user) {
        boolean result = false;

        for (User temp : users) {
            if (temp.getName().equals(user.getName())
                    && temp.getPassword().equals(user.getPassword())) {
                result = true;
                break;
            }
        }

        return result;
    }

    public boolean gameExist(int gameIndex) {
        boolean result = true;
        if (gamesList.size() - 1 < gameIndex) {
            result = false;
        }

        return result;
    }

    public GameModel getGameModel(int gameId) {
        System.out.println("gameModel called");
        return gamesList.get(gameId);
    }
}
