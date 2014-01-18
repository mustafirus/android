package ua.net.nt.servertroll;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.jcraft.jsch.*;
import java.io.*;
import java.util.ArrayList;

public class sshexec extends AsyncTask<Void, String, Void>{

	ArrayAdapter<String> adapter;

	public sshexec(ArrayAdapter<String> adapter){
		this.adapter = adapter;
	}
	
	protected Void doInBackground(Void...v) {

//		final ArrayList<String> list = new ArrayList<String>();
		try {
			JSch jsch = new JSch();
			JSch.setConfig("StrictHostKeyChecking", "no");
			
			String user = "root";
			String host = "mail";

			Session session = jsch.getSession(user, host);

			String home = adapter.getContext().getFilesDir().getPath();

			jsch.setKnownHosts(home + "/.ssh/known_hosts");
			//jsch.addIdentity(home + "/.ssh/id_rsa");

			// If two machines have SSH passwordless logins setup, the following
			// line is not needed:
			session.setPassword("cdvfbg1q");
			session.connect();

			String command = "cat /var/log/mail.info";
			// command=args[1];

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// channel.setInputStream(System.in);
			//ByteArrayInputStream in = new ByteArrayInputStream();
			channel.setInputStream(null);

			((ChannelExec) channel).setErrStream(null);

			InputStream in = channel.getInputStream();

			channel.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				publishProgress (line);
				//TODO: bulk UI updates
			}

			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			Log.e("sshexec", "Error", e);		}
		return null;
		
	} // end main
	@Override
	protected void onProgressUpdate (String... values){
		String line = values[0];
		adapter.add(line);
	}
} // end class