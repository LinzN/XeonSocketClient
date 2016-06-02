package de.nlinz.javaSocket.client.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.nlinz.javaSocket.client.SocketClientInitialisator;
import de.nlinz.javaSocket.client.interfaces.IDataListener;
import de.nlinz.javaSocket.client.interfaces.ITypeListener;

public class XeonSocketClientManager {

	/* Send byte[] data to connected servers */
	public static void sendData(ByteArrayOutputStream bytes) {
		SocketClientInitialisator.inst.getSocketClient().write(bytes);

	}

	/* Register a new EVA DataListener */
	public static void registerDataListener(IDataListener listener) {
		SocketClientInitialisator.inst.dataListeners.add(listener);
	}

	/* Register a new EVA TypeListener */
	public static void registerTypeListener(ITypeListener listener) {
		SocketClientInitialisator.inst.typeListeners.add(listener);
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
	public static DataInputStream readDataInput(byte[] bytes) {
		InputStream inputStream = new ByteArrayInputStream(bytes);
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		return dataInputStream;

	}
}
