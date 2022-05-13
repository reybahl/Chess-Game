import org.code.playground.*;
import java.io.FileNotFoundException;
public class Move extends ClickableImage {
  private int row;
  private int col;
  public Move (int row, int col) throws FileNotFoundException{
    super("Highlight.png", col * 50, row * 50, 50, 50);
    this.row = row;
    this.col = col;
  }
  public void onClick() {
    ChessBoard.clearPossibleMoves();
    try {
      Piece there = ChessBoard.getPieceAt(this.row, this.col);
      if (there != null) {
        there.kill(ChessBoard.getPiecesArray(), ChessBoard.getClicked());
      }
      ChessBoard.movePiece(ChessBoard.getPiecesArray(), this.row, this.col, null);
      Piece current = ChessBoard.getClicked();
      if (current.getType().equals("Pawn") && (this.row == 0 || this.row == 7)) {
        current.setType("Queen");
      }
      GameControl.switchTurn();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
