import java.io.FileNotFoundException;
import org.code.playground.*;

public class ValidMoveButton extends ClickableImage {
  public ValidMoveButton (String filename, int xPos, int yPos, int width, int height) throws FileNotFoundException {
    super(filename, xPos, yPos, width, height);
  } 

  public void onClick() {;
    // GameControl.playSoundEffect("oxp.wav");
    // GameControl.startPlayground();
    // GameDisplay.displayGame();					// displays the game components on the board
  }
}
