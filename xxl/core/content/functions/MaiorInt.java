package xxl.core.content.functions;

import java.util.ArrayList;

import xxl.core.Cell;
import xxl.core.Content;
import xxl.core.content.Literal;
import xxl.core.content.LiteralInteger;
import xxl.core.content.LiteralString;
import xxl.core.content.Message;
import xxl.core.content.Range;
import xxl.core.exception.ContentException;
import xxl.core.exception.InvalidRangeFormatException;
import xxl.core.exception.NoContentException;

public class MaiorInt extends IntervalFunction{
	public MaiorInt(Range range){
		super("MAIORINT", range);
	}

	protected Literal compute() throws ContentException {
		int biggest = 0;
		boolean first = true;
		try{
			ArrayList<Cell> l = _range.getCells();
			for(Cell c: l){
				try{
					if(first){
						biggest = c.value().asInt();
						first = false;
					}
					else{
						if(biggest < c.value().asInt())
							biggest = c.value().asInt();
					}
				}
				catch(NoContentException | ContentException e){
					//does nothing but skip this cell
				}
			}
		} catch(InvalidRangeFormatException e){
			throw new ContentException(e.getMessage());
		}
		if(first)
			return new LiteralString(Message.ErrorValue());

		return new LiteralInteger(biggest);
	}

	public Content copyConcrete(){
		return new MaiorInt(_range);
	}
}
