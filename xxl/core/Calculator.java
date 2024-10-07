package xxl.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import xxl.app.exception.InvalidCellRangeException;
import xxl.core.exception.ImportFileException;
import xxl.core.exception.InvalidFunctionException;
import xxl.core.exception.UnavailableFileException;
import xxl.core.exception.UnrecognizedEntryException;

/**
 * Classe que representa uma aplicação de spreadsheet.
 */
public class Calculator {
  
  private Spreadsheet _spreadsheet;
  private ArrayList<User> _users;
  private User _active;
  private String _fileName;

  public Calculator(){
	_users = new ArrayList<User>();
	_active = new User("root");
	_users.add(_active);
	_spreadsheet = null;
	_fileName = null;
  }

  /**
   * Return the filename associated.
   *
   * @returns the currently associated filename of this application. This reference can be null.
   */
  public final String getFile(){
	return _fileName;
  }

  public void setFile(String name){
	_fileName = name;
  }

  /**
   * Creates a new spreadsheet
   *
   * @param rows o número de linhas da nova spreadsheet
   * @param columns o número de colunas da nova spreadsheet
   */
  public void createNewSpreadsheet(int rows, int columns) throws InvalidCellRangeException
  {
	if(rows <= 2*columns)
		throw new InvalidCellRangeException("" + rows + ";" + columns);
	_spreadsheet = new Spreadsheet(rows, columns, _active);
  }
  
  /**
   * Return the current spreadsheet.
   *
   * @returns the current spreadsheet of this application. This reference can be null.
   */
  public final Spreadsheet getSpreadsheet() {
    return _spreadsheet;
  }
  
  /**
   * Saves the serialized application's state into the specified file. The current network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
	public void saveAs(String filename) throws FileNotFoundException, IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			_spreadsheet.setFileName(filename);
			out.writeObject(_spreadsheet);
		} 
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Não foi possível encontrar o arquivo: " + filename);
		} 
		catch (IOException e) {
			throw new IOException("Erro ao serializar " + e);
		}
		_spreadsheet.setAsSaved();
	}
  
  /**
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   */
	public void load(String filename) throws UnavailableFileException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			_spreadsheet = (Spreadsheet) in.readObject();
		} 
		catch (FileNotFoundException e) {
			throw new UnavailableFileException("Não foi possível encontrar o arquivo: " + filename);
		}
		catch (IOException | ClassNotFoundException e) {
			throw new UnavailableFileException("Erro ao ler " + filename);
		}
  	}
  
  /**
   * Read text input file and create domain entities.
   *
   * @param filename name of the text input file
   * @throws ImportFileException
   */
  public void importFile(String filename) throws ImportFileException, InvalidCellRangeException{
    try {
	  _spreadsheet = (new Parser()).parseFile(filename);
	  
    } catch (IOException | UnrecognizedEntryException | InvalidFunctionException e) {
      throw new ImportFileException(filename, e);
    }
  }

  public List<User> getUsers(){
	return Collections.unmodifiableList(_users);
  }

  public void setUsers(ArrayList<User> l){
	_users = l;
  }

}
