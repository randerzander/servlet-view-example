An example of an Ambari View with a simple UI and backing Java Servlet for executing Hive queries and viewing results.

![Hive Query View](/sshots/hive query.png?raw=true)

![Hive Results View](/sshots/hive results.png?raw=true)

Note: For Ambari 1.6.1 or earlier, you'll need to 'sudo service ambari-server restart' to pick up changes to deployed views. Ambari 1.7.x and above automatically pick up changes to view resources.

Install Pre-built jar:
```
sudo rm -rf /var/lib/ambari-server/resources/views/work/example\{1.0.0\}
sudo cp target/servlet-view-example-1.0-SNAPSHOT-view.jar /var/lib/ambari-server/resources/views/
```

You'll need maven, nodejs, npm, brunch (npm install -g brunch), and bower (npm install -g bower) installed to build Ambari and this Ambari View.

Make sure to edit /src/main/resources/config.properties and set your hive.host config (Example localhost:10000)

Build:
```
git clone https://github.com/apache/ambari
cd ambari
mvn package install
cd ambari-views
mvn package install

cd ~/
git clone https://github.com/randerzander/servlet-view-example
cd servlet-view-example
sh deploy.sh
```
