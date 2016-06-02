package de.nlinz.javaSocket.client.interfaces;

public interface IClientMask {
	void clientSchedulerAsync(final Runnable runnable);

	void clientSchedulerSync(final Runnable runnable);

}