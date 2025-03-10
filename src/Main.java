import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class TicTacToe {
    static char[][] board;

    public TicTacToe() {
        board = new char[3][3];
        initBoard();
    }

    static void initBoard() {
        for (char[] row : board) {
            Arrays.fill(row, ' ');
        }
    }

    static void displayBoard() {
        System.out.println("-------------");
        for (char[] row : board) {
            System.out.print("| ");
            for (char cell : row) {
                System.out.print(cell + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    static void placeXO(int r, int c, char m) {
        if (r >= 0 && r < 3 && c >= 0 && c < 3) {
            board[r][c] = m;
        } else {
            System.out.println("Invalid move");
        }
    }

    static boolean checkColumnWin() {
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true;
            }
        }
        return false;
    }

    static boolean checkRowWin() {
        for (char[] row : board) {
            if (row[0] != ' ' && row[0] == row[1] && row[1] == row[2]) {
                return true;
            }
        }
        return false;
    }

    static boolean checkDiagonalWin() {
        return (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }

    static boolean checkTie() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}

abstract class Player {
    String name;
    char mark;
abstract void makeMove();
    boolean isValidMove(int r, int c) {
        return r >= 0 && r < 3 && c >= 0 && c < 3 && TicTacToe.board[r][c] == ' ';
    }
}

class HumanPlayer extends Player {
    HumanPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    void makeMove() {
        Scanner scan = new Scanner(System.in);
        int r, c;
        do {
            System.out.println(name + ", enter the row and column (0-2):");
            r = scan.nextInt();
            c = scan.nextInt();
        } while (!isValidMove(r, c)); // ✅ FIXED: No more inversion
        TicTacToe.placeXO(r, c, mark);
    }
}

class AIPlayer extends Player {
    AIPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    void makeMove() {
        int r, c;
        Random rs = new Random();
        do {
            r = rs.nextInt(3);
            c = rs.nextInt(3);
        } while (!isValidMove(r, c)); // ✅ FIXED: No more inversion
        System.out.println("AI placed at: " + r + ", " + c);
        TicTacToe.placeXO(r, c, mark);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter Player 1's name: ");
        String player1Name = scan.nextLine();

        System.out.println("Choose Game Mode: ");
        System.out.println("1 - Single Player (vs AI)");
        System.out.println("2 - Multiplayer (vs another player)");
        int mode = scan.nextInt();
        scan.nextLine(); // Consume newline

        Player p1 = new HumanPlayer(player1Name, 'X');
        Player p2;

        if (mode == 1) {
            p2 = new AIPlayer("Computer", 'O');
        } else {
            System.out.print("Enter Player 2's name: ");
            String player2Name = scan.nextLine();
            p2 = new HumanPlayer(player2Name, 'O');
        }

        Player currentPlayer = p1;

        while (true) {
            System.out.println(currentPlayer.name + "'s turn");
            currentPlayer.makeMove();
            TicTacToe.displayBoard();

            if (TicTacToe.checkColumnWin() || TicTacToe.checkRowWin() || TicTacToe.checkDiagonalWin()) {
                System.out.println(currentPlayer.name + " has won!");
                break;
            } else if (TicTacToe.checkTie()) {
                System.out.println("Game is a tie!");
                break;
            } else {
                currentPlayer = (currentPlayer == p1) ? p2 : p1;
            }
        }
    }
}












