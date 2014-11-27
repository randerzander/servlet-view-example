mkdir -p src/main/resources/WEB-INF/lib
# Add hive-jdbc-standalone.jar as dependency -- Ambari will add jars in /src/main/resources/WEB-INF/lib to the classpath
ln -s /usr/hdp/current/hive-server2/lib/hive-jdbc-*-standalone.jar src/main/resources/WEB-INF/lib/hive-jdbc-standalone.jar

# Install UI dependencies via bower
cd src/main/resources/ui
bower install
cd ../../../../

# Build, install view, restart ambari-server
mvn package
sudo cp target/servlet-view-example-1.0-SNAPSHOT-view.jar /var/lib/ambari-server/resources/views/
sudo rm -rf /var/lib/ambari-server/resources/views/work/example\{1.0.0\}/
sudo service ambari-server restart
