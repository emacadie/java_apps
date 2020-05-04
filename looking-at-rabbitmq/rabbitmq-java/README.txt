mvn compile

mvn exec:java -Dexec.mainClass="info.shelfunit.app.Send"  
mvn exec:java -Dexec.mainClass="info.shelfunit.app.Recv"  

To turn on/off rabbit mq (at least on my machine):
as root:
cd /etc/init.d
bash rabbitmq-server start
bash rabbitmq-server stop

#- EOF
