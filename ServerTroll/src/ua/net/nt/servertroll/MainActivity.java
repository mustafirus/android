package ua.net.nt.servertroll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.LoaderManager;
import android.content.Loader;

//import android.support.v4.app.LoaderManager.LoaderCallbacks;

public class MainActivity extends Activity	
implements LoaderManager.LoaderCallbacks<List<String>> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 final ListView listview = (ListView) findViewById(R.id.listView1);
		    String[] values = new String[] { "Android1", "iPhone", "WindowsMobile",
		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
		        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
		        "Android", "iPhone", "WindowsMobile" };

		    final MyArrayAdapter adapter = new MyArrayAdapter(this,
		        R.layout.my_list_item);
		    
		    listview.setAdapter(adapter);
		    
		    getLoaderManager().initLoader(0, null, this);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		ContextWrapper c = new ContextWrapper(this);
		Toast.makeText(this, getFilesDir().getPath(), Toast.LENGTH_LONG).show();
		return true;
	}



	private class MyArrayAdapter extends ArrayAdapter<String> {

	    public MyArrayAdapter(Context context, int textViewResourceId) {
	      super(context, textViewResourceId);
	    }
	  }



	@Override
	public Loader<List<String>> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return new sshexec(this);
	}

	@Override
	public void onLoadFinished(Loader<List<String>> arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<List<String>> arg0) {
		// TODO Auto-generated method stub
		
	}


}
