docmag
======

Welcome to docmag - a project that will revolutionize the way students and
professors manage documents.

Database
--------

We use [MongoDB](http://www.mongodb.org/downloads) as our database. Install it
and add the `bin` directory to your `PATH`. Then create a configuration file
somewhere in your system e.g. `/path/to/mongodb.conf` and add the location where
you want Mongo to store your collections, like this:
`dbpath=/path/to/some/folder`. Add any other
[configuration options](http://docs.mongodb.org/manual/reference/configuration-options/)
you like e.g. `journal=true`. Then start MongoDB with one of the following
commands:

    mongod --config /path/to/mongodb.conf
    mongod -f /path/to/mongodb.conf

Or supply all options through the command line:

    mongod --dbpath=/path/to/some/folder

Verify the database is up by typing `mongo` in a new console. This starts up a
console client where you can run queries.

Dependency Management
---------------------

To manage such a complex project we need an equally complex tool. We're using
[Maven](http://maven.apache.org/). Please download the latest version from
[here](http://maven.apache.org/download.cgi). At the time of writing this is
3.1.1.

To add a dependency to the project you need to edit the `pom.xml` file like
this:

    <dependencies>
      ...
      <dependency>
        <groupId>com.example</groupId>
        <artifactId>cool.library</artifactId>
        <version>${cutting.edge}</version>
      </dependency>
      ...
    </dependencies>

IDEs
----

Any IDE with support for **Java** and **Maven** should be sufficient for our
purposes. However you could use even a simple text editor and build / commit
from the command line. Since we're using [Spring](http://spring.io/) though, the
recommended IDE is [STS](http://spring.io/tools/sts/all), because it has all the
necessary and useful plugins included. Alternatively you could use an existing
Eclipse installation and just add whatever you need, e.g.
[m2e](http://www.eclipse.org/m2e/) for Maven integration and
[EGit](http://www.eclipse.org/egit/) for Git source control.

In STS or Eclipse with m2e installed just go to *File -> Import... -> Maven ->
Existing Maven Projects* and you're good to go.

Should there be any need, more detailed explanations will be added.

Server
------

The server to use is
[Tomcat 7](http://tomcat.apache.org/tomcat-7.0-doc/index.html). You can get it
from [here](http://tomcat.apache.org/download-70.cgi). Of course any servlet
container with equal or better [API](http://tomcat.apache.org/whichversion.html)
support should work as well. If you write any tests you're encouraged to use the
[Jetty Maven plugin](http://wiki.eclipse.org/Jetty/Feature/Jetty_Maven_Plugin)
to run them.

Java
----

Both the source and target versions are 1.7 so you should
[get it](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
if you don't have it.
