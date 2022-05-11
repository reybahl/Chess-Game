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
    System.out.println("clicked!");
    ChessBoard.showPossibleMoves(row, col);				// displays the game components on the board
  }
  // public onClick () {;}
}
