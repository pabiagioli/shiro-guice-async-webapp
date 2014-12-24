package com.pampanet.sample.servlet.config;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.pampanet.sample.servlet.modules.BootstrapPropertiesModule;
import com.pampanet.sample.servlet.modules.BootstrapRestPackagesModule;
import com.pampanet.sample.shiro.modules.BootstrapShiroModule;
import com.pampanet.sample.shiro.modules.ShiroAnnotationsModule;

@WebListener(value="guiceListener")
public class GenericGuiceRestEasyContextListener extends GuiceResteasyBootstrapServletContextListener{

	private static final String INJECTOR_NAME = Injector.class.getName();
	private ServletContext context;
	private XLogger logger = XLoggerFactory.getXLogger(GenericGuiceRestEasyContextListener.class);
	
	@Override
	protected List<? extends Module> getModules(ServletContext context) {
		logger.entry();
		return Arrays.asList(new BootstrapPropertiesModule(), new RequestScopeModule(), new BootstrapShiroModule(context), new ShiroAnnotationsModule(),new BootstrapRestPackagesModule());
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.entry();
		context = event.getServletContext();
		super.contextInitialized(event);
		logger.exit();
	}
	
	@Override
	protected void withInjector(Injector injector) {
		logger.entry();
		super.withInjector(injector);
		context.setAttribute(INJECTOR_NAME, injector);
		
		//LinkedList<Class<? extends Filter>> filterChain = new LinkedList<Class<? extends Filter>>();
		//filterChain.add(GuiceRestEasyShiroFilter.class);
		//filterChain.add(GuiceRestEasyFilterDispatcher.class);
		
		//setUpFilters(filterChain);
		logger.exit();
	}
	
	@SuppressWarnings("unused")
	private void setUpFilters(List<Class<? extends Filter>> fList){
		logger.entry();
		for(Class<? extends Filter> f: fList){
			FilterRegistration fr = context.addFilter(f.getSimpleName(),f);
			if(fr != null){
				fr.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.ASYNC),true,"/*");
			}else{
				context.getFilterRegistration("shiroFilter").addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.ASYNC),true,"/*");
			}
			
		}
		logger.exit();
	}
	
}
