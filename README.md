# AutoLoader
This project is able to locate and load all of the classes that inherit the given class/interface.
It will invoke their constructor and cache the instance for you to use it later on.
By doing this you'll save yourself some work and you'll see that you might see yourself making
less mistakes when for example registering an EventListener. Please keep in mind that this process is also
a lot heavier than when you're doing the normal way. Made by Chickenpowerrr.

## Getting started
These instructions will get you a copy of the project up and running on your local machine for development 
and testing purposes. Right now this project is just in it's testing phase and we won't suggest that you use
this on a production environment.

### Prerequisites
* Maven
* Lombok
* JDK 8+

### Installing
[![](https://jitpack.io/v/Chickenpowerrr/AutoLoader.svg)](https://jitpack.io/#Chickenpowerrr/AutoLoader) 

# Dependencies
#### Maven

```xml
     <repositories>
 	    <repository>
 	        <id>jitpack.io</id>
 	        <url>https://jitpack.io</url>
 	    </repository>
     </repositories>
    
    <dependency>
        <groupId>com.github.Chickenpowerrr</groupId>
        <artifactId>AutoLoader</artifactId>
        <version>1.0.1</version>
    </dependency>        
```

#### Gradle

```
    allprojects {
	repositories {
	    ...
	    maven { url 'https://jitpack.io' }
	}
    }
	
    dependencies {
        implementation 'com.github.Chickenpowerrr:AutoLoader:1.0.1'
    }	
```

#### Sbt

```
    resolvers += "jitpack" at "https://jitpack.io"
 	
    libraryDependencies += "com.github.Chickenpowerrr" % "AutoLoader" % "1.0.1"	
```



After that you should be able to use the AutoLoader project inside of your own project!

### Running the tests
This project uses JUnit to check it is working properly. Please make sure to run those tests when you're compiling.
If any test fails, please contact me. This means that the build you're using might by unstable and that you should 
use another version until it gets fixed.

## License

This project is licensed under the GPL-3.0 license - see the [LICENSE](LICENSE) file for details.
