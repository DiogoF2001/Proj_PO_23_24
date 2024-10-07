package xxl.core.content.functions;

import java.util.ArrayList;

import xxl.core.Cell;
import xxl.core.Content;
import xxl.core.content.Literal;
import xxl.core.content.LiteralString;
import xxl.core.content.Range;
import xxl.core.exception.ContentException;
import xxl.core.exception.InvalidRangeFormatException;
import xxl.core.exception.NoContentException;

/**
 * Classe que representa a função CONCAT, que concatena os valores de um intervalo de células.
 */
public class Concat extends IntervalFunction {
	public Concat(Range range){
		super("CONCAT", range);
	}
	protected Literal compute() throws ContentException{
		String result = "";
		try{
			ArrayList<Cell> l = _range.getCells();
			for(Cell c: l){
				try{
					result += c.value().asString();
				}
				catch(NoContentException | ContentException e){
					// If it doesen't have content it carries on
				}
			}
		} catch(InvalidRangeFormatException e){
			throw new ContentException(e.getMessage());
		}
		return new LiteralString(result);
	}

	public Content copyConcrete(){
		return new Concat(_range);
	}
}
