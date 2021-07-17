package com.softib.notifier;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

/**
 * 
 * @author Habib ALI
 * 
 */

public class UserSessionHandler {
	private static ConcurrentHashMap<String, Set<Session>> userSessionStore = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String ,String> sessionPerUser = new ConcurrentHashMap<>();
 	private UserSessionHandler() {

	}

	public static Set<Session> getSessionsByUser( String userId) {
		return userSessionStore.containsKey( userId)
				? userSessionStore.get( userId)
				: null;
	}

	public static void removeSession( String userId, Session session) {
		Set<Session> sessions = userSessionStore.get( userId);

		if (sessions != null) {

			sessions.remove(session);

		}
	}

	public static void addSession(String userId, Session session) {
		session.setMaxIdleTimeout(1800000);
		if (userSessionStore.containsKey(userId)) {
			Set<Session> sessions = userSessionStore.get( userId);

			sessions.add(session);

		}

		else {
			Set<Session> sessions = ConcurrentHashMap.newKeySet();
			sessions.add(session);
			userSessionStore.put(userId, sessions);
		}

	}

	 
	
	public static String getUserBySessionId(String sessionId) {
		if (sessionPerUser.containsKey(sessionId)) {
			return sessionPerUser.get(sessionId);
		}
		return null;
	}
	
	public static void addUserBySessionId(String sessionId, String userId) {
		sessionPerUser.put(sessionId, userId);
	}
	
	public static void removeUserBySessionId(String sessionId) {
		if(sessionPerUser.contains(sessionId)) {
			sessionPerUser.remove(sessionId);
		}
	}
}
