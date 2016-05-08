package de.nlinz.javaSocket.client.interfaces;

import de.nlinz.javaSocket.client.run.SocketClient;

public interface ISocketClient {
	void onConnect(final SocketClient p0);

	void onDisconnect(final SocketClient p0);

	void onDataRecieve(final SocketClient p0, final String channel, final byte[] bytes);

	void runTaskClient(final SocketClient client);
}