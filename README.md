[![Build Status](https://travis-ci.org/alexey-kolonitsky/komander.svg?branch=master)](https://travis-ci.org/alexey-kolonitsky/komander) [![Coverage Status](https://coveralls.io/repos/alexey-kolonitsky/komander/badge.svg?branch=master)](https://coveralls.io/r/alexey-kolonitsky/komander?branch=master) 

Komander is a simple easy to use framework to develop command line application
with a solution for common issues.

Komander designed to help developer split functionality to different commands
inside the application and decouple them from each other. The main tool of 
decoupling is a Dependency Injection and Command pattern. So if you don't like 
Design patterns don't use Komander.

Advanages
---------
1. Decoupling the code of differenct commands - each command line sub-command 
  invoke own Command class in application.
  
2. Processing command line arguments -- Komander create instance of Command only 
  if requested sub-command has been found and input parameters will be matched to 
  the command input. Otherwise Komander will print friendly message about mistake 
  in command input.
  
3. Documentation automaticaly generated based on commands input declatation.

4. Common approach for configurations

This is a four aims I chased when creaed this framework. 

Definitions
-----------
**sub-command** -- is first argument of the command line application. As example 
`git pull` is command there are `pull` is a sub-command.
 
**argument** -- means sub-command argument, is a parameters which user must define
when call the command. 

**flag** or **switcher** -- means special type of input parameter without value
used only to switch on/off some additional functionality or switch command workflow.
As example one of the popular switcher available in lots of command line tools
`--verbose` which turn on more logs to the command output.

This is the simplies way to generalize command line interface and this siplitity 
allow to siplify **Komander** usage and extension.

Setup
=====
To integrate Komander to your application just add maven dependency 
```       
 <dependency>
   <groupId>org.kolonitsky</groupId>
   <artifactId>komander</artifactId>
   <version>1.0-SNAPSHOT</version>
 </dependency>
```

or download and put ``komander.jar`` file to the CLASSPATH

How to Use
==========

1. First of all you have to write your code in command classes which implements
IKommand interface.
2. Then you have to create instance of `Komander` class, with collection of all
commands which your will use.
3. At least you have to redirect command line input to the `Kommander.run()` input.



How to Extends
==============

Input
=====
Command line allow to create to various input interface and a lot of tools use 
their own format 

* Arguments
* Flags

