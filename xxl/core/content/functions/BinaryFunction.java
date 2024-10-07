package xxl.core.content.functions;

import xxl.core.Content;

/**
 * Classe abstrata que representa uma função binária, ou seja, uma função que recebe dois argumentos.
 */
public abstract class BinaryFunction extends Function{
	
	protected Content _arg1;
	protected Content _arg2;

	public BinaryFunction(String name, Content arg1, Content arg2){
		super(name);
		_arg1 = arg1;
		_arg2 = arg2;
	}

	@Override
	protected void addCell() {
		try{
			_arg1.getContentCell().add(_cell);
		} catch(NullPointerException e){
			/*Não faz nada porque significa que é uma constante*/
		}
		try{
			_arg2.getContentCell().add(_cell);
		} catch(NullPointerException e){
			/*Não faz nada porque significa que é uma constante*/
		}
	}

	public String toString(){
		return "=" + getFunction() + "(" + _arg1.toString() + "," + _arg2.toString() + ")";
	}
}
