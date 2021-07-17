package com.softib.notifier;

import java.io.IOException;
import java.util.Set;

import javax.websocket.Session;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SoftIbNotifierRestController {
	private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

	@PostMapping(value = "/push")
	public void push(@RequestBody String message) throws Exception {
		MessageBean messageBean = OBJECT_MAPPER.readValue(message, MessageBean.class);
		String username = messageBean.getUsername();
		if (username == null) {
			throw new Exception("Username cannot be null");
		}
		Set<Session> sessions = UserSessionHandler.getSessionsByUser(username);
		if (!sessions.isEmpty()) {
			sessions.forEach(s -> {
				pushMessage(s, message);
			});
		}

	}

	public static void pushMessage(Session session, String msg) {
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static ObjectMapper createObjectMapper() {
		return new ObjectMapper();
	}
}
