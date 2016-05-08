package de.nlinz.system.client.eva.client;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import de.nlinz.system.client.eva.interfaces.IEvaClient;

public class SocketClient implements Runnable {
	private String host;
	private int port;
	private Socket socket;
	private AtomicBoolean enabled;
	private IEvaClient app;
	private DataInputStream inputStream;

	public SocketClient(final IEvaClient app, final String host, final int port) {
		this.enabled = new AtomicBoolean(true);
		this.host = host;
		this.port = port;
		this.app = app;
		this.enabled.set(true);
		this.socket = new Socket();
	}

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

				while (this.enabled.get() && this.socket.isConnected()) {
					if (this.socket.isClosed()) {
						break;
					}
					final String channel = inputStream.readUTF();
					if (channel == null || channel.isEmpty()) {
						continue;
					}

					byte[] bytes = new byte[(int) inputStream.available()];
					inputStream.readFully(bytes);
					this.app.onDataRecieve(this, channel, bytes);

				}
			} catch (IOException e2) {
				this.close();
				continue;
			}

		}

	}

	public int getPort() {
		return this.port;
	}

	public String getHost() {
		return this.host;
	}

	public Socket getSocket() {
		return this.socket;
	}

	public boolean isConnectedAndOpened() {
		return this.socket.isConnected() && !this.socket.isClosed();
	}

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

	public IOException close() {
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

	public IOException interrupt() {
		this.enabled.set(false);
		return this.close();
	}

	public boolean isEnabled() {
		return this.enabled.get();
	}
}