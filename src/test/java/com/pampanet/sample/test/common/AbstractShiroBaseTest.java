package com.pampanet.sample.test.common;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

/**
 * Abstract test case enabling Shiro in test environments.
 */
public class AbstractShiroBaseTest {

	private ThreadState _threadState;
    protected Subject _mockSubject;
    
    @Before
    public void attachSubject()
    {
        _mockSubject = Mockito.mock(Subject.class);
        _threadState = new SubjectThreadState(_mockSubject);
        _threadState.bind();
    }
    
    @After
    public void detachSubject()
    {
        _threadState.clear();
    }
    
}
