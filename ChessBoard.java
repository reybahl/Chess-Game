import org.code.playground.*;
import java.io.FileNotFoundException;
public class ChessBoard {
  private static ClickableImage[][] squares = new ClickableImage[8][8];
  private static Piece[][] pieces = new Piece[2][16];
  private static Piece[][] takenPieces = new Piece[2][15];
  public static Board myChessBoard = Playground.board;
  private static Move[][] moves = new Move[8][8];
  private static Piece clickedPiece;
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
  public static Piece[][] getPiecesArray() {
    return pieces;
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
  public static void setClicked(Piece piece) {
    clickedPiece = piece;
  }
  public static Piece getClicked() {
    return clickedPiece;
  }
  public static void movePiece (Piece[][] pieces1, int row, int col, Piece thisPiece) throws FileNotFoundException {
    if (pieces1 == pieces) {
      clickedPiece.move(pieces, row, col);
      showPlayableBoard();
    } else {
      thisPiece.move(pieces1, row, col);
    }
  }
  public static void setPieceAt(Piece[][] pieces1, int row, int col, Piece piece) {
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 16; j++) {
        if (pieces1 == pieces) {
          if (pieces[i][j].getRow() == row && pieces[i][j].getCol() == col) {
            pieces[i][j] = piece;
          } else {
            if (pieces1[i][j].getRow() == row && pieces1[i][j].getCol() == col) {
              pieces1[i][j] = piece;
            }
          }
        }
      }
    }
  }
  public static boolean empty (int row, int col) {
    return getPieceAt(row, col) == null;
  }
  public static boolean free (int row, int col, String color) {
    return empty(row, col) || !getPieceAt(row, col).getColor().equals(color);
  }
  public static void getPossibleMoves(Piece piece) throws FileNotFoundException {
    for (int r=0; r<8; r++) {
      for (int c=0; c<8; c++) {
        if (piece.validMove(r, c)) {
          moves[r][c] = new Move(r, c);
        }
      }
    }
  }
  public static void showPossibleMoves (Piece piece) throws FileNotFoundException {
    clearPossibleMoves();
    getPossibleMoves(piece);
    for (int r=0; r<8; r++) {
      for (int c=0; c<8; c++) {
        if (moves[r][c] != null) {
          myChessBoard.addClickableImage(moves[r][c]);
        }
      }
    }
  }
  public static void clearPossibleMoves () {
    for (int r=0; r<8; r++) {
      for (int c=0; c<8; c++) {
        if (moves[r][c] != null) {
          myChessBoard.removeClickableImage(moves[r][c]);
          moves[r][c] = null;
        }
      }
    }
  }
  public static void removePiece (Piece[][] pieces1, Piece piece, Piece killedBy) {
    if (pieces1 == pieces) {
      setPieceAt(pieces, piece.getRow(), piece.getCol(), killedBy);
      myChessBoard.removeClickableImage(piece.getButton());
    } else {
      setPieceAt(pieces1, piece.getRow(), piece.getCol(), killedBy);
    }
  }
  public static void showPlayableBoard () throws FileNotFoundException {
    myChessBoard.setBackgroundImage("Chessboard.png");
    for (Piece[] arr : pieces) {
      for (Piece piece : arr) {
        if (piece != null && piece.isAlive()) {
          myChessBoard.addClickableImage(piece.getButton());
        }
      }
    }
  }
}
