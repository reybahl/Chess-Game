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
  public void onClick() {
    try {
      ChessBoard.showPossibleMoves(ChessBoard.getPieceAt(row, col));
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
