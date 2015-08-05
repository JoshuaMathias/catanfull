package shared.params;

/**
 * 
* Class for sending data for a create games request.
 * @author Ife's Group
 *
 */
public class CreateGamesParams 
{
	private boolean randomTiles = false;
	private boolean randomNumbers = false;
	private boolean randomPorts = false;
	private String name = "";
	public boolean isRandomTiles() {
		return randomTiles;
	}
	public void setRandomTiles(boolean randomTiles) {
		this.randomTiles = randomTiles;
	}
	public boolean isRandomNumbers() {
		return randomNumbers;
	}
	public void setRandomNumbers(boolean randomNumbers) {
		this.randomNumbers = randomNumbers;
	}
	public boolean isRandomPorts() {
		return randomPorts;
	}
	public void setRandomPorts(boolean randomPorts) {
		this.randomPorts = randomPorts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CreateGamesParams(boolean randomTiles, boolean randomNumbers,
			boolean randomPorts, String name) {
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.name = name;
	}

	
}
