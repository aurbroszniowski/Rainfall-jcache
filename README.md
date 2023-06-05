Rainfall
========

Rainfall is an extensible java framework to implement custom DSL based stress and performance tests in your application.

It has a customisable fluent interface that lets you implement your own DSL when writing tests scenarios, and define your own tests actions and metrics.
Rainfall is open to extensions, three of which are currently in progress,
- Rainfall web is a Yet Another Web Application performance testing library
- Rainfall JCache is a library to test the performance of JSR107 caches solutions
- Rainfall Ehcache is a library to test the performance of Ehcache 2 and 3

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

[Sonatype for Nexus](http://www.sonatype.org/)

and of course [Github](https://github.com/) for hosting this project.

