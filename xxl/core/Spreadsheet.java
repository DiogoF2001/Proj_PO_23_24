package xxl.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

import xxl.core.exception.UnrecognizedEntryException;

/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {
  	
	@Serial
  	private static final long serialVersionUID = 202309312359L;

	private int _rows;
	private int _columns;
	private Cell _cells[][];
	private String _fileName;
	private boolean _changed;
	private ArrayList<User> _users;
	private CutBuffer _buffer;

	public Spreadsheet(int rows, int columns, User user){
		int i, j;
		_rows = rows;
		_columns = columns;
		_fileName = null;
		_users = new ArrayList<User>();
		_users.add(user);
		_cells = new Cell[rows][columns];
		for(i = 0; i < rows; i++){
			for (j = 0; j < columns; j++) {
				_cells[i][j] =  new Cell(i+1, j+1);
			}
		}
		_changed = true;
		_buffer = new CutBuffer();
	}
	
	/**
	 * Insert specified content in specified address.
	 *
	 * @param row the row of the cell to change 
	 * @param column the column of the cell to change
	 * @param contentSpecification the specification in a string format of the content to put
	 *        in the specified cell.
	 */
	public void insertContent(int row, int column, Content contentSpecification) throws UnrecognizedEntryException {
		getCell(row, column).setContent(contentSpecification);
		_changed = true;
	}

	public boolean hasChanged(){
		return _changed;
	}

	protected void setAsSaved(){
		_changed = false;
	}

	protected void setFileName(String fileName){
		_fileName = fileName;
	}

	public String getFileName(){
		return _fileName;
	}

	public Cell getCell(int row, int column){
		return _cells[row][column];
	}

	public boolean checkCoords(int row, int column){
		return !(row < 0 || row >= _rows || column < 0 || column >=_columns);
	}

	public int getRows(){
		return _rows;
	}

	public int getColumns(){
		return _columns;
	}

	public CutBuffer getCutBuffer(){
		return _buffer;
	}

	public void addUser(User u){
		_users.add(u);
	}
}
