package de.nlinz.system.client.eva.interfaces;

import de.nlinz.system.client.eva.client.SocketClient;

public interface IEvaClient {
	void onConnect(final SocketClient p0);

	void onDisconnect(final SocketClient p0);

	void onDataRecieve(final SocketClient p0, final String channel, final byte[] bytes);

	void runTaskClient(final SocketClient client);
}