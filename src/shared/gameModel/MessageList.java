package shared.gameModel;

import java.util.ArrayList;
/**
 * This class represents a list of messages.
 * @author Ife's group
 *
 */
public class MessageList {

	private ArrayList<MessageLine> lines = new ArrayList<>();

	public ArrayList<MessageLine> getLines() {
		return lines;
	}

	public void setLines(ArrayList<MessageLine> lines) {
		this.lines = lines;
	}
	
	public void addLine(MessageLine line){
		lines.add(line);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
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
		MessageList other = (MessageList) obj;
		if (lines == null) {
			if (other.lines != null)
				return false;
		} else if (!lines.equals(other.lines))
			return false;
		return true;
	}
}
