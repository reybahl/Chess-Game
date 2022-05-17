import org.code.playground.*;
import java.io.FileNotFoundException;
public class Move extends ClickableImage {
  private int row;
  private int col;
  public Move (int row, int col) throws FileNotFoundException{
    super("Highlight.png", 10 + col * 50, 10 + row * 50, 30, 30);
    this.row = row;
    this.col = col;
  }
  public void onClick() {
    ChessBoard.clearPossibleMoves();
    try {
      if (ChessBoard.getPieceAt(this.row, this.col) != null) {
        ChessBoard.getPieceAt(this.row, this.col).kill(ChessBoard.getClicked());
      }
      ChessBoard.movePiece(this.row, this.col, null);
      if (ChessBoard.getClicked().getType().equals("Pawn") && (this.row == 0 || this.row == 7)) {
        ChessBoard.getClicked().setType("Queen");
      }
      GameControl.switchTurn();
      GameControl.checkForEnd();
    } catch (Exception e) {
      System.out.println("Exception in method onClick() of Move " + e);
    }
  }
}
