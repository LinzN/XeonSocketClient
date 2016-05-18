package de.nlinz.javaSocket.client.events;

import java.io.ByteArrayOutputStream;

import de.nlinz.javaSocket.client.run.SocketClient;

public class SocketTypeEvent {
	/*
	 * This event fired if the client execute a typeEvent like connect or
	 * disconnect
	 */
	private SocketClient client;

	public SocketTypeEvent(final SocketClient client) {
		this.client = client;

	}

	/* Return the socketClient which receive the data */
	public SocketClient getMessenger() {
		return this.client;
	}

	/*
	 * Send bytes back to the same socketServer where the typeEvent was called
	 */
	public void sendDataBack(final ByteArrayOutputStream bytes) {
		this.client.write(bytes);
	}

}