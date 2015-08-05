package client.roll;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import client.base.*;
import client.facade.ClientFacade;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;
	private ClientFacade clientFacade;
	private int interval = 5;
	private Timer timer;
	
	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {
		
		super(view);
		
		setResultView(resultView);
		clientFacade = ClientFacade.getSingleton();
		
		clientFacade.setRollController(this);
	}
	
	public void startRollGui() {
		
		interval = 5;
		timer = new Timer();
		int delay = 1000;
		int period = 1000;
		timer.scheduleAtFixedRate(new TimerTask(){
		
			public void run() {
				int newInterval = setInterval();
				
				getRollView().setMessage("Automatically rolling in " + Integer.toString(newInterval));
			}
			
		}, delay, period);
		getRollView().showModal();
	}
	
	private int setInterval() {
		
		if(interval == 0) {
			rollDice();
		}
		return --interval;
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		
		timer.cancel();
		
		getRollView().closeModal();
		
		Random random = new Random();
		int randomNum = random.nextInt((6 - 1) + 1) + 1;
		int randomNum2 = random.nextInt((6 - 1) + 1) + 1;
		
		int finalRandomNum = randomNum + randomNum2;	
		
		
		
		clientFacade.rollNumber(finalRandomNum);
		
		resultView.setRollValue(finalRandomNum);
		getResultView().showModal();
	}

}

