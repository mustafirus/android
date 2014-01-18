package ua.net.nt.servertroll;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.jcraft.jsch.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class sshexec extends AsyncTaskLoader<List<String>> {

	final String homeDir = getContext().getFilesDir().getPath();

	public sshexec(Context context) {
		super(context);
	}

	public List<String> loadInBackground() {

		final List<String> list = new ArrayList<String>();

		try {
			// Socket s = new Socket("glv-deb7",2222);
			// Socket s = new Socket("mail",22);

			JSch jsch = new JSch();
			JSch.setConfig("StrictHostKeyChecking", "no");

			String user = "root";
			String host = "mail";

			Session session = jsch.getSession(user, host);

			jsch.setKnownHosts(homeDir + "/.ssh/known_hosts");
			// jsch.addIdentity(homeDir + "/.ssh/id_rsa");

			// If two machines have SSH passwordless logins setup, the following
			// line is not needed:
			session.setPassword("cdvfbg1q");
			session.connect(30000);

			String command = "ls";
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
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}

			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			Log.e("sshexec", "Error", e);

			String err = e.toString();
			// Toast.makeText(adapter.getContext(), e.toString(),
			// Toast.LENGTH_LONG).show();
			String aaa = "aaa";
			// System.out.println(e);
		}
		return list;

	} // end main
} // end class