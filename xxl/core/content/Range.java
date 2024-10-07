package xxl.core.content;

import java.util.ArrayList;

import xxl.core.Cell;
import xxl.core.Spreadsheet;
import xxl.core.exception.InvalidRangeFormatException;

/**
 * Classe que representa um intervalo de células em uma spreadsheet.
 */
public class Range implements java.io.Serializable {

	private int _iniRow;
	private int _iniCol;
	private int _finRow;
	private int _finCol;
	private Spreadsheet _sheet;

	public Range(int iniRow, int iniCol, int finRow, int finCol, Spreadsheet sheet){
		_iniRow = iniRow;
		_iniCol = iniCol;
		_finRow = finRow;
		_finCol = finCol;
		_sheet = sheet;
	}

	/**
	 * Retorna a lista de células no intervalo.
	 *
	 * @return um ArrayList contendo todas as células no intervalo
	 * @throws InvalidRangeFormatException se o intervalo não for válido
	 */
	public ArrayList<Cell> getCells() throws InvalidRangeFormatException{
		int i;
		ArrayList<Cell> l = new ArrayList<Cell>();
		if(_iniRow == _finRow){
			for (i = _iniCol; i <= _finCol; i++) {
				l.add(_sheet.getCell(_iniRow, i));
			}
		}
		else{
			if(_iniCol == _finCol){
				for (i = _iniRow; i <= _finRow; i++) {
					l.add(_sheet.getCell(i, _iniCol));
				}
			}
			else{
				throw new InvalidRangeFormatException("O intevalo é inválido ");
			}
		}
		return l;
	}

	public String toString(){
		return "" + (_iniRow+1) + ";" + (_iniCol+1) + ":" + (_finRow+1) + ";" + (_finCol+1);
	}
}
