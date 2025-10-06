package TicTacToe;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {


    Deque<Player> players;
    Board gameBoard;

    public void initializeGame(){
        //creating 2 players

        players=new LinkedList<>();
        PlayingPieceX crossPiece=new PlayingPieceX();
        Player player1=new Player("Player1",crossPiece);

        PlayingPieceO noughtsPiece=new PlayingPieceO();
        Player player2=new Player("Player2",noughtsPiece);

        players.add(player1);
        players.add(player2);

        gameBoard=new Board(3);

    }


    public String startGame(){

        boolean noWinner=true;
        while(noWinner) {

            //take out the player whose turn is and also put the player back to the list
            Player playerTurn = players.removeFirst();


            //get the freespca efrom the board 1. show the board
            gameBoard.printBoard();
            List<Pair<Integer, Integer>> freeSpaces = gameBoard.getFreeCells();
            if (freeSpaces.isEmpty()) {
                noWinner = false;
                continue;
            }

            //read te user input 2. Read the input
            System.out.println("Player:" + playerTurn.name + "Enter row,column:  ");
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputCol = Integer.valueOf(values[1]);

            //place the Piece 3.place the piece
            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow, inputCol, playerTurn.playingPiece);
            if (!pieceAddedSuccessfully) {
                //player cannot insert the piece into this cell,player ahs to choose another cell
                System.out.println("Incorrect position choosen,try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputRow, inputCol, playerTurn.playingPiece.pieceType);
            if (winner) { //4. check winner
                return playerTurn.name; //5. next turn
            }
        }
        return "tie";





        }

        public boolean isThereWinner(int row,int col,PieceType pieceType){
        boolean isRowMatch=true;
        boolean isColumnMatch=true;
        boolean isDiagonalMatch=true;
        boolean isAntiDiagonalMatch=false;

        for(int i=0;i< gameBoard.size;i++){
            //match row if anything mismatch found mark it false
            if(gameBoard.board[i][col]==null || gameBoard.board[i][col].pieceType!=pieceType){
                isRowMatch=false;
            }
        }
            //match column if anything mismatch found mark it false
            for(int i=0;i< gameBoard.size;i++){
                if(gameBoard.board[row][i]==null || gameBoard.board[row][i].pieceType!=pieceType){
                    isColumnMatch=false;
                }
            }
            //match for the diagonal
            for(int i=0,j=0;i< gameBoard.size &&  j<gameBoard.size;i++,j++){
                if(gameBoard.board[i][j]==null || gameBoard.board[i][j].pieceType!=pieceType){
                    isDiagonalMatch=false;
                }
            }

            //match for the antiDiagonal
            for(int i=0,j= gameBoard.size-1;i< gameBoard.size &&  j>=0;i++,j--){
                if(gameBoard.board[i][j]==null || gameBoard.board[i][j].pieceType!=pieceType){
                    isAntiDiagonalMatch=false;
                }
            }

            return isRowMatch || isColumnMatch || isDiagonalMatch || isAntiDiagonalMatch;





    }
}
