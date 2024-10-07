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
 * Classe que representa a função de produto em uma spreadsheet.
 * Esta função recebe um intervalo de células e retorna o produto dos valores nessas células.
 */
public class Product extends IntervalFunction {
	public Product(Range range){
		super("PRODUCT", range);
	}

	protected Literal compute() throws ContentException {
		int result = 1;
		try{
			ArrayList<Cell> l = _range.getCells();
			for(Cell c: l){
				try{
					result *= c.value().asInt();
				}
				catch(NoContentException e){
					throw new ContentException(e.getMessage());
				}
			}
		} catch(InvalidRangeFormatException e){
			throw new ContentException(e.getMessage());
		}
		return new LiteralInteger(result);
	}

	public Content copyConcrete(){
		return new Product(_range);
	}
}
