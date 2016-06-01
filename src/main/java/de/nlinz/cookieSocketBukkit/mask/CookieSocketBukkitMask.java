package de.nlinz.cookieSocketBukkit.mask;

import org.bukkit.plugin.java.JavaPlugin;

import de.nlinz.javaSocket.client.JavaSocketClient;

public class CookieSocketBukkitMask extends JavaPlugin {
	private static CookieSocketBukkitMask inst;

	private String socketHost;
	private int socketPort;
	private String serverName;

	private JavaSocketClient socketClient;

	public static CookieSocketBukkitMask inst() {
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

		this.socketClient = new JavaSocketClient(socketHost, socketPort);
		this.socketClient.start();

	}

	public JavaSocketClient getSocketClient() {
		return this.socketClient;
	}

	public String getServerName() {
		return this.serverName;
	}
}
