package com.pampanet.sample.shiro.modules;

import javax.servlet.ServletContext;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;

import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * This Module loads a Sample IniRealm. This is also a Guice's PrivateModule.<br> 
 * For injecting any of the providers listed here, you need to expose them through configureShiroWeb() method.<br>
 * 
 * 
 * @author pablo.biagioli
 *
 */
public class BootstrapShiroModule extends ShiroWebModule{
	
	private static final String CREDENTIALS_MATCHER_ALGORITHM_NAME = "SHA-512";

	public BootstrapShiroModule(ServletContext servletContext) {
		super(servletContext);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configureShiroWeb() {
		//if you would like to expose the CredentialsMatcher listed here, uncomment the following line.
		//expose(CredentialsMatcher.class);
		expose(WebSecurityManager.class);
		expose(FilterChainResolver.class);
		try {
            bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
        } catch (NoSuchMethodException e) {
            addError(e);
        }
		addFilterChain("/logout", LOGOUT);
		//addFilterChain("/rest/public/**",ANON);
		addFilterChain("/rest/**",NO_SESSION_CREATION, AUTHC_BASIC);
		addFilterChain("/**", AUTHC_BASIC);
        
	}

	@Provides
	@Singleton
    Ini loadShiroIni() {
		Ini result = Ini.fromResourcePath("classpath:shiro.ini"); 
        return result;
    }
	
	/**
	 * When annotations activated, you'll need to hash passwords in your configured Realm (i.e.: shiro.ini file)
	 * @return credentialsMatcher singleton implementation for this application
	 */
	//@Provides
	//@Singleton
	public CredentialsMatcher provideCredentialsMatcher(){
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName(CREDENTIALS_MATCHER_ALGORITHM_NAME);
		return matcher;
	}
	
}
