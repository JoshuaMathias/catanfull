package client.poller;

import java.util.Timer;
import java.util.TimerTask;

import server.IServer;
import shared.gameModel.GameModel;
import client.facade.ClientFacade;
import client.serverproxy.ServerProxy;

/**
 * This class is for regularly updating the ClientModel of the game.
 * @author Ife's Group
 *
 */
public class ServerPoller {

	private int modelVersion;
	private ServerProxy proxy;
	private int interval;
	private Timer timer;
	private ClientFacade facade;
	
	public ServerPoller(ServerProxy proxy, ClientFacade facade) {
		this.proxy=proxy;
		this.facade=facade;
		modelVersion = -1;
		interval=1;
		timer=new Timer();
		timer.schedule(new updateTask(), 0, interval*1000);
	}
	
	public class updateTask extends TimerTask {

		@Override
		public void run() {
			if(proxy.gotCookies())
			{
				updateClientModel();
			}
		}
		
	}
	
	
//	/**
//	 * Returns the ClientModel.
//	 * @return ClientModel
//	 * @pre Need for the client model.
//	 * @post The client model is returned.
//	 */
//	public ClientModel getClientModel() {
//		return proxy.getClientModel(modelVersion);
//	}
	
	/**
	 * Regularly updates the client model.
	 * @pre There is a change in the client model.
	 * @post The client model is updated.
	 */
	public void updateClientModel() 
	{
		GameModel model=proxy.getClientModel(modelVersion);
		if(model != null)
		{
			modelVersion = model.getVersion();
			facade.updateClientModel(model);
		}
	}


	public int getModelVersion() {
		return modelVersion;
	}


	public void setModelVersion(int modelVersion) {
		this.modelVersion = modelVersion;
	}


	public ServerProxy getProxy() {
		return proxy;
	}


	public void setProxy(ServerProxy proxy) {
		this.proxy = proxy;
	}


	public int getInterval() {
		return interval;
	}


	public void setInterval(int interval) {
		this.interval = interval;
	}


	public Timer getTimer() {
		return timer;
	}


	public void setTimer(Timer timer) {
		this.timer = timer;
	}


	public ClientFacade getFacade() {
		return facade;
	}


	public void setFacade(ClientFacade facade) {
		this.facade = facade;
	}
	
	
	
}
