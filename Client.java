import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		try {
			Socket cliSock = new Socket(args[0], Integer.parseInt(args[1]));
			ClientRecvThread crt = new ClientRecvThread(cliSock);
			crt.start();

			ClientSendThread cst = new ClientSendThread(cliSock);
			cst.start();

			ClientRecvThread.sleep(10);
			ClientSendThread.sleep(10);

			// cliSock.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

}

class ClientRecvThread extends Thread {
	BufferedReader br;
	String cliRecv;
	Socket cliSock;

	ClientRecvThread(Socket cliSock) {
		this.cliSock = cliSock;
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(
					cliSock.getInputStream()));
			while ((cliRecv = br.readLine()) != null) {
				if (cliRecv.equalsIgnoreCase("EXIT")) {
					break;
				}
				System.out.println("From Server: " + cliRecv);

			}
			// System.out.println("Please enter something to send to server..");

			cliSock.close();
			System.exit(0);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage() + " Thank You!!");
		}
	}
}

class ClientSendThread extends Thread {
	BufferedReader br;
	PrintWriter pw;
	Socket cliSock;
	String cliSend;

	ClientSendThread(Socket cliSock) {
		this.cliSock = cliSock;
	}

	public void run() {

		if (cliSock.isConnected()) {
			try {
				br = new BufferedReader(new InputStreamReader(System.in));
				pw = new PrintWriter(cliSock.getOutputStream(), true);

				System.out.println("Connected to server with address "
						+ cliSock.getInetAddress() + " @ port "
						+ cliSock.getPort());

				System.out
						.println("Type your message to send to server..type exit to exit");

				while (true) {
					cliSend = br.readLine();
					pw.println(cliSend);
					pw.flush();
					if (cliSend.equalsIgnoreCase("EXIT")) {
						break;
					}
				}

				cliSock.close();
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
	}
}
