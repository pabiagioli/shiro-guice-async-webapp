package com.pampanet.sample.servlet.modules;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.pampanet.sample.servlet.config.GenericBootstrapConstants;

public class BootstrapRestPackagesModule extends AbstractModule{

	private static final Logger log = LoggerFactory.getLogger(BootstrapRestPackagesModule.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void configure() {
		String[] pkgs = GenericBootstrapConstants.REST_EASY_REST_PACKAGES.split(",");
		
		for(String pkg:pkgs){
			if(pkg.trim().endsWith(GenericBootstrapConstants.REST_EASY_REST_PACKAGES_SUFFIX)){
				log.info("found RESTful package: {}",pkg.trim());
				Class[] lst = null;
				try {
					lst = getClasses(pkg.trim());
				} catch (ClassNotFoundException | IOException e) {
					log.error("{}, {}", e.getClass().getName() ,e.getMessage());
					e.printStackTrace();
				}
				for (Class c: lst){
					if(c.isAnnotationPresent(Path.class) || c.isAnnotationPresent(Provider.class)){
						log.info("found RestEasy Resource: {}",c.getName());
						bind(c);
					}
				}
			}
		}
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * @param packageName The base package 
	 * @return The classes 
	 * @throws ClassNotFoundException 
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * 
	 * Recursive method used to find all classes in a given directory and subdirs. 
	 * @param directory The base directory 
	 * @param packageName The package name for classes found inside the base directory 
	 * @return The classes 
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file,
						packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
}
