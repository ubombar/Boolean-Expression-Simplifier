# Boolean-Expression-Simplifier
Goal is to come up with a *general method* for simplifying any kind of *boolean expression*. I also aim to provide an expression executer.
**Important** note that, the syntax of the expressions will be same as [SystemVerilog](https://en.wikipedia.org/wiki/SystemVerilog). For example:
> not (not A and not B) 

will be written as 
>\~(~A & ~B)

and hopefully will be simplified as

>A | B
