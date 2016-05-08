package de.nlinz.system.client.eva.interfaces;

import de.nlinz.system.client.eva.client.SocketTypeEvent;

public interface ITypeListener {

	public EvaEventType getType();

	public void onTypeEvent(SocketTypeEvent event);
}