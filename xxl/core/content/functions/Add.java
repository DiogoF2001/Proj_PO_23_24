package xxl.core.content.functions;

import xxl.core.Content;
import xxl.core.content.Literal;
import xxl.core.content.LiteralInteger;
import xxl.core.exception.ContentException;

/**
 * Classe que representa a função de adição binária.
 */
public class Add extends BinaryFunction{
	
	public Add(Content arg1, Content arg2){
		super("ADD", arg1, arg2);
	}

	protected Literal compute()  throws ContentException{
		LiteralInteger result = new LiteralInteger(_arg1.asInt() + _arg2.asInt());
		return result;
	}

	public Content copyConcrete(){
		return new Add(_arg1, _arg2);
	}
}
