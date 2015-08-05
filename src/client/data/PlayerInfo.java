package client.data;

import client.facade.ClientFacade;
import shared.definitions.*;

/**
 * Used to pass player information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * </ul>
 * 
 */
public class PlayerInfo
{
	
	private int id;
	private int playerIndex;
	private String name;
	private CatanColor colorEnum = null;
	private String color = null;
	
	public PlayerInfo()
	{
		setId(-1);
		setPlayerIndex(-1);
		setName("");
//		setColorEnum(CatanColor.WHITE);
//		color="white";
	}
	
	
	
	public PlayerInfo(int id, int playerIndex, String name,
			CatanColor colorEnum) {
		super();
		this.id = id;
		this.playerIndex = playerIndex;
		this.name = name;
		if (colorEnum!=null) {
			EnumToColor(colorEnum);
		}
	}



	public void EnumToColor(CatanColor colorEnum) {
		switch (colorEnum) {
			case red:
				color = "red";
				break;
			case orange:
				color = "orange";
				break;
			case yellow:
				color = "yellow";
				break;
			case blue:
				color = "blue";
				break;
			case green:
				color = "green";
				break;
			case purple:
				color = "purple";
				break;
			case puce:
				color = "puce";
				break;
			case white:
				color = "white";
				break;
			case brown:
				color = "brown";
		}
	}
	
	public void ColorToEnum(String color) {
		switch (color) {
			case "red":
				colorEnum = CatanColor.red;
				break;
			case "orange":
				colorEnum = CatanColor.orange;
				break;
			case "yellow":
				colorEnum = CatanColor.yellow;
				break;
			case "blue":
				colorEnum = CatanColor.blue;
				break;
			case "green":
				colorEnum = CatanColor.green;
				break;
			case "purple":
				colorEnum = CatanColor.purple;
				break;
			case "puce":
				colorEnum = CatanColor.puce;
				break;
			case "white":
				colorEnum = CatanColor.white;
				break;
			case "brown":
				colorEnum = CatanColor.brown;
		}
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getPlayerIndex()
	{
		return playerIndex;
	}
	
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	public String getColor() {
		EnumToColor(colorEnum);
		return color;
	}

	public void setColor(String color) {
		ColorToEnum(color);		
		this.color = color;
	}

	public CatanColor getColorEnum()
	{
		ColorToEnum(color);
		return colorEnum;
	}
	
	public void setColorEnum(CatanColor color)
	{
		this.colorEnum = color;
		EnumToColor(colorEnum);
	}

	@Override
	public int hashCode()
	{
		return 31 * this.id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final PlayerInfo other = (PlayerInfo) obj;
		
		return this.id == other.id;
	}
}

