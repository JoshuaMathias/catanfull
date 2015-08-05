package abstractFactory;

import dao.IGameDao;
import dao.IUserDao;

/**
 * Interface for the persist storage
 * @author Ife's Group
 *
 */
public interface IAbstractFactory {
	
	/**
	 * duh
	 * @return User Dao
	 */
	public IUserDao getUserDao();
	
	/**
	 * duh
	 * @return Game Dao
	 */
	public IGameDao getGameDao();
	
	/**
	 * Clears respective form of storage
	 */
	public void erase();
	
	/**
	 * Begins transaction to access persistence storage
	 */
	public void startTransaction();
	
	/**
	 * query for whether a transaction has begun
	 * @return true if currently accessing storage, false if not in transaction
	 */
	public boolean inTransaction();
	
	/**
	 * closes access to persistence storage
	 * @param commit, whether to commit changes to save permanently, or not
	 */
	public void endTransaction(boolean commit);
	
}
