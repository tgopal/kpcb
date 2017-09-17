# kpcb
Coding challenge for KPCB Engineering Fellow Application - Tejas Gopal

# Setting up the JUnit Environment 
First, to set up the environment (I'm on MacOS), you will need to add some things to your ~/.bash_profile file to make sure that JUnit runs correctly.

First, download the .jar files necessary from here: https://github.com/junit-team/junit4/wiki/Download-and-Install
I'm using the junit-4.12 jar, and the hamcrest-core-1.3 jar. 

For reference, to add these files to your classpath, the relevant part of my ~/.bash_profile file looks like this:

  export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home
  export JUNIT_HOME=/Users/tejasgopal/junit
  export CLASSPATH=$CLASSPATH:$JUNIT_HOME/junit4.12.jar:.
  export CLASSPATH=$CLASSPATH:$JUNIT_HOME/hamcrest-core-1.3.jar:.

