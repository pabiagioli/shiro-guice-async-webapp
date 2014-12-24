shiro-guice-async-webapp
========================

RESTful Web Services using Apache Shiro 1.2.3, JBoss RestEasy 3 and Google Guice 3 with Asynchronous HTTP Request Processing support.

Dependencies included
---------------------
- Servlet 3.1.0
- JBoss RestEasy 3.0.9.Final
- Guice 3.0
- Apache Shiro 1.2.3
- guice-persist 3.0
- gson 2.2.4
- joda-time 2.4
- JUnit 4.10
- Jukito 1.4
- Logback + SLF4J XLogger

Requirements
------------
- Java 7
- Maven 3
- Application Server (Tomcat)

Building
--------
- Make the war file <code>mvn clean package</code>
- Deploy the war file in Tomcat 7 with Eclipse or manually

After Deploy on Application Server
----------------------------------
- The REST WS are accessible under /*
- Front-End not yet implemented.

Run with Jetty
--------------
- You can run the application with Jetty via Maven doing <code>mvn jetty:run</code>
- The application gets deployed by default in <a href="http://localhost:8080">http://localhost:8080</a>

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
