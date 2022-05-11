import org.code.playground.*;
import org.code.media.*;

public class ValidMove {
  boolean valid;
  int row;
  int col;
  
  TextItem button;

  public ValidMove (boolean valid, int row, int col) {
    this.valid = valid;
    this.row = row;
    this.col = col;
    
    this.button = new TextItem("O", col*47 + 10, row*47 + 10, Color.BLUE, Font.SANS, 50, 0);
  }
  public boolean getValid() {
    return this.valid;
  }
  public TextItem getButton () {
    return this.button;
  } 
} 
