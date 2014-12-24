package com.pampanet.sample.servlet.filter;

import javax.inject.Singleton;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

@Singleton
@WebFilter(asyncSupported=true, value="/*", filterName="shiroFilter", initParams=@WebInitParam(name="staticSecurityManagerEnabled", value="true"))
public class GuiceRestEasyShiroFilter extends AbstractShiroFilter{
	private XLogger logger = XLoggerFactory.getXLogger(GuiceRestEasyShiroFilter.class);
	
	private Injector injector;
	
	@Override
	public void init() throws Exception {
		logger.entry();
		super.init();
		injector = (Injector) getServletContext().getAttribute(Injector.class.getName());
		this.setSecurityManager(injector.getInstance(WebSecurityManager.class));
		this.setFilterChainResolver(injector.getInstance(FilterChainResolver.class));
		logger.exit();
	}
	
	
	public GuiceRestEasyShiroFilter() {
		logger.entry();
		logger.exit();
	}

	@Inject
	public GuiceRestEasyShiroFilter(WebSecurityManager mWebSecurityManager, FilterChainResolver mFilterChainResolver) {
		logger.entry("injected-constructor");
		this.setSecurityManager(mWebSecurityManager);
		this.setFilterChainResolver(mFilterChainResolver);
		logger.exit();
	}
}
