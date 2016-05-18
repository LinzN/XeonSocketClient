package de.nlinz.javaSocket.client.interfaces;

import de.nlinz.javaSocket.client.events.SocketTypeEvent;

/* Interface for SocketTypeEvent*/
public interface ITypeListener {

	public SocketClientEventType getType();

	public void onTypeEvent(SocketTypeEvent event);
}