import java.io.FileNotFoundException;
public class Piece {
  private String type;
  private boolean isOnChessBoard;
  private String color;
  private int row;
  private int col;
  private PieceButton button;
  private int xPos;
  private int yPos;
  private boolean taken;
  public Piece (String type, String color, int r, int c) throws FileNotFoundException {
    this.row = r;
    this.col = c;
    this.type = type;
    this.color = color;
    this.isOnChessBoard = true;
    String filename = color + "-" +  type + ".png";
    this.xPos = c*50;
    this.yPos = r*50;
    this.button = new PieceButton(filename, c*50, r*50, 50, 50, r, c);
    this.taken = false;
  }
  public int getRow() {
    return this.row;
  }
  public int getCol() {
    return this.col;
  }
  public void setRow(int r) {
    this.row = r;
  }
  public void setCol(int c) {
    this.col = c;
  }
  public String getColor() {
    return this.color;
  }
  public String getType () {
    return this.type;
  }
  public PieceButton getButton () {
    return this.button;
  }
  public Piece getPiece() {
    return this;
  }
  public boolean isAlive () {
    return !this.taken;
  }
  public void setType(String type) throws FileNotFoundException {
    this.type = type;
    this.button.setFilename(this.color + "-" +  type + ".png");
  }
  public void kill(Piece[][] pieces1, Piece killedBy) {
    this.taken = true;
    try {
      ChessBoard.removePiece(pieces1, this, killedBy);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  private boolean knightCanMove (int row, int col) {
    boolean canMoveLogic = false;
    if (Math.abs(this.row - row) == 2 && Math.abs(this.col - col) == 1) {
      canMoveLogic = true;
    } else if (Math.abs(this.col - col) == 2 && Math.abs(this.row - row) == 1) {
      canMoveLogic = true;
    }
    return ChessBoard.free(row, col, this.color) && canMoveLogic;
  }
  private boolean bishopCanMove (int row, int col) {
    boolean diagonal = (row-col == this.row - this.col || row+col == this.row + this.col);
    if (!diagonal) {
      return false;
    }
    else {
      int currentRow = this.row;
      int currentCol = this.col;
      while (currentRow != row && currentCol != col) {
        if (currentRow < row) currentRow++;
        else currentRow--;
        if (currentCol < col) currentCol++;
        else currentCol--;
        if ((currentRow != row && currentCol != col) && !ChessBoard.empty(currentRow, currentCol)) {
          return false;
        }
      }
      return ChessBoard.free(row, col, this.color);
    }
  }
  private boolean rookCanMove(int r, int c) {
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
      if ((currentRow != r || currentCol != c ) && !ChessBoard.empty(currentRow, currentCol)) {
        return false;
      }
    }
    return ChessBoard.free(r, c, this.color);
  }
  private boolean pawnCanMove (int row, int col) {
    int add = -1;
    if (this.color.equals("White")) add = 1;
    if (this.row + add == row && ChessBoard.empty(row, col) && col == this.col)
        return true;
    if (this.row + add*2 == row && this.col == col && (this.row == 1 || this.row == 6) && ChessBoard.empty(row, col))
        return true;
    else if (this.row + add == row && (this.col+1 == col || this.col-1 == col)) {
        return !ChessBoard.empty(row, col) && !(ChessBoard.getPieceAt(row, col).getColor().equals(this.color));
    }
    return false;
  }
  private boolean kingCanMove (int r, int c) {
    if (Math.abs(this.row - r) <= 1 && Math.abs(this.col - c) <= 1 && !CheckLogic.squareUnderAttack(this.color, r, c)) {
      return ChessBoard.free(r, c, this.color);
    }
    return false;
  }
  private boolean queenCanMove (int row, int col) {
    if (bishopCanMove(row, col) || rookCanMove(row, col)) {
      return true;
    }
    return false;
  }
  public boolean canMove (int r, int c) {
    if (this.type.equals("King")) {
      return kingCanMove(r, c);
    } else if (this.type.equals("Queen")) {
      return queenCanMove(r, c);
    } else if (this.type.equals("Bishop")) {
      return bishopCanMove(r, c);
    } else if (this.type.equals("Rook")) {
      return rookCanMove(r, c);
    } else if (this.type.equals("Knight")) {
      return knightCanMove(r, c);
    } else {
      return pawnCanMove(r, c);
    }
  }
  public boolean validMove (int r, int c) {
    return canMove(r, c) && CheckLogic.noCheck(this, this.color, r, c);
  }
  public void move(Piece[][] pieces1, int r, int c) throws FileNotFoundException {
    if (pieces1 == ChessBoard.getPiecesArray()) {
      this.xPos = c*50;
      this.yPos = r*50;
      this.row = r;
      this.col = c;
      this.button.setLocation(this.xPos, this.yPos, this.row, this.col);
      ChessBoard.showPlayableBoard();
    } else {
      CheckLogic.getPieceAt(this.row, this.col).setRow(r);
      CheckLogic.getPieceAt(this.row, this.col).setCol(c);
    }
  }
}
