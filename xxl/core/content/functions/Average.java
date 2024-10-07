package xxl.core.content.functions;

import java.util.ArrayList;
import xxl.core.Cell;
import xxl.core.Content;
import xxl.core.content.Literal;
import xxl.core.content.LiteralInteger;
import xxl.core.content.Range;
import xxl.core.exception.ContentException;
import xxl.core.exception.InvalidRangeFormatException;
import xxl.core.exception.NoContentException;

/**
 * Classe que representa a função de média em um intervalo de células.
 */
public class Average extends IntervalFunction {
	public Average(Range range){
		super("AVERAGE", range);
	}

	protected Literal compute() throws ContentException{
		int result = 0, count = 0;
		try{
			ArrayList<Cell> l = _range.getCells();
			for(Cell c: l){
				try{
					result += c.value().asInt();
					count++;
				}
				catch(NoContentException e){
					throw new ContentException(e.getMessage());
				}
			}
		} catch(InvalidRangeFormatException e){
			throw new ContentException(e.getMessage());
		}
		return new LiteralInteger(result/count);
	}

	public Content copyConcrete(){
		return new Average(_range);
	}
}
