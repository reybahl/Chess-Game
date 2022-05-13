import java.io.FileNotFoundException;
public class CheckLogic {
  public static boolean inCheck (Piece[][] piecesArray, String color) {
    Piece king = null;
    for (Piece thisPiece: piecesArray[(color == "White" ? 0 : 1)]) {
      if (thisPiece.getType().equals("King")) {
        king = thisPiece;
      }
    }
    for (Piece thisPiece: piecesArray[(color.equals("White") ? 1 : 0)]) {
      if (thisPiece.canMove(king.getRow(), king.getCol())) {
        king = thisPiece;
        return true;
      }
    }
    return false;
  }
  public static boolean kingWillBeInCheck (String color, int row, int col) {
    for (Piece thisPiece: ChessBoard.getPiecesArray()[(color.equals("White") ? 1 : 0)]) {
      if (thisPiece.canMove(row, col)) {
        return true;
      }
    }
    return false;
  }
  public static boolean pieceCanMoveAway (Piece piece, String color, int row, int col) {
    Piece[][] piecesCopy = new Piece[2][16];
    try {
      for (int j = 0; j < 2; j++) {
        for(int i = 0; i < 16; i++) {
          Piece currentPiece = ChessBoard.getPiecesArray()[j][i];
          piecesCopy[j][i] = new Piece (currentPiece.getType(), currentPiece.getColor(), currentPiece.getRow(), currentPiece.getCol());
          if (currentPiece == piece) {
            piecesCopy[j][i].setRow(row);
            piecesCopy[j][i].setCol(col);
          }
        }
      }
      return !inCheck(piecesCopy, color);
    } catch (Exception e) {
      System.out.println(e);
    }
    return false;
  }
}
