Approximate-Data
================
Approximating software behavior by reducing precision of floating-point representation.

Design Patterns
===============
Composite  
Strategy

Composite
---------
Component: XML tags  
Composite: XML tags with opening and closing tags  
Leaf: XML tags with only opening tags    

Textual input that is not XML is stored within both of them.

Strategy
--------
There are three strategies utilized to selectively replace double variables using floats.    

* Naive Strategy: All existing variables are replaced.
* Random Strategy: A random k out of n variables are replaced.
* Loop Strategy: Only variables inside loops are replaced.
