package com.pampanet.sample.shiro.modules;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
/**
 * 
 * @author pablo.biagioli
 *
 */
public class ShiroMethodInterceptor implements MethodInterceptor{
	static final Logger logger = Logger.getLogger(ShiroMethodInterceptor.class.getName());

    private org.apache.shiro.aop.MethodInterceptor methodInterceptor;

    public ShiroMethodInterceptor(org.apache.shiro.aop.MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }


	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		return methodInterceptor.invoke(new ShiroMethodInvocation(methodInvocation));
	}

	private static class ShiroMethodInvocation implements org.apache.shiro.aop.MethodInvocation {

		private final MethodInvocation methodInvocation;

		public ShiroMethodInvocation(MethodInvocation methodInvocation) {
			this.methodInvocation = methodInvocation;
		}

        @Override
        public Object proceed() throws Throwable {
            return methodInvocation.proceed();
        }

        @Override
        public Method getMethod() {
            return methodInvocation.getMethod();
        }

        @Override
        public Object[] getArguments() {
            return methodInvocation.getArguments();
        }

        @Override
        public Object getThis() {
            return methodInvocation.getThis();
        }
    }

}
