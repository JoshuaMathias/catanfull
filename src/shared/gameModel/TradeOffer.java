package shared.gameModel;

import java.io.Serializable;

/**
 * This class represents a trade offer.
 * @author Ife's group
 *
 */
public class TradeOffer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4768078784481583390L;
	private int sender = -1;
	private int receiver = -1;
	private ResourceList offer;
	
	public TradeOffer(){
		
	}
	
	public TradeOffer(int sender, int receiver, ResourceList offer) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.offer = offer;
	}
	public int getSender() {
		return sender;
	}
	public void setSender(int sender) {
		this.sender = sender;
	}
	public int getReceiver() {
		return receiver;
	}
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	public ResourceList getOffer() {
		return offer;
	}
	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offer == null) ? 0 : offer.hashCode());
		result = prime * result + receiver;
		result = prime * result + sender;
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
		TradeOffer other = (TradeOffer) obj;
		if (offer == null) {
			if (other.offer != null)
				return false;
		} else if (!offer.equals(other.offer))
			return false;
		if (receiver != other.receiver)
			return false;
		if (sender != other.sender)
			return false;
		return true;
	}
}
