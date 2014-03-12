package com.client.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

/**
 * Base class that creates local (client) method implementations. 
 * @author kurtis
 */
public abstract class ProxyBase {
	// TODO: Represent this as a shared class. Only one instance of this needs to exist
	// for the client and server. They both serialize/deserialize in the same manner.
	protected static final Gson GSON;
	protected final BufferedReader reader;
	protected final PrintWriter writer;
	
	static {
		GSON = new Gson();
	}
	
	// TODO: Pass socket and use it to initialize the readers writers according to implementation?
	/**
	 * Creates a new instance of ProxyBase which receives its input from reader and sends its output
	 * through writer.
	 * 
	 * @param reader
	 * @param writer
	 */
	public ProxyBase(BufferedReader reader, PrintWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}
	
	/**
	 * Helper method to to invoke remote procedures which have no return values (void).
	 * @param methodName The name of the remote method to call.
	 * @param params The optional parameter values to pass to the remote call.
	 */
	protected final void invokeOnServer(String methodName, Object... params) {
		invokeOnServer(methodName, Void.class, params);
	}
	
	/**
	 * Invokes the given method by name on the server, with given parameters and expected return type.
	 * @param methodName The name of the remote method to call
	 * @param expected The expected type of the return value 
	 * @param params The optional parameter values to pass to the remote method call.
	 * @return T - the return value of type "expected"
	 */
	protected final <T> T invokeOnServer(String methodName, Type expected, Object... params) {
		JSONRPC2Request req = new JSONRPC2Request(methodName, UUID.randomUUID().toString());
		
		if (params != null && params.length > 0) {
			List<Object> posParams = new LinkedList<>();
			
			for (Object o : params) {
				posParams.add(GSON.toJson(o));
			}
			
			req.setPositionalParams(posParams);
		}
		
		// Send the request.
		writer.println(req.toJSONString());
		
		// Wait for response
		JSONRPC2Response res = null;
		String line = null;
		
		try {
			line = reader.readLine();
			// Socket closed if readLine returns null
			//TODO: Throw exception instead.
			if (line == null) return null;
			
			res = JSONRPC2Response.parse(line);
		} catch (IOException | JSONRPC2ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (expected.equals(Void.TYPE)) return null;
		
		return GSON.fromJson((String)res.getResult(), expected);
	}
}