package de.nlinz.javaSocket.client.interfaces;

import de.nlinz.javaSocket.client.events.SocketDataEvent;

public interface IDataListener {

	public String getChannel();

	public void onDataRecieve(SocketDataEvent event);
}