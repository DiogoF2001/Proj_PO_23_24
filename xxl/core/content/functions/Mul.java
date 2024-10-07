package xxl.core.content.functions;

import xxl.core.Content;
import xxl.core.content.Literal;
import xxl.core.content.LiteralInteger;
import xxl.core.exception.ContentException;

/**
 * Classe que representa a função de multiplicação em uma spreadsheet.
 * Esta função recebe dois argumentos e retorna o produto deles.
 */
public class Mul extends BinaryFunction {
	public Mul(Content arg1, Content arg2){
		super("MUL", arg1, arg2);
	}

	protected Literal compute() throws ContentException{
		LiteralInteger result = new LiteralInteger(_arg1.asInt() * _arg2.asInt());
		return result;
	}

	public Content copyConcrete(){
		return new Div(_arg1, _arg2);
	}
}
