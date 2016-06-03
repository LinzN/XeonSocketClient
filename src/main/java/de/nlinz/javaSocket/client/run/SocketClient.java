package de.nlinz.javaSocket.client.run;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import de.nlinz.javaSocket.client.interfaces.ISocketClient;

public class SocketClient implements Runnable {
	private String host;
	private int port;
	private Socket socket;
	private AtomicBoolean enabled;
	private ISocketClient app;
	private DataInputStream inputStream;

	/* SocketClient looking for new incoming bytedata */
	public SocketClient(final ISocketClient app, final String host, final int port) {
		this.enabled = new AtomicBoolean(true);
		this.host = host;
		this.port = port;
		this.app = app;
		this.enabled.set(true);
		this.socket = new Socket();
	}

	/* First runnable for execute this class */
	@Override
	public void run() {
		while (this.enabled.get()) {
			try {
				Thread.sleep(100);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.socket = new Socket(this.host, this.port);
				this.socket.setTcpNoDelay(true);
				this.app.onConnect(this);
				this.inputStream = new DataInputStream(this.socket.getInputStream());
				/*
				 * While loop for checking if socket is connected and available
				 */
				while (this.enabled.get() && this.socket.isConnected()) {
					if (this.socket.isClosed()) {
						break;
					}
					final String channel = inputStream.readUTF();
					if (channel == null || channel.isEmpty()) {
						continue;
					}

					byte[] bytes = new byte[inputStream.available()];
					inputStream.readFully(bytes);
					this.app.onDataRecieve(this, channel, bytes);

				}
			} catch (IOException e2) {
				if (this.socket != null) {
					this.close();
				}
				continue;
			}

		}

	}

	/* Return the port of this socketConnection */
	public int getPort() {
		return this.port;
	}

	/* Return the hostname of this socketConnection */
	public String getHost() {
		return this.host;
	}

	/* Return the full socket of this connection */
	public Socket getSocket() {
		return this.socket;
	}

	/* Returns a boolean value if the socket is connected or not */
	public boolean isConnectedAndOpened() {
		return this.socket.isConnected() && !this.socket.isClosed();
	}

	/* Write bytes out to the socketServer */
	public void write(ByteArrayOutputStream bytes) {
		try {
			OutputStream out = this.socket.getOutputStream();
			out.write(bytes.toByteArray());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Close the connection. If not successfully, return the IOException */
	private IOException close() {
		if (!this.socket.isClosed()) {
			try {
				this.socket.close();
				this.app.onDisconnect(this);
			} catch (IOException e) {
				return e;
			}
		}
		return null;
	}

	/* Interrupt the connection. If not successfully, return the IOException */
	public IOException interrupt() {
		this.enabled.set(false);
		return this.close();
	}

	/* Check the connection */
	public boolean isEnabled() {
		return this.enabled.get();
	}
}