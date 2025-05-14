# inheritence-level
Java CLI program which takes in a filepath to a .JAR file and prints out the classes
and their level of abstraction or the amount of classes per abstraction level.

Take for example three classes: ``A,B,C``.

``Class A extends Class B``, 

``Class B extends Class C``.

Class A has a abstraction-count of two, because you can go two layers deep. If you go deeper than that, you reach the Object.class. Because every class is a object, we ignore it.

The example output would be
```
Class A: 2
Class B: 1
Class C: 0
```

The program ask you to specify whether you want to display a listing of classes and their level (option 1) or the amount of classes per level (option 2). After you have set the filepath, type in the option as a number.

# How to run the program?
Currently there is only the source code. Download it, make sure you have a working JDK
and run the program via the main-method in Main.java.

# Issues/Feedback

If you have any issues or feedback, add it as a issue.