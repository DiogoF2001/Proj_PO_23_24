package xxl.core.content;

import xxl.core.Content;
import xxl.core.Visitor;
import xxl.core.exception.ContentException;

/**
 * Classe abstrata que representa um valor literal em uma spreadsheet.
 *
 * Um valor literal é um valor concreto, seja ele um número, uma string, ou outro tipo de dado que pode ser representado de forma direta na spreadsheet.
 */
public abstract class Literal extends Content{
	protected Literal value(){
		return this;
	}

	@Override
	protected void addCell() {
		/*Um conteudo literal nunca vai depender de outro*/
	}

	public void accept(Visitor v){
		v.visitLiteral(this);
	}

	/**
   * Converte o valor literal para uma string.
   *
   * @return a representação em string do valor literal
   * @throws ContentException se a operação não for suportada
   */
	public String asString() throws ContentException{
		throw new ContentException("A operação asString() não é suportada.");
	}
	
	/**
   * Converte o valor literal para um inteiro.
   *
   * @return a representação em inteiro do valor literal
   * @throws ContentException se a operação não for suportada
   */
	public int asInt()  throws ContentException{
        throw new ContentException("A operação asInt() não é suportada.");	
    }
}
