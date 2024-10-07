package xxl.core.content;
import xxl.core.Content;

/**
 * Classe que representa um valor literal inteiro em uma spreadsheet.
 */
public class LiteralInteger extends Literal {
	private int _value;

	public LiteralInteger(int value){
		_value = value;
	}

	@Override
	public int asInt(){
		return _value;
	}

	public String toString(){
		return "" + _value;
	}

	public Content copyConcrete(){
		return new LiteralInteger(_value);
	}
}
