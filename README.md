# inheritence-level
Java CLI program which takes in a filepath to a .JAR file and prints out classes
and their corresponding level of abstraction or in other words: how deep does the abstraction go?

Take for example three classes: A,B,C.
Class A extends Class B, Class B extends Class C.

Class A has a abstraction-count of two, because you can go two layers deep. If you go deeper than that, you reach the Object.class. Because every class is a object, we ignore that base-class.

The example output would be
Class A: 2
Class B: 1
Class C: 0

# How to run the program?
Currently there is only the source code. Download it, make sure you have a working JDK
and run the program via the main-method in Main.java.

# Issues/Feedback

If you have any issues or feedback, add it as a issue.