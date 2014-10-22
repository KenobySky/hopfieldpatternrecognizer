# Hopfield Pattern Recognizer # 


### What is this repository for? ###

Using Hopfield Neural network, this app is able to identify patterns from a grid / matrix.When the user click in one of the cells, it either enables it or disables. The Neural network can be trained with that grid and the user can present another or the same grid for recognizement.

Examples:

Picture 1 : Main screen showing a 2x2 grid
![Main Screen.png](https://bitbucket.org/repo/po7o54/images/3985879078-Main%20Screen.png)


Picture 2 : Settings Window
![Settings .png](https://bitbucket.org/repo/po7o54/images/4260489552-Settings%20.png)


Picture 3 : Training a pattern
![Train.png](https://bitbucket.org/repo/po7o54/images/2625006082-Train.png)


* Version
Currently its just a small program.But its planned to add a lot more features.



### How do I get set up? ###

here are precompiled jar files available for [download](https://bitbucket.org/andrelopes1705/tsm-ga-solver/downloads). Students and contributors can however build the application very easily.  
We use [gradle](http://gradle.org) for builds and dependency management. Assuming you have [Mercurial](http://mercurial.selenic.com) installed, open a shell and run the following commands to download the source, build an executable jar and run it:

```
#!bash
hg clone http://bitbucket.org/andrelopes1705/tsm-ga-solver TSM-GA-Solver
cd TSM-GA-Solver
gradlew.bat :desktop:run
```

The executable jar can be found in `desktop/build/libs/`. You can rebuild it any time without running the application using `gradlew.bat :desktop:build`.

If you do not want to install Mercurial you can also [download](https://bitbucket.org/andrelopes1705hopfieldpatternrecognizer/downloads) the repository normally.



### Who do I talk to? ###

Send a private message to [André Lopes](http://bitbucket.org/andrelopes1705) 