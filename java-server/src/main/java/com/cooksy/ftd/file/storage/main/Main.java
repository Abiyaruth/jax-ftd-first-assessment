package com.cooksy.ftd.file.storage.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksy.ftd.file.storage.server.Server;
import com.cooksys.ftd.file.storage.model.dao.FileDDao;
import com.cooksys.ftd.file.storage.model.dao.UserDao;


/**
 * Main class
 * Creates SQL & JAVA connection through JDBC
 *
 */
public class Main {
	private static Logger log = LoggerFactory.getLogger(Main.class);

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/file_storage";
	private static String username = "root";
	private static String password = "bondstone";
	private static int port = 667;

	public static void main(String[] args) throws ClassNotFoundException, IOException {

		Class.forName(driver); // register jdbc driver class
		ExecutorService executor = Executors.newCachedThreadPool(); // initialize
																	// thread
																	// pool

		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			Server server = new Server(); // initializing the server
			server.setServerSocket(new ServerSocket(port));

			server.setExecutor(executor);

			UserDao userdao = new UserDao();
			UserDao.setConn(conn);
			server.setUserdao(userdao);

			FileDDao filedao = new FileDDao();
			FileDDao.setConn(conn);
			server.setFiledoa(filedao);

			Future<?> serverFuture = executor.submit(server); // start server
																// (asynchronously)

			serverFuture.get(); // blocks until server's #run() method is done
								// (aka the server shuts down)

		} catch (SQLException | InterruptedException | ExecutionException e) {
			log.error("An error occurred during server startup. Shutting down after error log.", e);
		} finally {
			executor.shutdown(); // shutdown thread pool (see inside of try
									// block for blocking call)
		}
	}
}
