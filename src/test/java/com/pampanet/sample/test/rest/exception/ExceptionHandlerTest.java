package com.pampanet.sample.test.rest.exception;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.apache.shiro.authz.UnauthorizedException;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.pampanet.sample.rest.exception.UnauthorizedExceptionHandler;
import com.pampanet.sample.servlet.config.GenericBootstrapConstants;

/**
 * 
 * @author pablo.biagioli
 *
 */
@RunWith(JukitoRunner.class)
public class ExceptionHandlerTest extends GenericBootstrapConstants{

	UnauthorizedExceptionHandler handler = new UnauthorizedExceptionHandler();
	
	@Test
	public void shouldGetErrorMessage(){
		UnauthorizedException ex = new UnauthorizedException("mock");
		
		Response resp = handler.toResponse(ex);
		Assert.assertTrue(resp.getStatus() == 401);
		Assert.assertTrue(resp.getEntity() != null);
	}
	
}
