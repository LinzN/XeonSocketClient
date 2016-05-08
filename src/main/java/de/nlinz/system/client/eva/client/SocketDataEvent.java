package de.nlinz.system.client.eva.client;

import java.io.ByteArrayOutputStream;

public class SocketDataEvent {
	private final SocketClient client;
	private final String channel;
	private final byte[] bytes;

	public SocketDataEvent(final SocketClient client, final String channel, final byte[] bytes) {
		this.client = client;
		this.channel = channel;
		this.bytes = bytes;
	}

	public SocketClient getClient() {
		return this.client;
	}

	public String getChannel() {
		return this.channel;
	}

	public byte[] getStreamBytes() {
		return this.bytes;
	}

	public void sendDataBack(final ByteArrayOutputStream bytes) {
		this.client.write(bytes);
	}

}