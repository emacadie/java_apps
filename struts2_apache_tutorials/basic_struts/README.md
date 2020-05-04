I am going through the Struts2 example apps.
This one can be found at http://localhost:8080/basic_struts/index.jsp when it is run, and http://struts.apache.org/release/2.3.x/docs/create-struts-2-web-application-using-maven-to-manage-artifacts-and-to-build-the-application.html on the web.    

It can also be found at http://localhost:8080/basic_struts/index.action   

To put it into a war: mvn clean package    
To run on my machine:  
To start Tomcat:   
/home/ericm/jarFiles/apache-tomcat-7.0.22/bin/startup.sh   
To stop Tomcat:    
/home/ericm/jarFiles/apache-tomcat-7.0.22/bin/shutdown.sh   
To remove old app:     
rm -rvf /home/ericm/jarFiles/apache-tomcat-7.0.22/webapps/basic_struts*   
To copy new app:   
cp -v /home/ericm/github/struts2_apache_tutorials/basic_struts/target/basic_struts.war /home/ericm/jarFiles/apache-tomcat-7.0.22/webapps/  
To clear the logs:    
rm -v /home/ericm/jarFiles/apache-tomcat-7.0.22/logs/*    
