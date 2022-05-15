import java.io.FileNotFoundException;
import org.code.playground.*;
public class PieceButton extends ClickableImage {
  int row;
  int col;
  public PieceButton (String filename, int xPos, int yPos, int width, int height, int r, int c) throws FileNotFoundException {
    super(filename, xPos, yPos, width, height);
    this.row = r;
    this.col = c;
  }
  public void setLocation (int xPos, int yPos, int r, int c) {
    super.setY(yPos);
    super.setX(xPos);
    this.row = r;
    this.col = c;
  }
  public void onClick() {
    try {
      if (ChessBoard.getPieceAt(row, col).getColor().equals(GameControl.getTurn())) {
        ChessBoard.setClicked(ChessBoard.getPieceAt(row, col));
        ChessBoard.showPossibleMoves(ChessBoard.getPieceAt(row, col));
      }
    } catch (Exception e) {
      System.out.println("Exception in method onClick() of PieceButton " + e);
    }
  }
}
