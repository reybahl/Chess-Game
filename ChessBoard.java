import org.code.playground.*;
import java.io.FileNotFoundException;

public class ChessBoard {
  private static Piece[][] pieces = new Piece[8][8];
  private static Piece[][] takenPieces = new Piece[2][15];
  private static ValidMove[][] validMove = new ValidMove[8][8];

  private static Board myChessBoard = Playground.board;		// the board to display the game
  public ChessBoard (String filename) throws FileNotFoundException, PlaygroundException {
    myChessBoard.setBackgroundImage(filename);
    
    String[] pieceTypes = {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};

    for (int i=0; i<pieceTypes.length; i++) {
      pieces[0][i] = new Piece(pieceTypes[i], "White", 0, i);
      pieces[7][i] = new Piece(pieceTypes[i], "Black", 7, i);
    }
    
    for (int col=0; col<pieces[0].length; col++) {
      pieces[1][col] = new Piece("Pawn", "White", 1, col);
      pieces[6][col] = new Piece("Pawn", "Black", 6, col);
    }
    showChessBoard();
    myChessBoard.start();
    
  }

  public static boolean free (int row, int col, String color) {
    return (pieces[row][col] == null || !pieces[row][col].getColor().equals(color));
  }

  public static boolean empty (int row, int col) {
    return (pieces[row][col] == null);
  }

  public static Piece getPiece (int row, int col) {
    return pieces[row][col];
  }

  public static void setPossibleMoves (int row, int col) {
    
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        validMove[r][c] = new ValidMove(pieces[row][col].canMove(r, c), r, c);
      }
    }
  }
  
  public static void showPossibleMoves (int row, int col) {
    setPossibleMoves(row, col);
    for (int r=0; r<8; r++) {
      for (int c=0; c<8; c++) {
        if (validMove[r][c] != null && validMove[r][c].getValid()) {
          myChessBoard.addTextItem(validMove[r][c].getButton());
        }
      }
    }
  }

  public static void showChessBoard () throws FileNotFoundException {
    for (Piece[] arr : pieces) {
      for (Piece piece : arr) {
        if (piece != null) {
          myChessBoard.addClickableImage(piece.getButton());
        }
      }
    }
  }
}
