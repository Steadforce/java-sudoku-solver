package solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SudokuLoader implements SudokuProvider {
    private ArrayList<String> easySudokuBoards = null;
    private ArrayList<String> hardSudokuBoards = null;
    private ArrayList<String> veryHardSudokuBoards = null;
    
    private SudokuIO ioHandler9x9 = new SudokuSimpleStringIO();

    @Override
    public SudokuBoard getEasySudokuBoard() {
        if(easySudokuBoards == null) {
            easySudokuBoards = loadBoardFile("easy50.txt");
        }
        
        String boardRepresentation = getRandomBoardFrom(easySudokuBoards);
        SudokuBoard sudokuBoard = makeSudokuBoard(boardRepresentation);
        return sudokuBoard;
    }

    @Override
    public SudokuBoard getHardSudokuBoard() {
        if(hardSudokuBoards == null) {
            hardSudokuBoards = loadBoardFile("hard95.txt");
        }
        
        String boardRepresentation = getRandomBoardFrom(hardSudokuBoards);
        SudokuBoard sudokuBoard = makeSudokuBoard(boardRepresentation);
        return sudokuBoard;
    }

    @Override
    public SudokuBoard getVeryHardSudokuBoard() {
        if(veryHardSudokuBoards == null) {
            veryHardSudokuBoards = loadBoardFile("hardest11.txt");
        }
        
        String boardRepresentation = getRandomBoardFrom(veryHardSudokuBoards);
        SudokuBoard sudokuBoard = makeSudokuBoard(boardRepresentation);
        return sudokuBoard;
    }

    private ArrayList<String> loadBoardFile(String filename) {
        ArrayList<String> boardRepresentations = new ArrayList<String>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("gamedata/" + filename));
            String line = reader.readLine();
            while(line != null) {
                boardRepresentations.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return boardRepresentations;
    }

    private String getRandomBoardFrom(ArrayList<String> easySudokuBoards2) {
        Random rand = new Random();
        return easySudokuBoards2.get(rand.nextInt(easySudokuBoards2.size()));
    }

    private SudokuBoard makeSudokuBoard(String boardRepresentation) {
        SudokuBoard sudokuBoard = null;
        try {
            sudokuBoard = new SudokuBoard(ioHandler9x9, boardRepresentation);
        } catch (BoardParsingException e) {
            e.printStackTrace();
        }
        return sudokuBoard;
    }

}
