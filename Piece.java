import java.io.FileNotFoundException;
public class Piece {
  private String type;
  private boolean isOnChessBoard;
  private String color;
  private int row;
  private int col;
  private PieceButton button;
  private int xPos; // in pixel coordinates
  private int yPos;
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
  public String getType () {
    return this.type;
  }
  public PieceButton getButton () {
    return this.button;
  }
  public Piece getPiece() {
    return this;
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
        if (!ChessBoard.empty(currentRow, currentCol)) {
          return false;
        }
        if (currentRow < row) currentRow++;
        else currentRow--;
        if (currentCol < col) currentCol++;
        else currentCol--;
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
      if (!ChessBoard.empty(currentRow, currentCol)) {
        return false;
      }
      if (currentRow < r) currentRow++;
      else if (currentRow > r) currentRow--;
      else if (currentCol < c) currentCol++;
      else if (currentCol > c) currentCol--;
    }
    return ChessBoard.free(r, c, this.color);
  }
  private boolean pawnCanMove (int row, int col) {
    int add = -1;
    if (this.color.equals("White")) add = 1;
    
    if (this.row + add == row && ChessBoard.empty(row, col) && col == this.col)
        return true;

    else if (this.row + add == row && (this.col+1 == col || this.col-1 == col)) {
        return !ChessBoard.empty(row, col) && !(ChessBoard.getPieceAt(row, col).getColor().equals(this.color));
    }
    return false;
  }
  private boolean kingCanMove (int row, int col) {
    if (Math.abs(this.row - row) <= 1 && Math.abs(this.col - col) <= 1) {
      return ChessBoard.free(row, col, this.color);
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
    if (this.type.equals("King")) {
      return kingCanMove(row, col);
    } else if (this.type.equals("Queen")) {
      return queenCanMove(row, col);
    } else if (this.type.equals("Bishop")) {
      return bishopCanMove(row, col);
    } else if (this.type.equals("Rook")) {
      return rookCanMove(row, col);
    } else if (this.type.equals("Knight")) {
      return knightCanMove(row, col);
    } else {
      return pawnCanMove(row, col);
    }
  }

  public void move(int row, int col) throws FileNotFoundException {
    this.xPos = col*50;
    this.yPos = row*50;
    
    this.row = row;
    this.col = col;
    this.button.setLocation(this.xPos, this.yPos, this.row, this.col);
    ChessBoard.showPlayableBoard();
  }
}
