package com.server.handlers;

import java.security.Principal;

import com.server.PlayerConnection;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;

/**
 * Ignore superclass stuff.
 * @author kurtis
 *
 */
public class RequestContext extends  MessageContext {

	/** The PlayerConnection that generated the request. */
	private final PlayerConnection pc;
	
	public RequestContext(PlayerConnection pc) {
		this.pc = pc;
	}
	
	public final PlayerConnection getPlayerConnection() {
		return pc;
	}
	
	@Override
	public String getClientHostName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getClientInetAddress() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Principal getPrincipal() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getPrincipalName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getPrincipalNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Principal[] getPrincipals() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSecure() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
