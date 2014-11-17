An example of an Ambari View with a simple UI and backing Java Servlet for executing Hive queries and viewing results.

![Hive Query View](/sshots/hive query.png?raw=true)

![Hive Results View](/sshots/hive results.png?raw=true)

To install the pre-built jar (target/servlet-view-example-1.0-SNAPSHOT-view.jar):
```
sudo rm -rf /var/lib/ambari-server/resources/views/work/example\{1.0.0\}
sudo cp target/servlet-view-example-1.0-SNAPSHOT-view.jar /var/lib/ambari-server/resources/views/
```

To manually build and deploy this project:

You'll need maven, nodejs, npm, brunch (npm install -g brunch) installed to build Ambari.


Build and install Ambari jars to local maven repository (~/.m2):
```
git clone https://github.com/apache/ambari
cd ambari
mvn package install
cd ambari-views
mvn package install
```

Build and install this Ambari View:
Make sure to edit /src/main/resources/config.properties and set your hive.host config (Example localhost:10000)
```
git clone https://github.com/randerzander/servlet-view-example
cd servlet-view-example
sh deploy.sh
```
