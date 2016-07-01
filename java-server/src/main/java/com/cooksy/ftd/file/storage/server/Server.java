package com.cooksy.ftd.file.storage.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.file.storage.model.dao.FileDDao;
import com.cooksys.ftd.file.storage.model.dao.UserDao;
import com.cooksys.ftd.file.storage.model.dao.UserFileDao;

/**
 * Server class implements Runnable When the execution starts it waits for this
 * server run method to complete
 *
 */
public class Server implements Runnable {

	private Logger log = LoggerFactory.getLogger(Server.class);

	private ExecutorService executor;
	private ServerSocket serverSocket;
	private FileDDao filedao;
	private UserDao userdao;
	private UserFileDao userfiledao;
	private int port;

	public Server(int port) {
		super();
		this.port = port;
	}
/*
 * Starts from here when the connection is established
 */
	@Override
	public void run() {
		log.info("Server Started to run");
		try (ServerSocket severSocket = new ServerSocket(this.port)) {
			while (true) {
				Socket socket = this.serverSocket.accept();
				ClientHandler handler = this.createClientHandler(socket);
				this.executor.execute(handler);
			}
		} catch (IOException e) {
			this.log.error("The server has failed to accept a client.", e);
		}
	}

	public ClientHandler createClientHandler(Socket socket) throws IOException {
		ClientHandler handler = new ClientHandler();

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		handler.setReader(reader);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		handler.setWriter(writer);

		handler.setUserDao(userdao);
		handler.setFileDao(filedao);
		handler.setUserFileDao(userfiledao);

		return handler;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public Server() {

	}

	public FileDDao getFiledoa() {
		return filedao;
	}

	public void setFiledoa(FileDDao filedao) {
		this.filedao = filedao;
	}

	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	public UserFileDao getUserfiledao() {
		return userfiledao;
	}

	public void setUserfiledao(UserFileDao userfiledao) {
		this.userfiledao = userfiledao;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
