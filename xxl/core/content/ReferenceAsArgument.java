package xxl.core.content;

import xxl.core.Cell;
import xxl.core.Spreadsheet;

/**
 * Classe representando uma referência a uma célula em uma spreadsheet quando usada como argumento de função.
 */
public class ReferenceAsArgument extends Reference {
	
	public ReferenceAsArgument(int row, int column, Spreadsheet s){
		super(row, column, s);
		Cell c;
		c = new Cell(-1, -1);
		c.setContent(this);
		setCell(c);
	}

	@Override
	public String toString(){
		return super.toString().substring(1);
	}
}
