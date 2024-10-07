package xxl.core;

import java.util.HashSet;
import java.util.Set;
import xxl.core.content.Literal;
import xxl.core.exception.NoContentException;

/**
 * Representa uma célula em uma spreadsheet.
 */
public class Cell implements java.io.Serializable, Observer {
	private int _row;
	private int _column;
	private Content _content;
	private boolean _noContent;
	protected Literal _value;
	private Set<Observer> _observers;
	

	public Cell(int row, int column){
		_row = row;
		_column = column;
		_content = null;
		_noContent = true;
		_value = null;
		_observers = new HashSet<>();
	}

	/**
     * Define o conteúdo da célula.
     *
     * @param c O conteúdo a ser definido, pode ser nulo.
     */
	public void setContent(Content c){
		_content = c;
		if(c == null)
			_noContent = true;
		else{
			_noContent = false;
			_content.setCell(this);
			update();
		}
		notifyObservers();
	}

	/**
     * Retorna o valor da célula.
     *
     * @return O valor da célula como um Literal.
     * @throws NoContentException Se a célula não tiver conteúdo.
     */
	public Literal value() throws NoContentException{
		if(_noContent)
			throw new NoContentException();
		else
			return _value;
	}

	public String toString(){
		ContentToString v = new ContentToString();
		this.accept(v);
		if(!_noContent)
			return "" + _row + ";" + _column + "|" + _value.toString() + v.getToString();
		return "" + _row + ";" + _column + "|";
	}

	public int getRow(){
		return _row;
	}

	public int getColumn(){
		return _column;
	}

	Content getContent() throws NoContentException{
		if(_noContent)
			throw new NoContentException();
		else
			return _content;
	}

	/**
     * @return Uma cópia independente do conteúdo da célula.
     */
	public Content copy(){
		if(_noContent)
			return null;
		else
			return _content.copyContent();
	}

	public void accept(Visitor v){
		if(!_noContent)
			_content.accept(v);
		else
			v.visitCell(this);
	}

	public boolean add(Observer obs){
		return _observers.add(obs);
	}

	public boolean remove(Observer obs){
		return _observers.remove(obs);
	}

	private void notifyObservers(){
		if(!_observers.isEmpty())
			for (Observer obs : _observers) {
				if(obs != null)
					obs.update();
			}
	}

	public void update(){
		if(!_noContent)
			_value = _content.value();
		notifyObservers();
	}
}
