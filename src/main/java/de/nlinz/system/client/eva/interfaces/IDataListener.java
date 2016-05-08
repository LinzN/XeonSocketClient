package de.nlinz.system.client.eva.interfaces;

import de.nlinz.system.client.eva.client.SocketDataEvent;

public interface IDataListener {

	public String getChannel();

	public void onDataRecieve(SocketDataEvent event);
}