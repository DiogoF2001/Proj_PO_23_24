package xxl.core;

import java.io.Serializable;
import java.util.ArrayList;
import xxl.core.exception.ContentException;

/**
 * A classe CutBuffer é utilizada para armazenar um conjunto de células que foram recortadas ou que serão
 * utilizadas em uma operação de colar. O buffer pode armazenar células de uma linha ou coluna, e isso é 
 * indicado pela propriedade _vertical.
 */
public class CutBuffer implements Serializable{
	private ArrayList<Cell> _cells = null;
	private boolean _vertical;

	public CutBuffer(){
	}

	/**
     * Define as células que serão armazenadas no buffer.
     *
     * @param cells Uma lista de células a serem armazenadas.
     * @throws ContentException Se ocorrer um erro ao copiar o conteúdo das células.
     */
	public void setCells(ArrayList<Cell> cells) throws ContentException{
		_cells = new ArrayList<Cell>();
		int i = 1;
		Cell temp;
		if(cells.size() > 1){
			if(cells.get(0).getRow() == cells.get(1).getRow()){
				for (Cell c : cells) {
					temp = new Cell(1, i);
					temp.setContent(c.copy());
					_cells.add(temp);
					i++;
				}
				_vertical = true;
			}
			else{
				if (cells.get(0).getColumn() == cells.get(1).getColumn()) {
					for (Cell c : cells) {
						temp = new Cell(i, 1);
						temp.setContent(c.copy());
						_cells.add(temp);
						i++;
					}
					_vertical = false;
				}
			}
		}
		else{
			temp = new Cell(1, 1);
			temp.setContent(cells.get(0).copy());
			_cells.add(temp);
		}
	}

	public final ArrayList<Cell> getCells(){
		return _cells;
	}

	/**
     * Verifica se as células no buffer estão dispostas verticalmente.
     *
     * @return True se as células estão dispostas verticalmente, false caso contrário.
     */
	public boolean isVertical(){
		return _vertical;
	}
}
