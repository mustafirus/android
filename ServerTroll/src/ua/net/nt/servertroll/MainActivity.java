package ua.net.nt.servertroll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	sshexec se;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ListView listview = (ListView) findViewById(R.id.listView1);

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				R.layout.my_list_item);
		listview.setAdapter(adapter);

		(se = new sshexec(adapter, listview)).execute();
	}

	@Override
	protected void onStop() {
		se.cancel(true);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		public StableArrayAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			setNotifyOnChange(false);
		}
	}
}
/*
public class LV extends ListView{
	public LV (Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
};
*/