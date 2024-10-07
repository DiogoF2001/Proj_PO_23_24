package xxl.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User implements java.io.Serializable{
	
	private final String _name;
	private List<Spreadsheet> _activeSheets;

	public User(String n){
		_name = n;
		_activeSheets = new ArrayList<>();
	}

	public final String getName(){
		return _name;
	}

	public boolean addSheet(Spreadsheet s){
		return _activeSheets.add(s);
	}

	public List<Spreadsheet> getSpreadsheets(){
		return Collections.unmodifiableList(_activeSheets);
	}

}
