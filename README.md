# matheval

## Introduction
This is a command-line application designed to evaluate mathematical expressions entered by the user. It supports basic arithmetic operations such as addition, subtraction, multiplication, and division, as well as standard math functions like cos, sin, tan, asin, acos, atan, sqrt, and pow (^). The application processes the input expression and returns the result if it's a valid mathematical expression. If the expression is malformed, the application reports an error.

## Usage
To use the Math Expression Evaluator, follow these steps:

1. Clone or download the repository to your local machine.
2. Navigate to the root directory of the project.
3. Compile the source code using a Java compiler:
    ```bash
    javac Main.java
    ```
4. Run the application with the desired mathematical expression :
    ```bash
    java Main 'atan(cos(9.6) * 4^-1) - (sin(6.5))^2'
    ```

## Dependencies
This project has no external dependencies. It is implemented in Java and does not rely on any third-party libraries or frameworks.

## Design Overview
The application is designed to parse mathematical expressions, tokenize them, and evaluate them according to the standard order of operations. The `Scanner` class processes the input string and generates a list of tokens representing the expression. The `Parser` class parses the tokenized expression to ensure it is syntactically correct and converts it into a form suitable for evaluation. 

## Limitations and Future Improvements
- The current implementation does not support complex numbers or advanced mathematical functions beyond those specified in the problem statement.
- Error reporting could be improved to provide more detailed information about the nature of syntax errors or other issues encountered during evaluation.
