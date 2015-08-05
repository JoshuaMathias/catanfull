package database;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ApacheImporter {
	
	public static void resetTables(){
		
		File emptydb = new File("EmptyCatanDB.sqlite");
		File currentdb = new File("CatanDB.sqlite");
		
		try
		{
			//	Overwrite my existing *.sqlite database with an empty one.  Now, my
			//	database is completelty empty and ready to load with data.
			FileUtils.copyFile(emptydb, currentdb);
		}
		catch (IOException e)
		{
//			logger.log(Level.SEVERE, "Unable to deal with the IOException thrown", e);
		}
	}
}
