public class Piece {
  private String type; // type of piece
  private boolean isOnBoard;
  private String color;
  private int row;
  private int col;
  public Piece (String type, String color, int row, int col) {
    this.type = type;
    this.color = color;
    isOnBoard = true;
  }
  
  public String getColor() {
    return this.color;
  }
  
  private boolean knightCanMove (int row, int col) {
    boolean canMoveLogic = false;
    if (Math.abs(this.row - row) == 2 && Math.abs(this.col - col) == 1)
        canMoveLogic = true;
    else if (Math.abs(this.col - col) == 2 && Math.abs(this.row - row) == 1)
        canMoveLogic = true;
    
    return Board.free(row, col, this.color) && canMoveLogic;
  }

  private boolean bishopCanMove (int row, int col) {
    boolean diagonal = (row-col == this.row - this.col || row+col == this.row + this.col);
    if (!diagonal)
        return false;
    else {
      int currentRow = this.row;
      int currentCol = this.col;
      while (currentRow != row && currentCol != col) {
        if (!Board.empty(currentRow, currentCol))
            return false;
        if (currentRow < row) currentRow++;
        else currentRow--;

        if (currentCol < col) currentCol++;
        else currentCol--;
      }
      
      return Board.free(row, col, this.color);
    }
  }

  private boolean rookCanMove(int row, int col) {
    if (row != this.row && col != this.col)
        return false;
    
    int currentRow = this.row;
    int currentCol = this.col;
    while (currentRow != row || currentCol != col) {
      if (!Board.empty(currentRow, currentCol))
            return false;
      
      if (currentRow < row) currentRow++;
      else if (currentRow > row) currentRow--;
      else if (currentCol < col) currentCol++;
      else if (currentCol > col) currentCol--;
    }
    return Board.free(row, col, this.color);
  }

  private boolean pawnCanMove (int row, int col) {
    // move forward
    int add = 1;
    if (this.color.equals("white")) add = -1;
    
    if (this.row + add == row && Board.empty(row, col))
        return true;

    else if (this.row + add == row && (this.col+1 == col || this.col-1 == col)) {
        return !Board.empty(row, col) && !(Board.getPiece(row, col).getColor().equals(this.color));
    }
  }

  private boolean kingCanMove (int row, int col) {
    if (Math.abs(this.row - row) <= 1 && Math.abs(this.col - col) <= 1) {
      return Board.free(row, col, this.color);
    }
    return false;
  }

  private boolean queenCanMove (int row, int col) {
    if (bishopCanMove(row, col) || rookCanMove(row, col)) {
      return true;
    }
    return false;
  }

  public boolean canMove (int row, int col) {
    if (this.type.equals("king")) {
      return kingCanMove(row, col);
    } else if (this.type.equals("queen")) {
      return queenCanMove(row, col);
    } else if (this.type.equals("bishop")) {
      return bishopCanMove(row, col);
    } else if (this.type.equals("rook")) {
      return rookCanMove(row, col);
    } else if (this.type.equals("knight")) {
      return knightCanMove(row, col);
    } else if (this.type.equals("pawn")) {
      return pawnCanMove(row, col);
    }
  }
}
