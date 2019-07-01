# Sudoku Solver

This is a Java implementation of the Sudoku Solver as presented by Peter Norvig
and implemented in Python (http://norvig.com/sudoku.html). This implementation
was done by [Simon Schmitz](mailto:simon.schmitz@steadforce.com) at [Steadforce GmbH](https://steadforce.com/).

## Data Structure

The data used by the solver is represented in the class `SudokuBoard`.
One field to put a number is called a square. Squares are referenced using a string
which is composed from its row (letters A-I) and column (numbers 1-9).
These square indices are provided by the field
`Collection<String> squareIndices`.

```
 A1 A2 A3| A4 A5 A6| A7 A8 A9
 B1 B2 B3| B4 B5 B6| B7 B8 B9
 C1 C2 C3| C4 C5 C6| C7 C8 C9
---------+---------+---------
 D1 D2 D3| D4 D5 D6| D7 D8 D9
 E1 E2 E3| E4 E5 E6| E7 E8 E9
 F1 F2 F3| F4 F5 F6| F7 F8 F9
---------+---------+---------
 G1 G2 G3| G4 G5 G6| G7 G8 G9
 H1 H2 H3| H4 H5 H6| H7 H8 H9
 I1 I2 I3| I4 I5 I6| I7 I8 I9
```

To solve a Sudoku, each row, column and indicated 3-by-3 block have to contain
the values 1 to 9, no values can occur more than once. Each such row, column or block
is called a unit. So a unit is a set of squares and the set of all units
is provided by the constant `Collection<Collection<String>> units`.

For each square, we save the units it belongs to in the hashmap
`Map<String, Collection<Collection<String>>> squareUnitRelations`.
Let's for example consider the field C2. It belongs to the units defined by its row,
column and block. These units can be accessed using `squareUnitRelations.get("C2")`.
The field belongs to the units as shown below.
The union of all squares contained in the units of C2 are its peers
(`Map<String, Collection<String>> squarePeers`).

```
    A2   |         |                    |         |            A1 A2 A3|         |         
    B2   |         |                    |         |            B1 B2 B3|         |         
    C2   |         |            C1 C2 C3| C4 C5 C6| C7 C8 C9   C1 C2 C3|         |         
---------+---------+---------  ---------+---------+---------  ---------+---------+---------
    D2   |         |                    |         |                    |         |         
    E2   |         |                    |         |                    |         |         
    F2   |         |                    |         |                    |         |         
---------+---------+---------  ---------+---------+---------  ---------+---------+---------
    G2   |         |                    |         |                    |         |         
    H2   |         |                    |         |                    |         |         
    I2   |         |                    |         |                    |         |         
```

# Importing a board

To import a board from a string, any parser implementing the interface `SudokuIO`
can be used. The parser to use is injected into each instance of a board by passing it to the factory merhod.
The class `SudokuSimpleStringIO` provides one possible implementation
to parse boards as follows:
* The parser fills the board starting with the first row, inserting values for each column
and continuing with the next row, until all rows are filled.
* If a number from 1 to 9 is encountered in the input string,
the current square is set to this value. If a 0 or a '.' is found,
the current square is set to be unfilled.
All other characters are ignored by the parser.

This means that both string representations below are interpreted as the same board by the
parser:
```
4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......


4 . . |. . . |8 . 5 
. 3 . |. . . |. . . 
. . . |7 . . |. . . 
------+------+------
. 2 . |. . . |. 6 . 
. . . |. 8 . |4 . . 
. . . |. 1 . |. . . 
------+------+------
. . . |6 . 3 |. 7 . 
5 . . |2 . . |. . . 
1 . 4 |. . . |. . . 
```

The parsed board is then saved in the variable `Map<String, Character> initialBoard`.

## Solving a board

To solve a board, one calls the method `solveBoard(SudokuBoard board)` provided
by the class `SudokuSolver`. This method first calls the method `processInitialBoard(SudokuBoard board)` which does the following:
* Populate the board with the list of all possible characters for each square (1-9).
The variable `Map<String, Collection<Character>> currentBoard` contains this set
of possible characters for each square.
* For each square: if it is assigned in the `initialBoard`,
set its value in the `currentBoard`. Then, the value is deleted from the remaining possible
values of all peer squares.
* If there is only one possible value left for a square,
this value is again recursively deleted from all the square's peers.

Using this algorithm for the above mentioned Sudoku, one ends up with a board as shown
below, where the remaining possible entries for each square can be seen.

```
   4      1679   12679  |  139     2369    269   |   8      1239     5    
 26789     3    1256789 | 14589   24569   245689 | 12679    1249   124679 
  2689   15689   125689 |   7     234569  245689 | 12369   12349   123469 
------------------------+------------------------+------------------------
  3789     2     15789  |  3459   34579    4579  | 13579     6     13789  
  3679   15679   15679  |  359      8     25679  |   4     12359   12379  
 36789     4     56789  |  359      1     25679  | 23579   23589   23789  
------------------------+------------------------+------------------------
  289      89     289   |   6      459      3    |  1259     7     12489  
   5      6789     3    |   2      479      1    |   69     489     4689  
   1      6789     4    |  589     579     5789  | 23569   23589   23689  
```

Next, a depth first search is performed assigning one remaining possible value to a square
and deleting the same value from peers recursively. If the entire board can be filled like
this without introducing contradictions, the solver returns `true`,
if not, it returns `false`.

```
4 . . |. . . |8 . 5     4 1 7 |3 6 9 |8 2 5 
. 3 . |. . . |. . .     6 3 2 |1 5 8 |9 4 7
. . . |7 . . |. . .     9 5 8 |7 2 4 |3 1 6 
------+------+------    ------+------+------
. 2 . |. . . |. 6 .     8 2 5 |4 3 7 |1 6 9 
. . . |. 8 . |4 . .     7 9 1 |5 8 6 |4 3 2 
. . . |. 1 . |. . .     3 4 6 |9 1 2 |7 5 8 
------+------+------    ------+------+------
. . . |6 . 3 |. 7 .     2 8 9 |6 4 3 |5 7 1 
5 . . |2 . . |. . .     5 7 3 |2 9 1 |6 8 4 
1 . 4 |. . . |. . .     1 6 4 |8 7 5 |2 9 3 
```

## Example

```Java
String boardInputString =
        ". . . |. . 6 |. . . \r\n" + 
        ". 5 9 |. . . |. . 8 \r\n" + 
        "2 . . |. . 8 |. . . \r\n" + 
        "------+------+------\r\n" + 
        ". 4 5 |. . . |. . . \r\n" + 
        ". . 3 |. . . |. . . \r\n" + 
        ". . 6 |. . 3 |. 5 4 \r\n" + 
        "------+------+------\r\n" + 
        ". . . |3 2 5 |. . 6 \r\n" + 
        ". . . |. . . |. . . \r\n" + 
        ". . . |. . . |. . . ";
SudokuIO ioHandler = new SudokuSimpleStringIO();
BoardFactory boardFactory9x9 = new NineByNineBoardFactory();
SudokuSolver solver = new SudokuSolver();

SudokuBoard board = boardFactory9x9.generateNewBoard(ioHandler);
boardFactory9x9.fillBoard(board, boardInputString);
solver.processInitialBoard(board);
boolean solverReturnValue = solver.solveBoard();

String boardDisplayString =
        "4 3 8 |7 9 6 |2 1 5 \n" + 
        "6 5 9 |1 3 2 |4 7 8 \n" + 
        "2 7 1 |4 5 8 |6 9 3 \n" + 
        "------+------+------\n" + 
        "8 4 5 |2 1 9 |3 6 7 \n" + 
        "7 1 3 |5 6 4 |8 2 9 \n" + 
        "9 2 6 |8 7 3 |1 5 4 \n" + 
        "------+------+------\n" + 
        "1 9 4 |3 2 5 |7 8 6 \n" + 
        "3 6 2 |9 8 7 |5 4 1 \n" + 
        "5 8 7 |6 4 1 |9 3 2 \n";
assertTrue(solverReturnValue);
assertEquals(boardDisplayString, board.makeDisplayable());
```

## Dependencies

* [org.apache.commons.lang3.StringUtils](https://commons.apache.org/proper/commons-lang/download_lang.cgi)
