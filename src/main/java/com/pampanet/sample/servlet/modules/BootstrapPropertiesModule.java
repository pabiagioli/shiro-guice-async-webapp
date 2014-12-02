package com.pampanet.sample.servlet.modules;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

	@Override
	protected void configure() {
		Properties bootstrapProperties = new Properties();
		try {
			InputStream is = getClass().getResourceAsStream("/"+GenericBootstrapConstants.BOOTSTRAP_PROPERTIES_FILE);
			bootstrapProperties.load(is);
			Names.bindProperties(binder(), bootstrapProperties);
		} catch (FileNotFoundException e) {
	        System.out.println("The configuration file "+ GenericBootstrapConstants.BOOTSTRAP_PROPERTIES_FILE + " can not be found");
	    } catch (IOException e) {
	        System.out.println("I/O Exception during loading configuration");
	    }
	}

}
