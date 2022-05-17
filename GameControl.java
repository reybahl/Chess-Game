import java.io.FileNotFoundException;
import org.code.playground.*;
import org.code.media.*;
public class GameControl {
  private static ChessBoard board;
  private static String currentTurn;
  public static void start () throws FileNotFoundException, PlaygroundException {
    currentTurn = "White";
    board = new ChessBoard();
  }
  public static String getTurn() {
    return currentTurn;
  }
  public static void switchTurn() {
    currentTurn = (currentTurn.equals("White") ? "Black" : "White");
  }
  public static void checkForEnd() {
    ChessBoard.setPlayerHasMove(false);
    try {
      for (Piece thisPiece : ChessBoard.getPiecesArray()[currentTurn.equals("White") ? 0 : 1]) {
        ChessBoard.getPossibleMoves(thisPiece);
      }
      if (CheckLogic.inCheck(ChessBoard.getPiecesArray(), currentTurn) && !ChessBoard.getPlayerHasMove()) {
        System.out.println("Checkmate! " + (currentTurn == "White" ? "Black" : "White") + " wins!");
        ChessBoard.showOverlay(currentTurn == "White" ? "Black" : "White");
        ChessBoard.myChessBoard.end("win.wav");
      } else if (!ChessBoard.getPlayerHasMove()) {
        System.out.println("Stalemate!");
        ChessBoard.showOverlay("Black");
        ChessBoard.myChessBoard.end("move_sound.wav");
      }
    } catch (Exception e) {
        System.out.println("Exception in method checkForEnd() checkmate / stalemate check: " + e);;
    }
    boolean draw = true;
    int bishopCount = 0;
    int knightCount = 0;
    for (Piece[] x : ChessBoard.getPiecesArray()) {
      for (Piece y : x) {
        if (y.getType() != null && draw) {
          if (y.getType().equals("Queen") || y.getType().equals("Rook") || y.getType().equals("Pawn")) {
            draw = false;
          } else if (y.getType().equals("Bishop")) {
            bishopCount++;
          } else if (y.getType().equals("Knight")) {
            knightCount++;
          }
        }
      }
      if (bishopCount > 1  || (bishopCount == 1 && knightCount >= 1)) {
        draw = false;
      }
    }
    if (draw) {
      try {
        System.out.println("Draw!");
        ChessBoard.showOverlay("Draw");
        ChessBoard.myChessBoard.end("move_sound.wav");
      } catch (Exception e) {
        System.out.println("Exception in method checkForEnd() draw check: " + e);
      }
    }
  }
}
