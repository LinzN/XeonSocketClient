package de.nlinz.javaSocket.client.events;

import java.io.ByteArrayOutputStream;

import de.nlinz.javaSocket.client.run.SocketClient;

public class SocketTypeEvent {
	private SocketClient client;

	public SocketTypeEvent(final SocketClient client) {
		this.client = client;

	}

	public SocketClient getMessenger() {
		return this.client;
	}

	public void sendDataBack(final ByteArrayOutputStream bytes) {
		this.client.write(bytes);
	}

}