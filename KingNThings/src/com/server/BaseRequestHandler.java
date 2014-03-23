package com.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.presenter.Util;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;

public abstract class BaseRequestHandler implements RequestHandler {
	
	//TODO: Make this synchronized.
//	protected List<JSONRPC2Notification> pendingNotifications = new LinkedList<>();
	
	@Override
	public JSONRPC2Response process(JSONRPC2Request request, MessageContext requestCtx) {
		try {
			return respond(request);
		} catch (JSONRPC2Error e) {
			
			return new JSONRPC2Response(e, request.getID());
		}
	}
	
	protected JSONRPC2Response respond(final JSONRPC2Request req) throws JSONRPC2Error {
		//TODO: Dangerous if calling existing method that is not part of the RPC api. i.e. client sending respond(...) would crash the server.
		// TODO: Optimize by storing on construction
//		Method[] methods = getClass().getMethods();
		// Safer than above. Does not expose respond, process and object wait/notify.
		Method[] methods = getClass().getDeclaredMethods();
		// The method to invoke.
		Method handler = null;
		// The paramter types
		Type[] types = null;
		// The Return type of handler.
		Class<?> returnType = null;
		
		// Find the method with name stored in request, extract its parameter types
		// TODO: This is slow, use set or map.
		for (Method m : methods) {
			if (m.getName().equals(req.getMethod())) {
				handler = m;				
				types = m.getGenericParameterTypes();
				returnType = m.getReturnType();
				break;
			}
		}
		
		// Method does not exist
		if (handler == null) throw JSONRPC2Error.METHOD_NOT_FOUND;
		
		// Client uses positional params in request, extract them.
		List<Object> reqParams = req.getPositionalParams();
		// The parameters to pass to the method invocation.
		Object[] params = null;
		
		// Handles methods with parameters.
		if (reqParams != null && !reqParams.isEmpty()) {
			params = new Object[reqParams.size()];
			
			// Loop the reqParams strings and deserialize them using gson.
			for (int i = 0 ; i < reqParams.size() ; i ++) {
				// A simple string is not valid JSON. Handles special case.
				if (types[i].equals(String.class)) {
					params[i] = (String)reqParams.get(i);					
				}
				else {
					params[i] = Util.GSON.fromJson((String)reqParams.get(i), types[i]);
				}
			}
		}
		
		Object returnValue = null;
		try {
			// Invoke the method specified in the request and keep its return value if any.
			returnValue = handler.invoke(this, params);
		} catch (IllegalAccessException | IllegalArgumentException e ) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			//FIXME: Could cause problems if not JSONRPC2Error?
			throw (JSONRPC2Error)e.getTargetException();
		}
		
		JSONRPC2Response res = null;
		
		// Simply respond with ID if void return type.
		if (returnType.equals(Void.TYPE)) {
			res = new JSONRPC2Response(req.getID());
		}
		else {
			res = new JSONRPC2Response(Util.GSON.toJson(returnValue, returnType), req.getID());
		}
		
		return res;
	}
}
