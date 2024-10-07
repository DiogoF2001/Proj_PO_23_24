package xxl.core.content;

import xxl.core.Content;

/**
 * Classe que representa um valor literal string em uma spreadsheet.
 */
public class LiteralString extends Literal {
	private String _value;

	public LiteralString(String s){
		_value = s;
	}

	@Override
	public String asString(){
		return _value;
	}

	/**
	 * Converte o valor literal para o formato de "impressão"
	 *
	 * @return a representação em string do valor literal, com uma plica
	 *         antes do valor, a menos que o valor seja de erro
	 */
	public String toString(){
		if(_value != Message.ErrorValue())
			return "\'" + _value;
		else
			return _value;
	}

	public Content copyConcrete(){
		return new LiteralString(_value);
	}
}
