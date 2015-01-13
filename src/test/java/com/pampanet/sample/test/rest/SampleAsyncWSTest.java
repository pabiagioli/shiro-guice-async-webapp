package com.pampanet.sample.test.rest;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.container.AsyncResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;

import com.pampanet.sample.rest.SampleSecuredAsyncWS;
import com.pampanet.sample.test.common.AbstractShiroBaseTest;

/**
 * 
 * @author pablo.biagioli
 *
 */
@RunWith(JukitoRunner.class)
public class SampleAsyncWSTest extends AbstractShiroBaseTest {

	@Spy
	SampleSecuredAsyncWS sampleAsyncWs;
	
	/**
	 * Method to setup the test mockups.
	 */
	@Before
	public void setUp(){
		sampleAsyncWs = Mockito.spy(new SampleSecuredAsyncWS());
	}
	
	@Ignore 
	@Test
	public void shouldGetPrincipalTwiceTest(AsyncResponse asyncRes) throws Exception{
		
		//WHEN
		when(_mockSubject.getPrincipal()).thenReturn("lonestarr");
		doThrow(AuthorizationException.class).when(_mockSubject).checkPermission("forbiddenForAllExceptRoot");
		
		sampleAsyncWs.getBasic(asyncRes);
		sampleAsyncWs.sayHelloToUser(asyncRes);
		sampleAsyncWs.forbiddenToAll(asyncRes);
		
		//THEN
        //Verify that getPrincipal() method was called under the mocked subject
        verify(_mockSubject,times(2)).getPrincipal();
	}
	
}
