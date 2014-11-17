mkdir -p src/main/resources/WEB-INF/lib
ln -s /usr/hdp/current/hive-server2/lib/hive-jdbc-*-standalone.jar src/main/resources/WEB-INF/lib/hive-jdbc-standalone.jar
cd src/main/resources/ui
bower install
cd ../../../../
mvn package
sudo cp target/servlet-view-example-1.0-SNAPSHOT-view.jar /var/lib/ambari-server/resources/views/
sudo rm -rf /var/lib/ambari-server/resources/views/work/example\{1.0.0\}/
sudo service ambari-server restart
