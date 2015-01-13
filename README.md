shiro-guice-async-webapp
========================
[![Build Status](https://travis-ci.org/pabiagioli/shiro-guice-async-webapp.svg)](https://travis-ci.org/pabiagioli/shiro-guice-async-webapp)

Sample Project for building RESTful Web Services using Apache Shiro 1.2.3, JBoss RestEasy 3 and Google Guice 3 with Asynchronous HTTP Request Processing support.

Dependencies included
---------------------
- Servlet 3.1.0 (http://java.net/projects/servlet-spec/pages/Home)
- JBoss RestEasy 3.0.9.Final (http://resteasy.jboss.org) 
- Guice 3.0 (http://github.com/google/guice)
- Apache Shiro 1.2.3 (http://shiro.apache.org)
- gson 2.2.4 (http://code.google.com/p/google-gson/)
- joda-time 2.4 (http://www.joda.org/joda-time/) 
- JUnit 4.10 (http://junit.org/)
- Jukito 1.4 (http://github.com/ArcBees/Jukito)
- Logback + SLF4J XLogger (http://logback.qos.ch)

Requirements
------------
- Java 8
- Maven 3
- Application Server (Tomcat 9(?), Jetty 9 Recommended)

Building
--------
- Make the war file <code>mvn clean package</code>
- Deploy the war file in Tomcat with Eclipse or manually

After Deploy on Application Server
----------------------------------
- The REST WS are accessible under /*
- Front-End not yet implemented.

Run with Jetty
--------------
- You can run the application with Jetty via Maven doing <code>mvn jetty:run</code>
- The application gets deployed by default in <a href="http://localhost:8080/shiro-guice-async-webapp">http://localhost:8080/shiro-guice-async-webapp</a>

Creating a Local Maven Archetype
--------------------------------
- Generate an archetype: <code>mvn archetype:create-from-project</code>
- Go to <code>target/generated-sources/archetype</code> and run <code>mvn install</code>
- Create a fresh project from Archetype <code> mvn archetype:generate -DarchetypeCatalog=local </code> using <code>com.pampanet:shiro-guice-async-webapp</code>
- You can select the archetype from Eclipse IDE from the "New Maven Project" Wizard

Apache Shiro Filters
--------------------
- Shiro's Default Filters: http://shiro.apache.org/web.html#Web-DefaultFilters
- In <code>com.pampanet.sample.shiro.modules.BootstrapShiroModule</code> are all the filters, placed in order.
- You can replace <code>com.pampanet.sample.shiro.modules.ShiroAnnotationsModule</code> with Shiro's default <code>ShiroAopModule</code> class.

Apache Shiro Users Configuration
--------------------------------
- Sample <code>shiro.ini</code> file in <code>src/main/resources</code>
- To configure more Realms and Filters, refer to Shiro's Documentation https://shiro.apache.org/static/current/apidocs/org/apache/shiro/realm/Realm.html

Current Code Coverage
---------------------
[![Coverage Status](https://coveralls.io/repos/pabiagioli/shiro-guice-async-webapp/badge.png)](https://coveralls.io/r/pabiagioli/shiro-guice-async-webapp)

Next steps
----------
- Servlet 4 Push API
- Wiki
- More Code Coverage
- More stuff...
