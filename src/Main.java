import java.util.Random;
import java.util.Scanner;

class TicTacToe
{
   static char[][] board;

    public TicTacToe()
    {
        board = new char[3][3];
        initBoard();
    }

   static void initBoard()
    {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

   static void dboard()
    {
           System.out.println("-------------");
            for(int i=0; i<board.length; i++)
            {
                System.out.print("| ");
                for(int j=0; j<board[i].length; j++)
                {
                   System.out.print(board[i][j] + " | ");

                }
                System.out.println();
                System.out.println("-------------");
            }
    }

   static void placeXO(int r,int c,char m)
    {
        if(r >= 0 && r <= 2 && c >= 0 && c <= 2) {
            board[r][c] = m;
        }
        else{
            System.out.println("Invalid");
        }
    }

   static boolean checkcolwin()
    {
        for(int j=0; j<=2; j++){
            if(board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j] ){
                return true;
            }
        }
        return false;
    }

   static boolean checkrowin()
    {
        for(int i=0; i<=2; i++){
            if(board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2] ){
                return true;
            }
        }
        return false;
    }

   static boolean checkdiwin()
    {
        if(board[0][0] != ' ' &&  board[0][0] == board[1][1] &&  board[1][1] == board[2][2] ||
                board[0][2] != ' ' &&  board[0][2] == board[1][1] &&  board[1][1] == board[2][0]){
            return true;
        }
        return false;
    }

    static boolean checkTie()
    {
        for(int i=0;i<=2;i++)
        {
            for(int j=0;j<=2;j++)
            {
                if(board[i][j] == ' ')
                {
                   return false;
                }
            }
        }
        return true;
    }


}

abstract class Player
{
    String n;
    char m;
    abstract void makeMove();
    boolean isValidmove(int r,int c)
    {
        if(r >= 0 && r <= 2 && c >= 0 && c <= 2)
        {
            if(TicTacToe.board[r][c] == ' ')
            {
                return true;
            }
        }
        return false;
    }
}

class HumanPlayer extends Player
{
    Scanner scan = new Scanner(System.in);
    HumanPlayer(String n, char m)
    {
        this.n = n;
        this.m = m;
    }
    void makeMove()
    {
        Scanner scan = new Scanner(System.in);
        int r;
        int c;
        do{

            System.out.println("Enter the row and col");
            r = scan.nextInt();
            c = scan.nextInt();
        }while(!isValidmove(r,c));
        TicTacToe.placeXO(r,c,m);

    }
}

class AIPlayer extends Player
{
    AIPlayer(String n, char m) {
        this.n = n;
        this.m = m;
    }

    void makeMove()
    {
        Scanner scan = new Scanner(System.in);
        int r;
        int c;
        do
        {
            Random rs = new Random();
            r = rs.nextInt(3);
            c = rs.nextInt(3);
        } while (!isValidmove(r, c));
        System.out.println(n + " (AI) placed at: " + r + ", " + c);
        TicTacToe.placeXO(r, c, m);
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose Game Mode:");
        System.out.println("1 - Single Player (vs AI)");
        System.out.println("2 - Multiplayer (vs another player)");
        int mode = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        TicTacToe t = new TicTacToe();
        Player p1, p2;

        System.out.print("Enter Player 1 name: ");
        String player1Name = scanner.nextLine();
        p1 = new HumanPlayer(player1Name, 'X');

        if (mode == 1) {
            p2 = new AIPlayer("Computer", 'O'); // AI as second player
        } else {
            System.out.print("Enter Player 2 name: ");
            String player2Name = scanner.nextLine();
            p2 = new HumanPlayer(player2Name, 'O'); // Human as second player
        }

        Player cp = p1; // Current player

        while (true) { // Game loop
            System.out.println(cp.n + "'s turn (" + cp.m + ")");
            cp.makeMove();
            TicTacToe.dboard();

            // Check for win
            if (TicTacToe.checkcolwin() || TicTacToe.checkrowin() || TicTacToe.checkdiwin()) {
                System.out.println(cp.n + " has won!");
                break;
            }
            // Check for tie
            else if (TicTacToe.checkTie()) {
                System.out.println("Game is a tie!");
                break;
            }
            // Switch players
            else {
                cp = (cp == p1) ? p2 : p1;
            }
        }

        scanner.close();
    }
}

