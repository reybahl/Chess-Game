import org.code.playground.*;

public class Board {
  private static Piece[][] pieces = new Piece[8][8];
  private static Piece[][] takenPieces = new Piece[2][15];
  private static boolean[][] validMove = new boolean[8][8];
  public Board () {
    String[] pieceTypes = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};

    for (int i=0; i<pieceTypes.length; i++) {
      pieces[0][i] = new Piece(pieceTypes[i], "white", 0, i);
      pieces[7][i] = new Piece(pieceTypes[i], "black", 7, i);
    }
    
    for (int col=0; col<pieces[0].length; col++) {
      pieces[1][col] = new Piece("pawn", "white", 1, col);
      pieces[6][col] = new Piece("pawn", "black", 6, col);
    }
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

  public static boolean[][] getPossibleMoves (int row, int col) {
    
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        validMove[r][c] = pieces[row][col].canMove(r, c);
      }
    }
    return validMove;
  }
  
  public static showPossibleMoves (int row, int col) {
    boolean[][] validMoves = getPossibleMoves(row, col);
    Board.showBoard(validMoves);
  }

  public static showBoard ()
}