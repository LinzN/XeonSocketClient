package de.nlinz.javaSocket.client.events;

import java.io.ByteArrayOutputStream;

import de.nlinz.javaSocket.client.run.SocketClient;

/* This event fired if the client receive valid byte-data from the socketServer*/
public class SocketDataEvent {
	private final SocketClient client;
	private final String channel;
	private final byte[] bytes;

	public SocketDataEvent(final SocketClient client, final String channel, final byte[] bytes) {
		this.client = client;
		this.channel = channel;
		this.bytes = bytes;
	}

	/* Return the socketClient which receive the data */
	public SocketClient getClient() {
		return this.client;
	}

	/* Return the channel where the data was send */
	public String getChannel() {
		return this.channel;
	}

	/* Return the data in bytes from this event */
	public byte[] getStreamBytes() {
		return this.bytes;
	}

	/* Send bytes back to the same socketServer */
	public void sendDataBack(final ByteArrayOutputStream bytes) {
		this.client.write(bytes);
	}

}