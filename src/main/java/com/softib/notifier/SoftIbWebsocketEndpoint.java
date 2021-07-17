package com.softib.notifier;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

 
 

@ServerEndpoint(value = "/push")
public class SoftIbWebsocketEndpoint {
	@OnOpen
	public void onOpen(final Session session) {
		final String username = session.getRequestParameterMap().get("username").get(0);
		UserSessionHandler.addUserBySessionId(session.getId(), username);
		UserSessionHandler.addSession( username, session);
  	}

	@OnClose
	public void close(final Session session) {
		final String username = UserSessionHandler.getUserBySessionId(session.getId());

 			UserSessionHandler.removeSession( username, session);
 		}
	

	@OnMessage
	public void onMessage(final String message, final Session session) {
  	}

	@OnError
	public void onError(final Throwable e, Session session) {
		final String username = UserSessionHandler.getUserBySessionId(session.getId());

 			UserSessionHandler.removeSession( username, session);
 	}

}
