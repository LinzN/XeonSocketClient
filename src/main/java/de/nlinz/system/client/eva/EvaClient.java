
package de.nlinz.system.client.eva;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.nlinz.system.client.eva.client.SocketClient;
import de.nlinz.system.client.eva.client.SocketDataEvent;
import de.nlinz.system.client.eva.client.SocketTypeEvent;
import de.nlinz.system.client.eva.interfaces.EvaEventType;
import de.nlinz.system.client.eva.interfaces.IDataListener;
import de.nlinz.system.client.eva.interfaces.IEvaClient;
import de.nlinz.system.client.eva.interfaces.ITypeListener;

public class EvaClient implements IEvaClient {

	/* Variables and instances */
	private SocketClient client;
	private String hostName;
	private int port;
	private static HashSet<IDataListener> dataListeners = new HashSet<IDataListener>();
	private static HashSet<ITypeListener> typeListeners = new HashSet<ITypeListener>();

	/* Create this new Instance of EvaClient with the IEvaClient interface */
	public EvaClient(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	/* Get this running SocketClient instance */
	public SocketClient getSocketClient() {
		return this.client;
	}

	/* Starting socketClient and prepare for server connection */
	public void start() {
		this.client = new SocketClient(this, this.hostName, this.port);
		this.runTaskClient(this.client);
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

	/* Runnable to start a new SocketClient */
	public void runTaskClient(final SocketClient client) {
		Executors.newScheduledThreadPool(1).schedule(client, 0, TimeUnit.MILLISECONDS);
	}

	/* Runnable for default type */
	public void runTask(final Runnable runnable) {
		Executors.newScheduledThreadPool(1).schedule(runnable, 0, TimeUnit.MILLISECONDS);
	}

	/* Call when the client join the network */
	public void onConnect(final SocketClient client) {
		final EvaEventType type = EvaEventType.CONNECT;
		final SocketTypeEvent event = new SocketTypeEvent(client);
		for (ITypeListener listener : typeListeners) {
			if (listener.getType() == type) {
				listener.onTypeEvent(event);
			}
		}
		SocketDebug.sendConnectSuccess(client.getSocket().getRemoteSocketAddress().toString());

	}

	/* Call when the client leave the network */
	public void onDisconnect(final SocketClient client) {
		final EvaEventType type = EvaEventType.DISCONNECT;
		final SocketTypeEvent event = new SocketTypeEvent(client);
		for (ITypeListener listener : typeListeners) {
			if (listener.getType() == type) {
				listener.onTypeEvent(event);
			}
		}
		SocketDebug.sendDisconnectSuccess(client.getSocket().getRemoteSocketAddress().toString());

	}

	/* Call when the client receive data from the SocketServer */
	public void onDataRecieve(final SocketClient client, final String channel, final byte[] bytes) {
		final SocketDataEvent event = new SocketDataEvent(client, channel, bytes);
		for (IDataListener listener : dataListeners) {
			if (listener.getChannel().equalsIgnoreCase(channel)) {
				listener.onDataRecieve(event);
			}
		}

	}

	/* Send byte[] data to connected servers */
	public void sendBytesOut(ByteArrayOutputStream bytes) {
		this.getSocketClient().write(bytes);
	}

	/* Register a new EVA DataListener */
	public static void registerDataListener(IDataListener listener) {
		dataListeners.add(listener);
	}

	/* Register a new EVA TypeListener */
	public static void registerTypeListener(ITypeListener listener) {
		typeListeners.add(listener);
	}

	/* Creating a new channel outputStream */
	public static DataOutputStream createChannel(ByteArrayOutputStream bytes, String channel) {
		DataOutputStream outStream = new DataOutputStream(bytes);
		try {
			outStream.writeUTF(channel);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return outStream;

	}

	/* Creating a new channel inputStream */
	public static DataInputStream createInputStream(byte[] bytes) {
		InputStream inputStream = new ByteArrayInputStream(bytes);
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		return dataInputStream;

	}
}
