# AtmegaSim

The goal of this project is to join a Java program with an Atmega microcontroller C program, in order
to improve debugging capabilities. For example if you have to implement a communicaton protocol stack 
between a Java program on the one side and a microcontroller on the other side.

This project is created and tested with Netbeans 8.2 under Linux. The usage of other 
IDEs, versions or operating systems may cause problems which have not been checked up to now.

## Build and run

Follow the following procedure to build and run this project...

1. `git clone <repository>`
2. `cd <repository>/AtmegaSimJava`
3. `ant jar`  
     Now the Java project is build and the tool *javah* creates the file 
     **AtmegaSimSharedLib/src/jni_App.h** which is needed by the shared library project.
     You can also use Netbeans, open the project and build the project.
4. `cd ../AtmegaSimSharedLib`
5. `make`  
     Now the native shared library is build and saved in...  
     **AtmegaSimSharedLib/dist/Debug_linux_Gnu-Linux/libAtmegaSimSharedLib.so**
6.  `cd ..`
7.  Start the application with...  
    `java -Djava.library.path=$(pwd)/AtmegaSimJava -jar AtmegaSimJava/dist/AtmegaSimJava.jar`

The shell will replace `$(pwd)` (print working directory) by the current directory path. The Define *java.library.path*
must be set, otherwise the shared library will not be found by the Java VM. An alternative is 
to locate the library file (or a symbolic link) in the directory `/usr/java/packages/lib/amd64/`

## Java Project

See subproject [AtmegaSimJava](AtmegaSimJava)

The Java Native Interface (JNI) is used to bind the native shared library 
[libAtmegaSimSharedLib.so](AtmegaSimJava/libAtmegaSimSharedLib.so) with the Java VM. 
The library file itself is build in the subproject **AtmegaSimSharedLib** and 
only a symbolic link is used to make the library available for Javas *System.load(..)*.
An alternative systemwide location is [/usr/java/packages/lib/amd64/](file:///usr/java/packages/lib/amd64/).

So you need to build AtmegaSimSharedLib first, before you can start the Java application.

## Native Shared Library C Project

See subproject [AtmegaSimSharedLib](AtmegaSimSharedLib).

In the directory [src](AtmegaSimSharedLib/src) are the the source files for building the native shared library. 
The files [global.h](ArduinoNano/src/global.h), [app.h](ArduinoNano/src/app.h) and [app.c](ArduinoNano/src/app.c) 
are only symbolic links to the original microcontroller project [ArduinoNano](ArduinoNano).

The project is compiled and linked with the GNU **-g** option, to support debugging information inside the 
shared library.

Please check the correct location of:  

* [/usr/lib/jvm/java-8-oracle/include/linux](file:///usr/lib/jvm/java-8-oracle/include/linux)  
* [/usr/lib/jvm/java-8-oracle/include/jni.h](file:///usr/lib/jvm/java-8-oracle/include/jni.h)

If you cannot find these two header files there, you have to set the proper directory. Open Netbeans project properties,  category Build/C++ Compile and set include directories to the location of your Java Development Kit. You will
also find these settings in [configurations.xml](AtmegaSimSharedLib/nbproject/configurations.xml).

The header files are needed by [jni_App.c](AtmegaSimSharedLib/src/jni_App.c).

--------------------------------------------------------

## Debugging with gdb

See also:
* [https://www.gnu.org/software/gdb/gdb.html](https://www.gnu.org/software/gdb/gdb.html)
* [https://www.cs.cmu.edu/~gilpin/tutorial/](https://www.cs.cmu.edu/~gilpin/tutorial/)
* [http://www.dirac.org/linux/gdb/](http://www.dirac.org/linux/gdb/)

At first start the Java GUI application and afterwards the GNU-Debugger **gdb**.
Using two different shells would make sense.

Start in a first shell (or use Netbeans IDE to start the Java application):

1. `cd <project-dir>/AtmegaSimJava`
2. `java -Djava.library.path=$(pwd) -jar dist/AtmegaSimJava.jar &`

... and in a second shell:

1. `sudo -i`
2. `gdb`


Now you have to attach the GNU-Debugger to the running Java program.
For the command `attach` you need the process identifier (the PID) of the running Java application.
This PID is shown in the title bar of the Java GUI application.

After attachment you can pause the program with pressing CTRL+C. 
Now you can use the following commands to debug your 'simulated' microcontroller program.

```
attach <pid>
detach

info source
info functions
info breakpoints
info proc map

list <function>
break <line>

frame
continue
next
info args
info locals
info
```
With `break` you can set breakpoints.
With `next` you step over the call of functions. With `step` you 
can step into functions. With `continue` you can continue the program 
up to next breakpoint. You can also use abbreviations (b, n, s, c).

For example:

```
attach 7123
list Java_jni_App_main
break 126
c

list
n
n
s
n

detach
quit
```
