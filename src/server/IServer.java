package server;

import java.util.ArrayList;

import shared.definitions.*;
import shared.gameModel.*;
import shared.locations.*;
import client.serverproxy.GamesList;

/**
 * Interface for the server.
 * @author Ife's group
 *
 */
public interface IServer {
    //Do Methods

	public boolean register(String username, String password);
	
	public boolean login(String username, String password);
	
	public void createGame(boolean randomTiles,boolean randomNumbers,boolean randomPorts, String gameName);
	
	public boolean joinGame(String gameId, String color);
		
	public void sendChat(int playerIndex, String content);
	
	public void monument(int playerIndex);
	
	public void robPlayer(int playerIndex, int victimIndex, HexLocation location);
	
	public void maritimeTrade(int playerIndex, int ratio, String inputResource, String outputResource);
	
	public GamesList getGamesList();
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param number
     * @pre playerIndex and number != null, number between 2 and 12 inclusive, playerIndex between 0 and 3 inclusive
     * @post Server receives information
     */
    public void rollNumber(int playerIndex, int number);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param spot1
     * @param spot2
     * @pre playerIndex between 0 and 3 inclusive, playerIndex and spot1 and spot2 are not null
     * @post Server receives information
     */
    public void roadBuilding(int playerIndex, EdgeLocation spot1, EdgeLocation spot2);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post Server receives information
     */
    public void finishTurn(int playerIndex);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post Server receives information
     */
    public void buyDevCard(int playerIndex);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param resource1
     * @param resource2
     * @pre playerIndex between 0 and 3 inclusive and not null, both resources must not be null and one of the key words for resources
     * @post Server receives information
     */
    public void yearOfPlenty(int playerIndex, String resource1, String resource2);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param victimIndex
     * @param location
     * @pre playerIndex and victinIndex between 0 and 3 inclusive and not null, location not null
     * @post Server receives information
     */
    public void soldier(int playerIndex, int victimIndex, HexLocation location);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param resource
     * @param playerIndex
     * @pre resource must not be null and one of the key words for resources, playerIndex between 0 and 3 inclusive and not null
     * @post Server receives information
     */
    public void monopoly(String resource, int playerIndex);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param roadLocation
     * @param free
     * @pre playerIndex between 0 and 3 inclusive and not null, roadLocation not null
     * @post Server receives information
     */
    public void buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param vertexLocation
     * @param free
     * @pre playerIndex between 0 and 3 inclusive and not null, vertexLocation not null
     * @post Server receives information
     */
    public void buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param vertexLocation
     * @param free
     * @pre playerIndex between 0 and 3 inclusive and not null, vertexLocation not null
     * @post Server receives information
     */
    public void buildCity(int playerIndex, VertexLocation vertexLocation);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param offer
     * @param receiver
     * @pre playerIndex and receiver between 0 and 3 inclusive and not null, offer not null
     * @post Server receives information
     */
    public void offerTrade(int playerIndex, ResourceList offer, int receiver);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param willAccept
     * @pre playerIndex between 0 and 3 inclusive and not null
     * @post Server receives information
     */
    public void acceptTrade(int playerIndex, boolean willAccept);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @param playerIndex
     * @param discardedCards
     * @pre playerIndex between 0 and 3 inclusive and not null, discardedCards not null
     * @post Server receives information
     */
    public void discardCards(int playerIndex, ResourceList discardedCards);
    
    /**
     * Creates appropriate communication class and generates command string for Client Communicator. Sends to Server via Client Communicator.
     * @pre none
     * @post Server receives information
     */
    public GameModel getClientModel(int version);
}
