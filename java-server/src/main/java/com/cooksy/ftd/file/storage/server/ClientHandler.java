package com.cooksy.ftd.file.storage.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.file.storage.model.User;
import com.cooksys.ftd.file.storage.model.api.Abstract;
import com.cooksys.ftd.file.storage.model.api.ClientMessage;
import com.cooksys.ftd.file.storage.model.api.Login;
import com.cooksys.ftd.file.storage.model.api.Register;
import com.cooksys.ftd.file.storage.model.api.Response;
import com.cooksys.ftd.file.storage.model.dao.FileDDao;
import com.cooksys.ftd.file.storage.model.dao.UserDao;

/**
 * Handles the client in server side
 *
 */
public class ClientHandler implements Runnable {
	Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private BufferedReader reader;
	private PrintWriter writer;
	private boolean closed;
	Map<String, Object> properties = new HashMap<String, Object>();
	private UserDao userDao;
	private FileDDao fileDao;

	@Override
	public void run() {
		properties.put("eclipselink.media-type", "application/json");
		String input = "";
		try {
			while (!closed) {
				log.info("Waiting for client input...");

				ClientMessage clientMessage = getClientMessage();

				log.info("clientMessage: data={}, message={}", clientMessage.getData(), clientMessage.getMessage());

				switch (clientMessage.getMessage()) {
				case "register":
					registerUser(clientMessage);
					break;
				case "login":
					loginUser(clientMessage);
					break;
				default:
				}
			}
		} catch (IOException | JAXBException e) {
			log.error("Error processing user input " + input + ".", e);
			writer.write("{\"response\":{\"message\":\"*error*error\"}}");
			closed = true;
		} catch (SQLException e) {
			log.error("Error retreiving information from SQL database.", e);
			writer.write("{\"response\":{\"message\":\"*error*error\"}}");
			closed = true;
		}
	}

	// Handles the client Messages and unmarshalls
	public ClientMessage getClientMessage() throws IOException, JAXBException {
		String input = reader.readLine();
		log.info("Input: {}", input);
		JAXBContext jc = JAXBContext.newInstance(new Class[] { ClientMessage.class }, properties);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(JAXBContextProperties.MEDIA_TYPE, "application/json");

		return (ClientMessage) unmarshaller.unmarshal(new StringReader(input));
	}

	// Registers User
	public void registerUser(ClientMessage clientMessage) throws JAXBException, SQLException {
		Response<User> response = new Response<>();
		response.setMessage("User has been sucessfully registered!");

		Abstract regCmd = new Register();
		regCmd.setUserdao(userDao);
		regCmd.executeCommand(clientMessage.getData(), properties);
		User newUser = regCmd.getUser();
		response.setData(newUser);

		if (newUser == null) {
			String message = "Unable to enter user into database.";
			response.setMessage(message);
			log.error(message);
		} else {
			if (newUser.getUserid() == -1) {
				String message = "Username already exists.";
				response.setMessage(message);
				log.info(message);
			} else
				log.info("User {} has been sucessfully registered!", newUser.getUsername());
		}

		sendResponse(response);
	}

	// User Login
	public void loginUser(ClientMessage clientMessage) throws JAXBException, SQLException, IOException {

		Response<String> response = new Response<>();
		String message = "Login credentials are incorrect.";
		response.setMessage(message);
		response.setData("invalid");

		Abstract logCmd = new Login();
		logCmd.setUserdao(userDao);
		logCmd.executeCommand(clientMessage.getData(), properties);
		User newUser = logCmd.getUser();

		if (newUser.getUserid() == -1) {
			log.info(message);
			sendResponse(response);
			return;
		}

		Response<User> checkPwd = new Response<>();
		checkPwd.setMessage("checkPass");
		checkPwd.setData(newUser);
		sendResponse(checkPwd); // Sends user the hashed pass to check with client

		ClientMessage passwordCheckMessage = getClientMessage();

		if (passwordCheckMessage.getMessage().equals("success")) {
			message = "Login successful!";
			response.setMessage(message);
			log.info(message);
		}

		sendResponse(response);
	}

	// sends marshalled Objects as response
	public void sendResponse(Response<?> response) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[] { Response.class }, properties);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(JAXBContextProperties.MEDIA_TYPE, "application/json");

		marshaller.marshal(response, writer);
		writer.flush();
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public FileDDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDDao fileDao) {
		this.fileDao = fileDao;
	}
}
