Setup:
```
git clone https://github.com/apache/ambari
cd ambari
mvn package install
cd ambari-views
mvn package install

cd ~/
git clone https://github.com/randerzander/servlet-view-example
cd servlet-view-example
mvn package
# de-cache any previous view deployments
sudo rm -rf /var/lib/ambari-server/resources/views/* 
#Deploy
sudo cp target/servlet-view-example-1.0-SNAPSHOT-view.jar /var/lib/ambari-server/resources/views/
sudo service ambari-server restart
