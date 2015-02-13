import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket serSock = new ServerSocket(Integer.parseInt(args[0]));
			System.out.println("Waiting for connection on port "
					+ Integer.parseInt(args[0]));

			Socket cliSock = serSock.accept();
			
			ServerRecvThread srt = new ServerRecvThread(cliSock);
			srt.start();

			ServerSndThread sst = new ServerSndThread(cliSock);
			sst.start();

			System.out.println("Connected to client with address "
					+ cliSock.getInetAddress() + " @ port number "
					+ cliSock.getLocalPort());
			
			ServerRecvThread.sleep(1000000);
			ServerSndThread.sleep(1000000);

			serSock.close();
			// cliSock.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

}

class ServerRecvThread extends Thread {
	BufferedReader br;
	Socket cliSock;
	String servRecv;

	ServerRecvThread(Socket cliSock) {
		this.cliSock = cliSock;
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(
					cliSock.getInputStream()));

			System.out
					.println("Please enter something to send back to client..");
			while (true) {
				if ((servRecv = br.readLine()) != null) {
					if (servRecv.equalsIgnoreCase("EXIT")) {
						break;
					}
					// servRecv = br.readLine();
					System.out.println("From Client: " + servRecv);

				}

			}
			cliSock.close();
			System.exit(0);

		} catch (IOException ioe) {
			System.out.println(ioe.getMessage() + " Thank You!!");
		}
	}
}

class ServerSndThread extends Thread {
	BufferedReader br;
	PrintWriter pw;
	String servSnd;
	Socket cliSock;

	ServerSndThread(Socket cliSock) {
		this.cliSock = cliSock;
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			pw = new PrintWriter(cliSock.getOutputStream(), true);

			while (true) {
				if ((servSnd = br.readLine()) != null) {
					// servSnd = br.readLine();
					pw.println(servSnd);
					pw.flush();
					if(servSnd.equalsIgnoreCase("EXIT")){
						break;
					}
				}

			}
			cliSock.close();
			System.exit(0);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
