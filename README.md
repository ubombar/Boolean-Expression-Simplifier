# What is That?
The *Boolean Expression Simplifier* is a piece of algorithm that aims to provide *Parser*, *Executer* and *Simplifier* for manipulating boolean expressions.

## How?
The expressions are in [Python](http://thomas-cokelaer.info/tutorials/python/boolean.html) or [SystemVerilog](https://en.wikipedia.org/wiki/SystemVerilog) format. Parser converts the String expression to a executable form which is a tree consists of operators. These operators are responsible for executing the node under them. By that execution is enabled. Simplification process however is a bit tricky and not implemented yet.

### String Representation of a Boolean Expression
The followings are the *Python* and *SystemVerilog* style representations
>Y = A and (B or not C) 
>Y = A & (B | ~C)

## Status
The implementation of parser and simplifier is not done yet. But the executer is working perfectly.
