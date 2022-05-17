import java.io.FileNotFoundException;
public class CheckLogic {
  public static Piece[][] piecesCopy = new Piece[2][16];
  private static void copyPiecesArray() throws FileNotFoundException {
    for (int j = 0; j < 2; j++) {
      for(int i = 0; i < 16; i++) {
        piecesCopy[j][i] = new Piece (ChessBoard.getPiecesArray()[j][i].getType(), ChessBoard.getPiecesArray()[j][i].getColor(), ChessBoard.getPiecesArray()[j][i].getRow(), ChessBoard.getPiecesArray()[j][i].getCol());
      }
    }
  }
  public static Piece[][] getPiecesArray() {
    return piecesCopy;
  }
  public static Piece getPieceAt(int row, int col) {
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 16; j++) {
        if (piecesCopy[i][j].getRow() == row && piecesCopy[i][j].getCol() == col) {
          return piecesCopy[i][j];
        }
      }
    }
    return null;
  }
  public static boolean isEmpty (int row, int col) {
    return getPieceAt(row, col) == null;
  }
  public static boolean isFree (int row, int col, String color) {
    return isEmpty(row, col) || !getPieceAt(row, col).getColor().equals(color);
  }
  public static boolean inCheck (Piece[][] piecesArray, String color) {
    for (Piece aPiece : piecesArray[(color.equals("White") ? 1 : 0)]) {
      if (aPiece.canMove(false, piecesArray[(color.equals("White") ? 0 : 1)][4].getRow(), piecesArray[(color.equals("White") ? 0 : 1)][4].getCol())) {
        return true;
      }
    }
    return false;
  }
  public static boolean dangerResolved (Piece piece, int row, int col) {
    try {
      copyPiecesArray();
      if (CheckLogic.getPieceAt(row, col) != null) {
        CheckLogic.getPieceAt(row, col).kill(CheckLogic.getPieceAt(piece.getRow(), piece.getCol()));
      } else {
        CheckLogic.getPieceAt(piece.getRow(), piece.getCol()).move(false, row, col);
      }
      return !inCheck(piecesCopy, piece.getColor());
    } catch (Exception e) {
      System.out.println("Exception in method dangerResolved() of CheckLogic: " + e);
    }
    return false;
  }
}
