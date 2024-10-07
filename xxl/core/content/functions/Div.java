package xxl.core.content.functions;

import xxl.core.Content;
import xxl.core.content.Literal;
import xxl.core.content.LiteralInteger;
import xxl.core.exception.ContentException;

/**
 * Classe que representa a função DIV, que divide o valor de um conteúdo por outro.
 */
public class Div extends BinaryFunction {
	public Div(Content arg1, Content arg2){
		super("DIV", arg1, arg2);
	}

	protected Literal compute() throws ContentException{
		LiteralInteger result = new LiteralInteger(_arg1.asInt() / _arg2.asInt());
		return result;
	}

	public Content copyConcrete(){
		return new Div(_arg1, _arg2);
	}
}
