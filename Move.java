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
      ChessBoard.myChessBoard.playSound("move_sound.wav");
      if (ChessBoard.getPieceAt(this.row, this.col) != null) {
        ChessBoard.getPieceAt(this.row, this.col).kill(ChessBoard.getClicked());
        ChessBoard.myChessBoard.playSound("move_sound.wav");
      }
      ChessBoard.movePiece(this.row, this.col, null);
      if (ChessBoard.getClicked().getType().equals("Pawn") && (this.row == 0 || this.row == 7)) {
        ChessBoard.getClicked().setType("Queen");
      }
      
      GameControl.switchTurn();
      Piece currentKing = ChessBoard.getPiecesArray()[(GameControl.getTurn().equals("White") ? 0 : 1)][4];
      if (CheckLogic.inCheck(ChessBoard.getPiecesArray(), GameControl.getTurn())) {
        currentKing.setImage("Red-King.png");
      } else {
        currentKing.setType("King");
      }

      Piece otherKing = ChessBoard.getPiecesArray()[(GameControl.getTurn().equals("White") ? 1 : 0)][4];
      if (CheckLogic.inCheck(ChessBoard.getPiecesArray(), (GameControl.getTurn().equals("White") ? "Black" : "White"))) {
        otherKing.setImage("Red-King.png");
      } else {
        otherKing.setType("King");
      }
      
      GameControl.checkForEnd();
    } catch (Exception e) {
      System.out.println("Exception in method onClick() of Move " + e);
    }
  }
}
