package abstractFactory;

import dao.IGameDao;
import dao.IUserDao;

public interface IAbstractFactory {
	
	public IUserDao getUserDao();
	
	public IGameDao getGameDao();
	
	public void erase();
	
	public void startTransaction();
	
	public boolean inTransaction();
	
	public void endTransaction(boolean commit);
	
}
