package com.pampanet.sample.servlet.filter;

import javax.inject.Singleton;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

import com.google.inject.Inject;
import com.google.inject.Injector;

@Singleton
@WebFilter(asyncSupported=true, value="/*", filterName="shiroFilter", initParams=@WebInitParam(name="staticSecurityManagerEnabled", value="true"))
public class GuiceRestEasyShiroFilter extends AbstractShiroFilter{
	
	private Injector injector;
	
	@Override
	public void init() throws Exception {
		super.init();
		injector = (Injector) getServletContext().getAttribute(Injector.class.getName());
		this.setSecurityManager(injector.getInstance(WebSecurityManager.class));
		this.setFilterChainResolver(injector.getInstance(FilterChainResolver.class));
	}
	
	
	public GuiceRestEasyShiroFilter() {
	}

	@Inject
	public GuiceRestEasyShiroFilter(WebSecurityManager mWebSecurityManager, FilterChainResolver mFilterChainResolver) {
		this.setSecurityManager(mWebSecurityManager);
		this.setFilterChainResolver(mFilterChainResolver);
	}
}
