# AutoLoader
This project is able to locate and load all of the classes that inherit the given class/interface.
It will invoke their constructor and cache the instance for you to use it later on.
By doing this you'll save yourself some work and you'll see that you might see yourself making
less mistakes when for example registering an EventListener. Please keep in mind that this process is also
a lot heavier than when you're doing the normal way. 

## Getting started
These instructions will get you a copy of the project up and running on your local machine for development 
and testing purposes. Right now this project is just in it's testing phase and we won't suggest that you use
this on a production environment.

### Prerequisites
* Maven
* Lombok
* JDK 8+

### Installing
To use this project, you'll need to `clean install` this project first. After doing that you'll be able to
add the following maven dependency to your `pom.xml`

```xml
        <dependency>
            <groupId>com.gmail.chickenpowerrr</groupId>
            <artifactId>autoloader</artifactId>
            <version>1.0.1</version>
            <scope>compile</scope>
        </dependency>
```

after that you should be able to use the AutoLoader project inside of your own project!

### Running the tests
This project uses JUnit to check it is working properly. Please make sure to run those tests when you're compiling.
If any test fails, please contact me. This means that the build you're using might by unstable and that you should 
use another version until it gets fixed.

## License

This project is licensed under the GPL-3.0 - see the [LICENSE](LICENSE) file for details
