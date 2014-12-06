package com.pampanet.sample.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

import org.apache.shiro.subject.Subject;
import org.jukito.JukitoRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;

import com.google.inject.Inject;
import com.pampanet.sample.rest.SampleSecuredAsyncWS;
import com.pampanet.sample.rest.SampleSecuredRESTWebService;
import com.pampanet.sample.test.common.AbstractShiroUnitTest;

/**
 * Injected fields are mocks by Jukito<br>
 * Jukito: https://github.com/ArcBees/Jukito <br>
 * Shiro Testing: http://shiro.apache.org/testing.html
 * 
 * @author pablo
 *
 */
@RunWith(JukitoRunner.class)
public class SampleTest extends AbstractShiroUnitTest{
	
	@Inject
	SampleSecuredRESTWebService sampleService;
	@Inject
	Subject subjectUnderTest;
	@Spy
	@Inject
	SampleSecuredAsyncWS sampleAsyncWs;
	
	/**
	 * use this method for integration tests with shiro ini realm
	 */
	@BeforeClass
	public static void setupClass(){
		//0.  Build and set the SecurityManager used to build Subject instances used in your tests
        //    This typically only needs to be done once per class if your shiro.ini doesn't change,
        //    otherwise, you'll need to do this logic in each test that is different
        //Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:test.shiro.ini");
        //setSecurityManager(factory.getInstance());
	}
	
	/**
	 * Method to setup the test mockups.
	 */
	@Before
	public void setUp(){
		//1.a (Integration Test) Build the Subject instance for the test to run:
        //subjectUnderTest = new Subject.Builder(getSecurityManager()).principals(new SimplePrincipalCollection("lonestarr", IniSecurityManagerFactory.INI_REALM_NAME)).authenticated(true).buildSubject();
		
		//1.b (Unit Test) Mock the Subject:
		when(subjectUnderTest.getPrincipal()).thenReturn("lonestarr");
        
		//2. Bind the subject to the current thread:
        setSubject(subjectUnderTest);
	}
	
	@Test
	public void testShiroSample() throws Exception{
        
		Response response = sampleService.sayHelloToUser();
        
        //THEN
        //Verify that getPrincipal() method was called under the mocked subject
        verify(subjectUnderTest).getPrincipal();
        //assert the response had a 200 OK status
        Assert.assertTrue(response.getStatus() == 200);
	}
	
	@Test
	public void testShiroAsyncSample(AsyncResponse asyncRes) throws Exception{
		
		//SampleSecuredAsyncWS asyncWs = Mockito.spy(new SampleSecuredAsyncWS());
		
		//WHEN
		sampleAsyncWs.sayHelloToUser(asyncRes);
        
		//THEN
        //Verify that getPrincipal() method was called under the mocked subject
        verify(subjectUnderTest).getPrincipal();
	}
	
	@After
    public void tearDownSubject() {
        //3. Unbind the subject from the current thread:
        clearSubject();
    }
}
