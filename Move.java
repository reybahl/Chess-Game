import org.code.playground.*;
import java.io.FileNotFoundException;
public class Move extends ClickableImage {
  public Move (int xPos, int yPos) throws FileNotFoundException{
    super("Highlight.png", xPos * 50, yPos * 50, 50, 50);
  }
  public void onClick() {
    //Set new location for piece, process taking if applicable, move taken pieces to takenPieces array, progress turn;
  }
}
