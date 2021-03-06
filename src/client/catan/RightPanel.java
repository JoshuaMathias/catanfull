package client.catan;

import javax.swing.*;

import shared.definitions.PieceType;
import client.points.*;
import client.resources.*;
import client.base.*;
import client.map.*;
import client.devcards.*;

@SuppressWarnings("serial")
public class RightPanel extends JPanel
{
	
	private PlayDevCardView playCardView;
	private BuyDevCardView buyCardView;
	private DevCardController devCardController;
	private PointsView pointsView;
	private GameFinishedView finishedView;
	private PointsController pointsController;
	private ResourceBarView resourceView;
	private ResourceBarController resourceController;
	
	
	
	public PlayDevCardView getPlayCardView() {
		return playCardView;
	}

	public void setPlayCardView(PlayDevCardView playCardView) {
		this.playCardView = playCardView;
	}

	public BuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	public void setBuyCardView(BuyDevCardView buyCardView) {
		this.buyCardView = buyCardView;
	}

	public DevCardController getDevCardController() {
		return devCardController;
	}

	public void setDevCardController(DevCardController devCardController) {
		this.devCardController = devCardController;
	}

	public PointsView getPointsView() {
		return pointsView;
	}

	public void setPointsView(PointsView pointsView) {
		this.pointsView = pointsView;
	}

	public GameFinishedView getFinishedView() {
		return finishedView;
	}

	public void setFinishedView(GameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	public PointsController getPointsController() {
		return pointsController;
	}

	public void setPointsController(PointsController pointsController) {
		this.pointsController = pointsController;
	}

	public ResourceBarView getResourceView() {
		return resourceView;
	}

	public void setResourceView(ResourceBarView resourceView) {
		this.resourceView = resourceView;
	}

	public ResourceBarController getResourceController() {
		return resourceController;
	}

	public void setResourceController(ResourceBarController resourceController) {
		this.resourceController = resourceController;
	}

	public RightPanel(final IMapController mapController)
	{
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		// Initialize development card views and controller
		//
		playCardView = new PlayDevCardView();
		buyCardView = new BuyDevCardView();
		IAction soldierAction = new IAction() {
			@Override
			public void execute()
			{
				mapController.playSoldierCard();
			}
		};
		IAction roadAction = new IAction() {
			@Override
			public void execute()
			{
				mapController.playRoadBuildingCard();
			}
		};
		devCardController = new DevCardController(playCardView, buyCardView,
												  soldierAction, roadAction);
		playCardView.setController(devCardController);
		buyCardView.setController(devCardController);
		
		// Initialize victory point view and controller
		//
		pointsView = new PointsView();
		finishedView = new GameFinishedView();
		pointsController = new PointsController(pointsView, finishedView);
		pointsView.setController(pointsController);
		
		// Initialize resource bar view and controller
		//
		resourceView = new ResourceBarView();
		resourceController = new ResourceBarController(resourceView);
		resourceController.setElementAction(ResourceBarElement.ROAD,
											createStartMoveAction(mapController,
																  PieceType.ROAD));
		resourceController.setElementAction(ResourceBarElement.SETTLEMENT,
											createStartMoveAction(mapController,
																  PieceType.SETTLEMENT));
		resourceController.setElementAction(ResourceBarElement.CITY,
											createStartMoveAction(mapController,
																  PieceType.CITY));
		resourceController.setElementAction(ResourceBarElement.BUY_CARD,
											new IAction() {
												@Override
												public void execute()
												{
													devCardController.startBuyCard();
												}
											});
		resourceController.setElementAction(ResourceBarElement.PLAY_CARD,
											new IAction() {
												@Override
												public void execute()
												{
													devCardController.startPlayCard();
												}
											});
		resourceView.setController(resourceController);
		
		this.add(pointsView);
		this.add(resourceView);
	}
	
	private IAction createStartMoveAction(final IMapController mapController,
										  final PieceType pieceType)
	{
		
		return new IAction() {
			
			@Override
			public void execute()
			{
				boolean isFree = false;
				boolean allowDisconnected = false;
				mapController.startMove(pieceType, isFree, allowDisconnected);
			}
		};
	}
	
}

