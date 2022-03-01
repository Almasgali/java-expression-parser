# Java expression parser.

Math expressions parser on Java, working on Jetty - server, localhost:8080.

Can parse and evaluate expressions with 3 or less variables (x, y, z).

Supported operations:
 * Add
   * Syntax: *operand* **+** *operand*
 * Subtract
   * Syntax: *operand* **-** *operand*
 * Divide
   * Syntax: *operand* **/** *operand*
 * Multiply
   * Syntax: *operand* **\*** *operand*
 * Min
   * Syntax: *operand* **min** *operand*
 * Max
   * Syntax: *operand* **max** *operand*
 * LeftZeros
   * Syntax: **l0** *operand*
   * Counts left zero bits in the number.
 * RightZeros
   * Syntax: **t0** *operand*
   * Counts right zero bits in the number.
 * UnaryMinus
   * Syntax: **-** *operand*

Builds with maven.
