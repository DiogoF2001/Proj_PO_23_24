package xxl.core;

import xxl.core.content.Literal;
import xxl.core.content.Reference;
import xxl.core.content.functions.Function;

public class ContentToString implements Visitor{
	private String _name;

	public void visitLiteral(Literal l){
		_name = "";
	}

	public void visitReference(Reference r){
		_name = r.toString();
	}

	public void visitFunction(Function f){
		_name = f.toString();
	}

	public void visitCell(Cell c){
		_name = "";
	}

	public String getToString(){
		return _name;
	}
}
