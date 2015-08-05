package abstractFactory;

import dao.IGameDao;
import dao.IUserDao;
import dao.other.OtherGameDao;
import dao.other.OtherUserDao;

public class OtherAbstractFactory implements IAbstractFactory {

	private IUserDao userDao = new OtherUserDao();
	private IGameDao gameDao = new OtherGameDao();
	
	@Override
	public IUserDao getUserDao() {
		// TODO Auto-generated method stub
		return userDao;
	}

	@Override
	public IGameDao getGameDao() {
		// TODO Auto-generated method stub
		return gameDao;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void erase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean inTransaction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void endTransaction(boolean commit) {
		// TODO Auto-generated method stub
		
	}

}
