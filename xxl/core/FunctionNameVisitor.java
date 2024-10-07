package xxl.core;

import xxl.core.content.Literal;
import xxl.core.content.Reference;
import xxl.core.content.functions.Function;

public class FunctionNameVisitor implements Visitor{
	private String _name;

	public void visitLiteral(Literal l){
		_name = "";
	}

	public void visitReference(Reference r){
		_name = "";
	}

	public void visitFunction(Function f){
		_name = f.getFunction();
	}

	public void visitCell(Cell c){
		_name = "";
	}

	public String getName(){
		return _name;
	}
}
