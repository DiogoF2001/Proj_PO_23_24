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
 * Classe que representa a função COALESCE, que retorna o primeiro valor não nulo em um intervalo de células.
 */
public class Coalesce extends IntervalFunction {
	public Coalesce(Range range){
		super("COALESCE", range);
	}
	protected Literal compute() throws ContentException{
		String result = "";
		try{
			ArrayList<Cell> l = _range.getCells();
			for(Cell c: l){
				try{
					result += c.value().asString();
					break;
				}
				catch(NoContentException | ContentException e){
					/*Se não tiver conteudo continua*/
				}
			}
		} catch(InvalidRangeFormatException e){
			throw new ContentException(e.getMessage());
		}
		return new LiteralString(result);
	}

	public Content copyConcrete(){
		return new Coalesce(_range);
	}
}
