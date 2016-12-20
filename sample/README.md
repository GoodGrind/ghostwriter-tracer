This is simple application for demonstrating the usage of GhostWriter.
It contains a commented build files for Maven and Gradle as well. The relevant lines can be copy-pasted to your own build scripts.

Assuming that you have a working Maven and Gradle installation on your specified path, you can see the
"GhostWriter-ed" FizzBuzz application in action by executing one (or all if you are curious) of the following commands:


### Maven

```
mvn clean package exec:java
```

### Gradle
 
```
gradle clean build run
```