package xxl.core.content;

import xxl.core.Cell;
import xxl.core.Content;
import xxl.core.Spreadsheet;
import xxl.core.Visitor;
import xxl.core.exception.NoContentException;

/**
 * Classe representando uma referência a uma célula em uma spreadsheet.
 */
public class Reference extends Content{
	private int _row;
	private int _column;
	private Spreadsheet _sheet;

	public Reference(int row, int column, Spreadsheet s){
		_row = row;
		_column = column;
		_sheet = s; 
	}

	@Override
	protected void addCell() {
		getCell().add(_cell);
	}

	public void accept(Visitor v){
		v.visitReference(this);
	}

	protected Cell getCell(){
		return _sheet.getCell(_row-1, _column-1);
	}

	protected Literal value(){
		try {
			return getCell().value();
		} catch (NoContentException e) {
			return new LiteralString(Message.ErrorValue());
		}
	}

	@Override
	public String toString(){
		return "=" + _row + ";" + _column;
	}

	public Content copyConcrete(){
		return new Reference(_row, _column, _sheet);
	}
}
