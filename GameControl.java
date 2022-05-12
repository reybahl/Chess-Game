import java.io.FileNotFoundException;
import org.code.playground.*;
import org.code.media.*;

public class GameControl {
  private static ChessBoard board;
  private static String currentTurn;

  public static void start () throws FileNotFoundException, PlaygroundException {
    currentTurn = "Black";
    board = new ChessBoard();
  }
 
  public static String getTurn() {
    return currentTurn;
  }
  
  public static void switchTurn() {
    if (currentTurn.equals("White")) currentTurn = "Black";
    else currentTurn = "White";
  }
}
