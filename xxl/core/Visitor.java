package xxl.core;

import xxl.core.content.Literal;
import xxl.core.content.Reference;
import xxl.core.content.functions.Function;

public interface Visitor {
	public void visitLiteral(Literal l);
	public void visitReference(Reference r);
	public void visitFunction(Function f);
	public void visitCell(Cell c);
}
