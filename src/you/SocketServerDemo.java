package you;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerDemo extends Thread {

	private ServerSocket ss;

	@Override
	public void run() {
		try {
			ss = new ServerSocket();
			ss.setReuseAddress(true);
			ss.bind(new InetSocketAddress(12315));
		} catch (Exception e) {
			System.out.println(e);
		}
		while (true) {
			try {
				Socket s = ss.accept();
				InputStream in = s.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				System.out.println(br.readLine());
				s.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

}
