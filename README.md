Rainfall
========

Rainfall is an extensible java framework to implement custom DSL based stress and performance tests in your application.

It has a customisable fluent interface that lets you implement your own DSL when writing tests scenarios, and define your own tests actions and metrics.
Rainfall is open to extensions, three of which are currently in progress,
- Rainfall web is a Yet Another Web Application performance testing library
- Rainfall JCache is a library to test the performance of JSR107 caches solutions
- Rainfall Ehcache is a library to test the performance of Ehcache 2 and 3

![Built on DEV@cloud](https://www.cloudbees.com/sites/default/files/styles/large/public/Button-Built-on-CB-1.png?itok=3Tnkun-C)

[![Build Status](https://rainfall.ci.cloudbees.com/buildStatus/icon?job=Rainfall jcache)](https://rainfall.ci.cloudbees.com/job/Rainfall%20jcache/)

Quick start
-----------

Performance tests are written in java

Build the project
-----------------
```
  mvn clean install
```

Use it in your project
----------------------
```
  <dependencies>
    <dependency>
      <groupId>io.rainfall</groupId>
      <artifactId>rainfall-jcache</artifactId>
      <version>LATEST</version>
    </dependency>
  </dependencies>
```


Thanks to the following companies for their support to FOSS:
------------------------------------------------------------

[ej-technologies for JProfiler](http://www.ej-technologies.com/products/jprofiler/overview.html)

[Sonatype for Nexus](http://www.sonatype.org/)

[Cloudbees for cloud-based continuous delivery](https://www.cloudbees.com/)

and of course [Github](https://github.com/) for hosting this project.

