
package de.nlinz.javaSocket.client;

import java.io.IOException;
import java.util.HashSet;

import de.nlinz.javaSocket.client.events.SocketDataEvent;
import de.nlinz.javaSocket.client.events.SocketTypeEvent;
import de.nlinz.javaSocket.client.interfaces.IClientMask;
import de.nlinz.javaSocket.client.interfaces.IDataListener;
import de.nlinz.javaSocket.client.interfaces.ISocketClient;
import de.nlinz.javaSocket.client.interfaces.ITypeListener;
import de.nlinz.javaSocket.client.interfaces.SocketClientEventType;
import de.nlinz.javaSocket.client.run.SocketClient;

public class SocketClientInitialisator implements ISocketClient {
	public static SocketClientInitialisator inst;

	/* HashSets for storing the external eventlistener */
	public HashSet<IDataListener> dataListeners = new HashSet<IDataListener>();
	public HashSet<ITypeListener> typeListeners = new HashSet<ITypeListener>();

	/* Variables and instances */
	private SocketClient client;
	private String hostName;
	private int port;
	private IClientMask mask;

	/* Create this new Instance of EvaClient with the IEvaClient interface */
	public SocketClientInitialisator(IClientMask mask, String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
		this.mask = mask;
		inst = this;
	}

	/* Get this running SocketClient instance */
	public SocketClient getSocketClient() {
		return this.client;
	}

	/* Starting socketClient and prepare for server connection */
	public void start() {
		this.client = new SocketClient(this, this.hostName, this.port);
		this.runTask(this.client);
	}

	/* Stopping the socketClient and closing running server connections */
	public boolean stop() {
		final IOException err = this.client.interrupt();
		if (err != null) {
			SocketDebug.sendStopError();
			err.printStackTrace();
			return false;
		}
		SocketDebug.sendStopSuccess();
		return true;
	}

	/* Runnable for default type */
	@Override
	public void runTask(final Runnable runnable) {
		this.mask.clientSchedulerAsync(runnable);
	}

	/* Call when the client join the network */
	@Override
	public void onConnect(final SocketClient client) {
		final SocketClientEventType type = SocketClientEventType.CONNECT;
		final SocketTypeEvent event = new SocketTypeEvent(client);
		for (ITypeListener listener : typeListeners) {
			if (listener.getType() == type) {
				listener.onTypeEvent(event);
			}
		}
		SocketDebug.sendConnectSuccess(client.getSocket().getRemoteSocketAddress().toString());

	}

	/* Call when the client leave the network */
	@Override
	public void onDisconnect(final SocketClient client) {
		final SocketClientEventType type = SocketClientEventType.DISCONNECT;
		final SocketTypeEvent event = new SocketTypeEvent(client);
		for (ITypeListener listener : typeListeners) {
			if (listener.getType() == type) {
				listener.onTypeEvent(event);
			}
		}
		SocketDebug.sendDisconnectSuccess(client.getSocket().getRemoteSocketAddress().toString());

	}

	/* Call when the client receive data from the SocketServer */
	@Override
	public void onDataRecieve(final SocketClient client, final String channel, final byte[] bytes) {
		final SocketDataEvent event = new SocketDataEvent(client, channel, bytes);
		for (final IDataListener listener : dataListeners) {
			if (listener.getChannel().equalsIgnoreCase(channel)) {
				this.mask.clientSchedulerSync(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.onDataRecieve(event);
					}

				});
			}
		}

	}

}
