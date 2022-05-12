import java.io.FileNotFoundException;
import org.code.playground.*;
public class PieceButton extends ClickableImage {
  int row;
  int col;
  public PieceButton (String filename, int xPos, int yPos, int width, int height, int row, int col) throws FileNotFoundException {
    super(filename, xPos, yPos, width, height);
    this.row = row;
    this.col = col;
  }
  public void setLocation (int xPos, int yPos, int row, int col) {
    super.setY(yPos);
    super.setX(xPos);
    this.row = row;
    this.col = col;
  }
  public void onClick() {
    try {
      Piece current = ChessBoard.getPieceAt(row, col);
      
      if (current.getColor().equals(GameControl.getTurn())) {
        ChessBoard.showPossibleMoves(current);
        ChessBoard.setClicked(current);
        GameControl.switchTurn();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
