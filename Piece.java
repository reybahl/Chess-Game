import java.io.FileNotFoundException;
public class Piece {
  private String type;
  private String color;
  private int row;
  private int col;
  private PieceButton button;
  private int xPos;
  private int yPos;
  private int timesMoved;
  public Piece (String type, String color, int r, int c) throws FileNotFoundException {
    this.row = r;
    this.col = c;
    this.color = color;
    this.type = type;
    this.timesMoved = 0;
    if (type != null) {
      String filename = color + "-" +  type + ".png";
      this.xPos = c*50;
      this.yPos = r*50;
      this.button = new PieceButton(filename, c*50, r*50, 50, 50, r, c);
    }
  }
  public int getRow() {
    return this.row;
  }
  public int getCol() {
    return this.col;
  }
  public String getColor() {
    return this.color;
  }
  public String getType() {
    return this.type;
  }
  public PieceButton getButton() {
    return this.button;
  }
  public int getTimesMoved() {
    return this.timesMoved;
  }
  public void setRow (int r) {
    this.row = r;
  }
  public void setCol (int c) {
    this.col = c;
  }
  public void setType (String type) throws FileNotFoundException {
    this.type = type;
    if (this.button != null) {
      this.button.setFilename(this.color + "-" +  type + ".png");
    }
  }
  public void setImage (String filename) throws FileNotFoundException {
    if (this.button != null) {
      this.button.setFilename(filename);
    }
  }
  public void kill (Piece killedBy) throws FileNotFoundException {
    this.type = null;
    killedBy.setRow(this.row);
    killedBy.setCol(this.col);
    this.row = -10000;
    this.col = -10000;
    if (this.button != null) {
      ChessBoard.removePiece(this, killedBy);
    }
  }
  public boolean kingCanMove (boolean real, int r, int c) {
    if (this.timesMoved == 0 && this.row == r && Math.abs(this.col - c) == 2) {
    	return (this.col > c && (real ? ChessBoard.isEmpty(r, this.col-1) : CheckLogic.isEmpty(r, this.col-1)) && (real ? ChessBoard.isEmpty(r, this.col-2) : CheckLogic.isEmpty(r, this.col-2)) && (real ? ChessBoard.isEmpty(r, this.col-3) : CheckLogic.isEmpty(r, this.col-3)) && (real ? CheckLogic.dangerResolved(this, r, this.col-1) && CheckLogic.dangerResolved(this, r, this.col-2) : true) && (real ? ChessBoard.getPieceAt(r, 0).getTimesMoved() == 0 : CheckLogic.getPieceAt(r, 0).getTimesMoved() == 0)) || (this.col < c && (real ? ChessBoard.isEmpty(r, this.col+1) : CheckLogic.isEmpty(r, this.col+1)) && (real ? ChessBoard.isEmpty(r, this.col+2) : CheckLogic.isEmpty(r, this.col+2)) && (real ? CheckLogic.dangerResolved(this, r, this.col-1) && CheckLogic.dangerResolved(this, r, this.col-2) : true) && (real ? ChessBoard.getPieceAt(r, 7).getTimesMoved() == 0 : CheckLogic.getPieceAt(r, 7).getTimesMoved() == 0));
    } else {
      return (real ? (Math.abs(this.row - r) <= 1 && Math.abs(this.col - c) <= 1 && ChessBoard.isFree(r, c, this.color)) : (Math.abs(this.row - r) <= 1 && Math.abs(this.col - c) <= 1 && CheckLogic.isFree(r, c, this.color)));
    }
  }
  private boolean queenCanMove (boolean real, int row, int col) {
    return bishopCanMove(real, row, col) || rookCanMove(real, row, col);
  }
  private boolean rookCanMove (boolean real, int r, int c) {
    if (r != this.row && c != this.col) {
      return false;
    }
    int currentRow = this.row;
    int currentCol = this.col;
    while (currentRow != r || currentCol != c) {
      if (currentRow < r) currentRow++;
      else if (currentRow > r) currentRow--;
      else if (currentCol < c) currentCol++;
      else if (currentCol > c) currentCol--;
      if ((currentRow != r || currentCol != c ) && (real ? (!ChessBoard.isEmpty(currentRow, currentCol)) : (!CheckLogic.isEmpty(currentRow, currentCol)))) {
        return false;
      }
    }
    return (real ? ChessBoard.isFree(r, c, this.color) : CheckLogic.isFree(r, c, this.color));
  }
  private boolean bishopCanMove (boolean real, int r, int c) {
    if (!(r - c == this.row - this.col || r + c == this.row + this.col)) {
      return false;
    }
    else {
      int currentRow = this.row;
      int currentCol = this.col;
      while (currentRow != r && currentCol != c) {
        if (currentRow < r) currentRow++;
        else currentRow--;
        if (currentCol < c) currentCol++;
        else currentCol--;
        if ((currentRow != r && currentCol != c) && (real ? (!ChessBoard.isEmpty(currentRow, currentCol)) : (!CheckLogic.isEmpty(currentRow, currentCol)))) {
          return false;
        }
      }
      return (real ? ChessBoard.isFree(r, c, this.color) : CheckLogic.isFree(r, c, this.color));
    }
  }
  private boolean knightCanMove (boolean real, int row, int col) {
    return ((Math.abs(this.row - row) == 2 && Math.abs(this.col - col) == 1) || (Math.abs(this.col - col) == 2 && Math.abs(this.row - row) == 1)) && (real ? ChessBoard.isFree(row, col, this.color) : CheckLogic.isFree(row, col, this.color));
  }
  private boolean pawnCanMove (boolean real, int row, int col) {
    int add = this.color.equals("White") ? 1 : -1;
    return (col == this.col && this.row + add == row && (real ? ChessBoard.isEmpty(row, col) : CheckLogic.isEmpty(row, col))) || (this.row + add*2 == row && this.col == col && (this.row == 1 || this.row == 6) && (real ? ChessBoard.isEmpty(this.row + add, col) : CheckLogic.isEmpty(this.row + add, col)) && (real ? ChessBoard.isEmpty(row, col) : CheckLogic.isEmpty(row, col)));
  }
  public boolean pawnCanTake (boolean real, int row, int col) {
    int add = this.color.equals("White") ? 1 : -1;
    return  (this.row + add == row && (this.col+1 == col || this.col-1 == col)) && !(real ? ChessBoard.isEmpty(row, col) : CheckLogic.isEmpty(row, col)) && (real ? ChessBoard.isFree(row, col, this.color) : CheckLogic.isFree(row, col, this.color));
  }
  public boolean canMove (boolean real, int r, int c) {
    if (this.type == null || (this.row == r && this.col == c)) {
      return false;
    }
    switch (this.type) {
      case "King": return kingCanMove(real, r, c);
      case "Queen": return queenCanMove(real, r, c);
      case "Bishop": return bishopCanMove(real, r, c);
      case "Rook": return rookCanMove(real, r, c);
      case "Knight": return knightCanMove(real, r, c);
      case "Pawn": return pawnCanMove(real, r, c) || pawnCanTake(real, r, c);
      default: return false;
    }
  }
  public boolean validMove (int r, int c) {
    return canMove(true, r, c) && CheckLogic.dangerResolved(this, r, c);
  }
  public void move(boolean real, int r, int c) throws FileNotFoundException {
    if (real && this.type.equals("King") && Math.abs(this.col - c) == 2) {
    	ChessBoard.getPieceAt(r, this.col > c ? 0 : 7).move(true, r, this.col > c ? 3 : 5);
    }
    this.timesMoved++;
    this.row = r;
    this.col = c;
    if (real && this.button != null) {
      this.xPos = c*50;
      this.yPos = r*50;
      this.button.setLocation(this.xPos, this.yPos, this.row, this.col);
    }
  }
}
