package xxl.core.content.functions;

import xxl.core.Content;
import xxl.core.Visitor;
import xxl.core.content.Literal;
import xxl.core.content.LiteralString;
import xxl.core.content.Message;
import xxl.core.exception.ContentException;

/**
 * Classe abstrata que representa uma função na spreadsheet.
 * Uma função pode ser avaliada para produzir um valor literal.
 */
public abstract class Function extends Content{
	
	private String _name;

	public Function(String name){
		_name = name;
	}

	public void accept(Visitor v){
		v.visitFunction(this);
	}

	/**
     * Método abstrato para calcular o valor da função.
     *
     * @return um objeto Literal representando o resultado da função
     * @throws ContentException se ocorrer um erro durante o cálculo
     */
	protected abstract Literal compute() throws ContentException;

	public String asString()  throws ContentException{
		return compute().asString();
	}

	public int asInt()  throws ContentException{
		return compute().asInt();
	}

	public String getFunction(){
		return _name;
	}

	/**
     * Retorna o valor da função, ou uma literal com a mensagem de erro se ocorrer um erro durante o cálculo.
     *
     * @return um objeto Literal representando o resultado da função, ou mensagem em formato de LiteralString se ocorrer um erro
     */
	public Literal value(){
		Literal res;
		try {
			res = compute();
		} catch (ContentException e) {
			return new LiteralString(Message.ErrorValue());
		}
		return res;
	}
}
