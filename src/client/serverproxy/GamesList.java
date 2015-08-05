package client.serverproxy;

import java.util.ArrayList;

import client.data.GameInfo;

public class GamesList 
{
	ArrayList <GameInfo> games;
	
	public GamesList ()
	{
		
	}

	public ArrayList<GameInfo> getGames() {
		return games;
	}

	public void setGames(ArrayList<GameInfo> games) {
		this.games = games;
	}
}
