public class CheckLogic {
  public static Piece[][] piecesCopy = new Piece[2][16];
  public static Piece[][] getPiecesCopy() {
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
  public static boolean inCheck (Piece[][] piecesArray, String color) {
    for (Piece aPiece : piecesArray[(color.equals("White") ? 1 : 0)]) {
      if (aPiece.canMove(piecesArray[(color.equals("White") ? 0 : 1)][4].getRow(), piecesArray[(color.equals("White") ? 0 : 1)][4].getCol())) {
        return true;
      }
    }
    return false;
  }
  public static boolean squareUnderAttack (String color, int row, int col) {
    for (Piece thisPiece: ChessBoard.getPiecesArray()[(color.equals("White") ? 1 : 0)]) {
      if (thisPiece.canMove(row, col)) {
        return true;
      }
    }
    return false;
  }
  public static boolean noCheck (Piece piece, String color, int row, int col) {
    try {
      for (int j = 0; j < 2; j++) {
        for(int i = 0; i < 16; i++) {
          Piece currentPiece = ChessBoard.getPiecesArray()[j][i];
          piecesCopy[j][i] = new Piece (currentPiece.getType(), currentPiece.getColor(), currentPiece.getRow(), currentPiece.getCol());
        }
      }
      Piece movingPiece = CheckLogic.getPieceAt(piece.getRow(), piece.getCol());
      movingPiece.setRow(row);
      movingPiece.setCol(col);
      Piece there = CheckLogic.getPieceAt(row, col);
      if (there != null) {
        there.kill(piecesCopy, piece);
      }
      ChessBoard.movePiece(piecesCopy, row, col, movingPiece);
      return !inCheck(piecesCopy, color);
    } catch (Exception e) {
      System.out.println(e);
    }
    return false;
  }
}
