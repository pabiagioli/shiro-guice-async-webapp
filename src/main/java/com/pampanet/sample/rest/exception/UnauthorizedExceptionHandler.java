package com.pampanet.sample.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.authz.UnauthorizedException;

@Provider
public class UnauthorizedExceptionHandler implements ExceptionMapper<UnauthorizedException>{

	@Override
	public Response toResponse(UnauthorizedException arg0) {
		return Response.status(401).entity(arg0.getMessage()).build();
	}

}
