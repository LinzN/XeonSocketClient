package de.nlinz.javaSocket.client;

/* Socket debug output for tasks*/
public class SocketDebug {
	private static String prefix = "[SocketClient]";

	public static void sendConnectSuccess(String adress) {
		System.out.println(prefix + "Connection to " + adress + " succesful!");
	}

	public static void sendDisconnectSuccess(String adress) {
		System.out.println(prefix + "Lost connection to " + adress + "!");
	}

	public static void sendStopSuccess() {
		System.out.println(prefix + "Client shutting down succesful!");
	}

	public static void sendStopError() {
		System.out.println(prefix + "Error on shutting down Client!");
	}

}
