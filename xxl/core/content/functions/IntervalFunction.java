package xxl.core.content.functions;

import xxl.core.Cell;
import xxl.core.content.Range;
import xxl.core.exception.InvalidRangeFormatException;

/**
 * Classe abstrata que representa uma função que opera em um intervalo de células na spreadsheet.
 * Uma função de intervalo pode ser avaliada para produzir um valor literal com base em um conjunto de células.
 */
public abstract class IntervalFunction extends Function {
	protected Range _range;

	public IntervalFunction(String name, Range range){
		super(name);
		_range = range;
	}

	@Override
	protected void addCell() {
		try{
			for (Cell c : _range.getCells()) {
				c.add(this);
			}
		} catch(InvalidRangeFormatException ex){
			/*Não faz nada uma vez que as gama não é válida*/
		}
	}

	public String toString(){
		return "=" + getFunction() + "(" + _range.toString() + ")";
	}
}
