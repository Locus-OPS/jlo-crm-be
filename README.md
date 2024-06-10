Hello, this is how to setup the F/E and B/E development environment.
Please, refer to the URL and contents there.

https://dzone.com/articles/angular-2-and-spring-boot-development-environment

In this repository, there are 2 projects.
1. JLOXG - B/E application using Spring F/W (Spring 5.x and Boot 2.x)
2. JLOXGFE - F/E application usging Angular 8 and template from "Creative Tim".
3. Import from Git to create projects in your eclipse or STS.
4. You can use either STS / eclipse or Visual Source Code.
5. The choice of the development tool is up to individual's taste. (or F/E - Visual Source Code and B/E - eclipse / STS)
6. I just set up and tested 2 tools - STS / eclipse or Visual Source Code and they seems to fine perfectly.

There are 2 tasks we need to do more and didn't complete yet as of now (July 4, 2019)
+++++++++++++++++++++++++++++++++++++++
1. Client-Server Integration
Now, both server and client projects are working independently on ports 8080 and 4200 respectively. It’s now time to integrate both layers and the client project’s server will be the main entry point (http://localhost:4200/) and all requests will be served by this server except URLs with the pattern “/api/”. The client server at 4200 will proxy any “/api/” requests to the “backend” server at “localhost: 8080”. 
To configure this setup, create a file “proxy.conf.json” in the demo-app-client directory with the following contents.
```xml
{
  “/api” :{
  “target” : “http://localhost:8080”,
  “secure” : false
  }
}
```

2. Build and Deployment
Ideally, for any non-trivial use case, you should build your client and server independently, and deploy client bundles (static resources) to your choice of web server such as Apache, NGINX, IIS etc., and application server running your choice of servlet container such as Tomcat, Jetty, etc.
However, if you need something quick, you can use the following steps to generate a single deployable war file.

1. Build Client - Go to “demo-app-client” project directory and run “ng build -prod” command.

2. Build Server to generate a deployable war. We can do this by right clicking on the “demo-app-server” project” and clicking Run As -> Maven Install. But before we do this, we have to modify our pom.xml to include static resources from the “demo-app-client” project as shown below.


```xml
<plugin>
<artifactId>maven-resources-plugin</artifactId>
<executions>
      <execution>
          <id>copy-resources</id>
          <phase>validate</phase>
          <goals><goal>copy-resources</goal></goals>
          <configuration>
              <outputDirectory>${basedir}/target/classes/static/</ outputDirectory >
              <resources>
                  <resource>
                      <directory>${basedir}/../ demo-app-client/dist</ directory >
                  </resource>
              </resources>
          </configuration>
      </execution>
</executions>
</plugin>
```

+++++++++++++++++++++++++++++++++++++

For this matter, please, check the issue - the link below. 
https://github.com/selloper/JLOXGFS/issues?q=is%3Aissue+is%3Aclosed
