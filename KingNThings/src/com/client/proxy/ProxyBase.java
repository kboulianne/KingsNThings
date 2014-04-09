package com.client.proxy;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.util.Util;

/**
 * Base class that creates local (client) method implementations. 
 * @author kurtis
 */
abstract class ProxyBase {
	// TODO: Represent this as a shared class. Only one instance of this needs to exist
	// for the client and server. They both serialize/deserialize in the same manner.


	private final LinkedBlockingQueue<JSONRPC2Response> in;
	private final LinkedBlockingQueue<JSONRPC2Request> out;
	
//	// TODO: Pass socket and use it to initialize the readers writers according to implementation?
//	/**
//	 * Creates a new instance of ProxyBase which receives its input from reader and sends its output
//	 * through writer.
//	 * 
//	 * @param reader
//	 * @param writer
//	 */
//	public ProxyBase(BufferedReader reader, PrintWriter writer) {
//		this.reader = reader;
//		this.writer = writer;
//		in = null;
//	}
	
	ProxyBase(LinkedBlockingQueue<JSONRPC2Response> in, 
			LinkedBlockingQueue<JSONRPC2Request> out) {
		this.in = in;
		this.out = out;
	}
	
	/**
	 * Helper method to to invoke remote procedures which have no return values (void).
	 * @param methodName The name of the remote method to call.
	 * @param params The optional parameter values to pass to the remote call.
	 * @throws JSONRPC2Error 
	 */
	protected final void invokeOnServer(String methodName, Object... params) throws JSONRPC2Error {
		invokeOnServer(methodName, Void.class, params);
	}
	
	/**
	 * Invokes the given method by name on the server, with given parameters and expected return type.
	 * @param methodName The name of the remote method to call
	 * @param expected The expected type of the return value 
	 * @param params The optional parameter values to pass to the remote method call.
	 * @return T - the return value of type "expected"
	 * @throws JSONRPC2Error 
	 */
	protected final <T> T invokeOnServer(String methodName, Type expected, Object... params) throws JSONRPC2Error {
		JSONRPC2Request req = new JSONRPC2Request(methodName, UUID.randomUUID().toString());
		final Gson GSON = Util.GSON_BUILDER.create();
		
		if (params != null && params.length > 0) {
			List<Object> posParams = new LinkedList<>();
			
			for (Object o : params) {
				// Do not serialize string. This is to bypass json-smart dependency in jsonrpc
				if (o instanceof String) {
					posParams.add(o);
				}
				else {
					posParams.add(GSON.toJson(o));
				}
			}
			
			req.setPositionalParams(posParams);
		}

		// Wait for response
		JSONRPC2Response res = null;
		
		try {
			// Queue the request.
			out.put(req);

			// Wait for response to be sent and processed.
			res = in.take();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		if (!res.indicatesSuccess()) {
			// An error has occured.
			throw res.getError();
		}
		
		if (expected.equals(Void.TYPE)) return null;
		
		return GSON.fromJson((String)res.getResult(), expected);
	}
}