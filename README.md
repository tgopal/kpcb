# KPCB Engineering Fellow Application - Tejas Gopal
Coding challenge for KPCB Engineering Fellow Application 2018: Implement a fixed-size hashmap that maps String keys to arbitrary object values using only primitive-types.

Author: Tejas Gopal, B.S. Computer Science UCSD 2019

This repository holds all source, tests, and executable files associated with this part of the application!

Let's dive in... :)

## Information about the implementation

Here is some information and background about my implementation:
* Since the hashmap needs to be fixed-size, I found the closest power of 2 to the user specified size. This ensures that my hash function always generates a valid index between 0 and that power of 2 (it makes use of bitwise AND).
* I used a custom linked list object to handle collisions.
* Here is a table of runtimes, which tells you exactly how efficient operations are in the best/worst cases! Let n be the number of elements (# of key-value pairs) within the hashmap prior to the operation.

Operation | Best/Average Case | Worst Case
------------ | ------------- | ------------- 
set(key,value) | O(1) | O(n) 
get(key) | O(1) | O(n) 
delete(key) | O(1) | O(n)

* `PrimitiveHashmap.java` -> the implementation and definition of my hashmap. (**non-executable**)
* `PrimitiveHashmapSandbox.java` -> testing the Hashmap from a user's perspective (**executable**)
* `PrimitiveHashmapTest.java` -> the suite of unit tests. (**non-executable**)
* `PrimitiveHashmapTestRunner.java` -> to run all the unit tests. (**executable**)

## Setting up the JUnit Environment + Requirements
Using the Hashmap requires that you have Java installed on your machine. 

To set up the testing environment (**I'm on MacOS**), you will need to add some things to your ~/.bash_profile file to make sure that JUnit runs correctly.

First, download the .jar files necessary from [here](https://github.com/junit-team/junit4/wiki/Download-and-Install).
I'm using the `junit-4.12.jar`, and the `hamcrest-core-1.3.jar` files from the link. 

For reference, to add these files to your classpath, the relevant part of my ~/.bash_profile file is below. 
* I've set up JAVA_HOME to point to where my jdk is stored.
* I've set up JUNIT_HOME to be a directory under my home directory, which includes the junit and hamcrest jar files.

```
  export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home
  export JUNIT_HOME=/Users/tejasgopal/junit
  export CLASSPATH=$CLASSPATH:$JUNIT_HOME/junit4.12.jar:.
  export CLASSPATH=$CLASSPATH:$JUNIT_HOME/hamcrest-core-1.3.jar:.
```

## The best part ... testing Tejas's Hashmap!

The **PrimHashmapSandbox** class is exactly what you are looking for to test out the hashmap! For the sake of simplicity, it only supports String and Integer value object types, but the hashmap will work for any custom object. Before running, run the following command:

`$ javac PrimHashmap.java PrimHashmapSandbox.java PrimHashmapTest.java PrimHashmapTestRunner.java`

Ignore warnings - these are due to Java not liking checks for generic type casting, but don't affect runtime behavior.
Then, to run the Sandbox:

`$ java PrimHashmapSandbox`
... and follow the prompts.

The **PrimHashmapTestRunner** class will run the full suite of unit tests defined in `PrimHashmapTest.java`. To run the full suite of tests cases:

`$ javac PrimHashmap.java PrimHashmapSandbox.java PrimHashmapTest.java PrimHashmapTestRunner.java`
`$ java PrimHashmapTestRunner`

Any feedback is both welcome and really appreciated! Happy hashing :)
