package com.pampanet.sample.servlet.modules;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.pampanet.sample.servlet.config.GenericBootstrapConstants;
/**
 * Guice Module to load Properties file and bind it to Guice Injector.<br>
 * Properties can later be used in constructor or field injection by using: <br> 
 * <code>@Inject @Named("name.of.the.key") private String propValue;</code>
 * 
 * @author pablo.biagioli
 *
 */
public class BootstrapPropertiesModule extends AbstractModule{

	private XLogger logger = XLoggerFactory.getXLogger(this.getClass());
	
	@Override
	protected void configure() {
		logger.entry();
		Properties bootstrapProperties = new Properties();
		try {
			InputStream is = getClass().getResourceAsStream("/"+GenericBootstrapConstants.BOOTSTRAP_PROPERTIES_FILE);
			bootstrapProperties.load(is);
			Names.bindProperties(binder(), bootstrapProperties);
		} catch (FileNotFoundException e) {
	        logger.error("The configuration file "+ GenericBootstrapConstants.BOOTSTRAP_PROPERTIES_FILE + " can not be found");
	        logger.throwing(e);
	    } catch (IOException e) {
	        logger.error("I/O Exception during loading configuration");
	        logger.throwing(e);
	    }
		logger.exit();
	}

}
