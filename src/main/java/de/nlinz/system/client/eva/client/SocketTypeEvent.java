package de.nlinz.system.client.eva.client;

import java.io.ByteArrayOutputStream;

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