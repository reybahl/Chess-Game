import java.io.FileNotFoundException;
import org.code.playground.*;
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
      ChessBoard.myChessBoard.playSound("move_sound.wav");
      if (ChessBoard.getPieceAt(this.row, this.col) != null) {
        ChessBoard.getPieceAt(this.row, this.col).kill(ChessBoard.getClicked());
      }
      ChessBoard.movePiece(this.row, this.col, null);
      if (ChessBoard.getClicked().getType().equals("Pawn") && (this.row == 0 || this.row == 7)) {
        ChessBoard.getClicked().setType("Queen");
      }
      GameControl.switchTurn();
      if (CheckLogic.inCheck(ChessBoard.getPiecesArray(), "White")) {
        System.out.println("White in check");
        ChessBoard.getPiecesArray()[0][4].setImage("Red-King.png");
        ChessBoard.myChessBoard.removeClickableImage(ChessBoard.getPiecesArray()[0][4].getButton());
        ChessBoard.myChessBoard.addClickableImage(ChessBoard.getPiecesArray()[0][4].getButton());
        System.out.println("Image set");
      } else {
        ChessBoard.getPiecesArray()[0][4].setType("King");
      }
      if (CheckLogic.inCheck(ChessBoard.getPiecesArray(), "Black")) {
        ChessBoard.getPiecesArray()[1][4].setImage("Red-King.png");
        ChessBoard.myChessBoard.removeClickableImage(ChessBoard.getPiecesArray()[1][4].getButton());
        ChessBoard.myChessBoard.addClickableImage(ChessBoard.getPiecesArray()[1][4].getButton());
      } else {
        ChessBoard.getPiecesArray()[1][4].setType("King");
      }
      GameControl.checkForEnd();
    } catch (Exception e) {
      System.out.println("Exception in method onClick() of Move " + e);
    }
  }
}
