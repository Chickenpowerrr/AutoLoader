# AutoLoader
Autoloader is able to locate and load all of the classes that inherit a given class/interface.
The loader will invoke the constructors and cache the instances, such that you can use it later.
However, keep in mind that this way of dynamically loading classes is expensive.
Therefore one should not use this in performance sensitive settings.

## Getting started
Please ensure that you follow these instructions for setting up AutoLoader on your machine.
The project is still in the testing phase and thus full stability is not yet ensured.

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



### Running the tests
This project uses JUnit to ensure its proper operation. Please make sure to run these tests after you've finished compiling.
If any test fails, please contact me. 
While waiting for a fix, please use a different version.

## License

This project is licensed under the GPL-3.0 license - see the [LICENSE](LICENSE) file for details
