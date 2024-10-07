package xxl.core;

import xxl.core.content.Literal;
import xxl.core.exception.ContentException;

/**
 * Representa o conteúdo em uma célula da spreadsheet. Esta é uma classe abstrata que serve como base para
 * diferentes tipos de conteúdos que podem ser armazenados em uma célula, como textos, números ou
 * fórmulas.
 */
public abstract class Content implements java.io.Serializable, Observer {
	protected Cell _cell;

	/**
	 * @return O conteúdo formatado para "impressão"
	 */
	public abstract String toString();
	
	protected abstract Literal value();

	/**
     * @return Uma cópia independente da classe concreta.
     */
	public abstract Content copyConcrete();
	
	public abstract void accept(Visitor v);

	protected abstract void addCell();

	public String asString()  throws ContentException{
		return value().asString();
	}

	public int asInt()  throws ContentException{
		return value().asInt();
	}

	Content copyContent(){
		return this.copyConcrete();
	}
	
	protected void setCell(Cell c){
		_cell = c;
		addCell();
	}

	public Cell getContentCell() throws NullPointerException{
		if(_cell == null)
			throw new NullPointerException("This content doesn't belong to a cell");
		else
			return _cell;
	}

	public void update(){
		_cell.update();
	}
}
