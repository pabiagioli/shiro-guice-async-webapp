package com.pampanet.sample.servlet.config;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;

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
	//private Logger logger = LoggerFactory.getLogger(GenericGuiceRestEasyContextListener.class);
	
	@Override
	protected List<? extends Module> getModules(ServletContext context) {
		return Arrays.asList(new BootstrapPropertiesModule(), new RequestScopeModule(), new BootstrapShiroModule(context), new ShiroAnnotationsModule(),new BootstrapRestPackagesModule());
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		super.contextInitialized(event);
	}
	
	@Override
	protected void withInjector(Injector injector) {
		super.withInjector(injector);
		context.setAttribute(INJECTOR_NAME, injector);
	}
}
