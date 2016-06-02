package de.nlinz.xeonSocketBukkit.mask;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.nlinz.javaSocket.client.JavaSocketClient;
import de.nlinz.javaSocket.client.interfaces.IClientMask;

public class XeonSocketBukkitMask extends JavaPlugin implements IClientMask {
	private static XeonSocketBukkitMask inst;

	private String socketHost;
	private int socketPort;
	private String serverName;

	private JavaSocketClient socketClient;

	public static XeonSocketBukkitMask inst() {
		return inst;
	}

	@Override
	public void onDisable() {
		this.socketClient.stop();
	}

	@Override
	public void onEnable() {
		inst = this;
		this.saveDefaultConfig();
		this.socketHost = this.getConfig().getString("connect.host");
		this.socketPort = this.getConfig().getInt("connect.port");
		this.serverName = this.getConfig().getString("connect.serverName");

		this.socketClient = new JavaSocketClient(this, socketHost, socketPort);
		this.socketClient.start();

	}

	public JavaSocketClient getSocketClient() {
		return this.socketClient;
	}

	public String getServerName() {
		return this.serverName;
	}

	@Override
	public void clientScheduler(Runnable runnable) {
		// TODO Auto-generated method stub
		Bukkit.getScheduler().runTaskAsynchronously(this, runnable);

	}
}