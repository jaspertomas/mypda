package utils;

import android.content.Context;

public class MyInitializer {
	public static void initialize(Context context)
	{
		MyApplicationContextHolder.setAppContext(context.getApplicationContext());
//		MyDownloadHelper.initialize(context.getApplicationContext());
//		ThreadDownloadManager.getInstance();
//		MyDatabaseHelper.initialize(context.getApplicationContext());
//		
		//BiofemmeSharedDatabaseHelper.initialize(context.getApplicationContext());
	}
}
