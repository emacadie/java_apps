This is the example project referred to in the
Struts 2 documentation, Struts 2 Spring tutorial.
See:  http://struts.apache.org.

To build the application's war file run mvn clean package
from the project's root folder.

The war file is created in the target sub-folder.

Copy the war file to your Servlet container (e.g. Tomcat, GlassFish) and 
then startup the Servlet container.

In a web browser go to:  http://localhost:8080/spring_struts/index.action.

You should see a web page with Welcome to Struts 2!

rm -rvf /home/ericm/jarFiles/apache-tomcat-7.0.22/webapps/spring_struts*
cp -v /home/ericm/github/struts2_apache_tutorials/14.spring_struts/target/spring_struts.war /home/ericm/jarFiles/apache-tomcat-7.0.22/webapps



