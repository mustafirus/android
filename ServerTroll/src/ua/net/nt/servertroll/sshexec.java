package ua.net.nt.servertroll;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jcraft.jsch.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class sshexec extends AsyncTask<Void, List<String>, Void> {

	ArrayAdapter<String> adapter;
	ListView listView;

	public sshexec(ArrayAdapter<String> adapter, ListView listView) {
		this.adapter = adapter;
		this.listView = listView;
	}

	protected Void doInBackground(Void... v) {

		try {
			ArrayList<String> list = new ArrayList<String>();
			JSch jsch = new JSch();
			JSch.setConfig("StrictHostKeyChecking", "no");

			String user = "root";
			String host = "mail";

			Session session = jsch.getSession(user, host);

			String home = adapter.getContext().getFilesDir().getPath();

			jsch.setKnownHosts(home + "/.ssh/known_hosts");
			// jsch.addIdentity(home + "/.ssh/id_rsa");

			// If two machines have SSH passwordless logins setup, the following
			// line is not needed:
			session.setPassword("cdvfbg1q");
			session.connect();
//			session.setTimeout(1000);
//			session.setServerAliveCountMax(0);

			String command = "tail -f -n200 /var/log/mail.info";
			// command=args[1];

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// channel.setInputStream(System.in);
			// ByteArrayInputStream in = new ByteArrayInputStream();
			channel.setInputStream(null);

			((ChannelExec) channel).setErrStream(null);

			InputStream in = channel.getInputStream();

			channel.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int m = 1;
			Log.i("sshexec", "Connected - beging loading data");
			while (!channel.isClosed()) {
				if( isCancelled()){
					Log.i("sshexec", "Cancelled - stoping load data");
					break;
				}
				while (br.ready()) {
					if ((line = br.readLine()) != null)
						list.add(line);
					if(list.size() > m)
						break;
				}
				m=500;
				if(list.size() == 0){
					Thread.sleep(1000);
					continue;
				}
				Log.i("sshexec", "Publish " + list.size() + " values");
				publishProgress(list);
				Thread.sleep(10);
				list = new ArrayList<String>();
			}

			channel.disconnect();
			session.disconnect();
			Log.i("sshexec", "Disconnected - exitting");
		} catch (Exception e) {
			Log.e("sshexec", "Error", e);
		}
		return null;

	} // end main

	@Override
	protected void onProgressUpdate(List<String>... values) {
		List<String> list = values[0];
//		Toast.makeText(adapter.getContext(),
//				String.valueOf(list.size()), Toast.LENGTH_LONG).show();
		Log.i("sshexec", "About to add " + list.size() + " values");
		adapter.addAll(list);
		Log.i("sshexec", "Added " + list.size() + " values");
		adapter.notifyDataSetChanged();
   		listView.requestLayout();
   		listView.getParent().requestLayout();
		listView.invalidate();
		//adapter
	}
} // end class