package com.pampanet.sample.servlet.filter;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.plugins.server.servlet.Filter30Dispatcher;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Binding;
import com.google.inject.Injector;

@Singleton
@WebFilter(asyncSupported=true, value="/*", filterName="restEasyFilter")
public class GuiceRestEasyFilterDispatcher extends Filter30Dispatcher {
    
	private static final Logger log = LoggerFactory.getLogger(GuiceRestEasyFilterDispatcher.class);
	
	@Inject
    Injector injector;

    @Override
    public void init(FilterConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        if(injector == null)
        	injector = (Injector) servletConfig.getServletContext().getAttribute(Injector.class.getName());

        Registry registry = getDispatcher().getRegistry();
        ResteasyProviderFactory providerFactory = getDispatcher().getProviderFactory();

        for (final Binding<?> binding : injector.getBindings().values()) {
            Type type = binding.getKey().getTypeLiteral().getType();
            if (type instanceof Class) {
                Class<?> beanClass = (Class<?>) type;
                if (GetRestful.isRootResource(beanClass)) {
                    ResourceFactory resourceFactory = new GuiceResourceFactory(binding.getProvider(), beanClass);
                    log.info("registering factory for {}", beanClass.getName());
                    registry.addResourceFactory(resourceFactory);
                }

                if (beanClass.isAnnotationPresent(Provider.class)) {
                	log.info("registering provider instance for {}", beanClass.getName());
                    providerFactory.registerProviderInstance(binding.getProvider().get());
                }
            }
        }
    }
}