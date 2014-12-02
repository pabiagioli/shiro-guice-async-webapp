package com.pampanet.sample.shiro.modules;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.GuestAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.PermissionAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.RoleAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.UserAnnotationMethodInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class ShiroAnnotationsModule extends AbstractModule{

	@Override
	protected void configure() {
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresRoles.class),
                new ShiroMethodInterceptor(new RoleAnnotationMethodInterceptor()));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresUser.class),
                new ShiroMethodInterceptor(new UserAnnotationMethodInterceptor()));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresPermissions.class),
                new ShiroMethodInterceptor(new PermissionAnnotationMethodInterceptor()));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresGuest.class),
                new ShiroMethodInterceptor(new GuestAnnotationMethodInterceptor()));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresAuthentication.class),
                new ShiroMethodInterceptor(new AuthenticatedAnnotationMethodInterceptor()));
	}

	
}
