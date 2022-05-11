import org.code.playground.*;
import java.io.FileNotFoundException;
public class ChessBoard {
  private static ClickableImage[][] squares = new ClickableImage[8][8];
  private static Piece[][] pieces = new Piece[2][16];
  private static Piece[][] takenPieces = new Piece[2][15];
  public static Board myChessBoard = Playground.board;
  public ChessBoard () throws FileNotFoundException, PlaygroundException {
    String[] pieceTypes = {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};
    for (int i=0; i < 8; i++) {
      pieces[0][i] = new Piece(pieceTypes[i], "White", 0, i);
      pieces[0][8+i] = new Piece("Pawn", "White", 1, i);
      pieces[1][i] = new Piece(pieceTypes[i], "Black", 7, i);
      pieces[1][8+i] = new Piece("Pawn", "Black", 6, i);
    }
    showPlayableBoard();
    myChessBoard.start();
  }
  public static Piece getPieceAt(int row, int col) {
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 16; j++) {        
        if (pieces[i][j].getRow() == row && pieces[i][j].getCol() == col) {
          return pieces[i][j];
        }
      }
    }
    return null;
  }
  public static boolean empty (int row, int col) {
    return getPieceAt(row, col) == null;
  }
  public static boolean free (int row, int col, String color) {
    return empty(row, col) || !getPieceAt(row, col).getColor().equals(color);
  }
  public static void showPossibleMoves (Piece piece) throws FileNotFoundException {
    for (int r=0; r<8; r++) {
      for (int c=0; c<8; c++) {
        if (piece.canMove(r, c)) {
          myChessBoard.addClickableImage(new Move(r, c));
        }
      }
    }
  }
  public static void showPlayableBoard () throws FileNotFoundException {
    myChessBoard.setBackgroundImage("chess-board.png");
    for (Piece[] arr : pieces) {
      for (Piece piece : arr) {
        if (piece != null) {
          myChessBoard.addClickableImage(piece.getButton());
        }
      }
    }
  }
}
