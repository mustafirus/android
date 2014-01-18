package ua.net.nt.servertroll;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.jcraft.jsch.*;
import java.io.*;
import java.util.ArrayList;

public class sshexec extends AsyncTask<ArrayAdapter<String>, Void, Void>{

	protected Void doInBackground(ArrayAdapter<String>...adapters) {

		final ArrayList<String> list = new ArrayList<String>();
		try {
			JSch jsch = new JSch();

			String user = "root";
			String host = "mail";

			Session session = jsch.getSession(user, host);

			ArrayAdapter<String> adapter = adapters[0];
			String home = adapter.getContext().getFilesDir().getPath();

			jsch.setKnownHosts(home + "/.ssh/known_hosts");
			//jsch.addIdentity(home + "/.ssh/id_rsa");

			// If two machines have SSH passwordless logins setup, the following
			// line is not needed:
			session.setPassword("cdvfbg1q");
			session.connect();

			String command = "ls";
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
				adapter.add(line);
			}

			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			String err = e.toString();
			String aaa = "aaa";
			// System.out.println(e);
		}
		return null;
		
	} // end main
} // end class