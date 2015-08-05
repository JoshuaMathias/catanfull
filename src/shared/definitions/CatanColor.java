package shared.definitions;

import java.awt.Color;

public enum CatanColor
{
	red, orange, yellow, blue, green, purple, puce, white, brown;
	
	private Color color;
	
	static
	{
		red.color = new Color(227, 66, 52);
		orange.color = new Color(255, 165, 0);
		yellow.color = new Color(253, 224, 105);
		blue.color = new Color(111, 183, 246);
		green.color = new Color(109, 192, 102);
		purple.color = new Color(157, 140, 212);
		puce.color = new Color(204, 136, 153);
		white.color = new Color(223, 223, 223);
		brown.color = new Color(161, 143, 112);
	}
	
	public Color getJavaColor()
	{
		return color;
	}
}

