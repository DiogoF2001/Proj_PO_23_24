package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.FileOpenFailedException;
import xxl.core.Calculator;

/**
 * Comando para guardar em um arquivo com o nome atual (se não tiver nome, pergunte por um).
 */
class DoSave extends Command<Calculator> {

	/**
     * Construtor para o comando DoSave.
     * 
     * @param receiver o Calculator que executará o comando
     */
	DoSave(Calculator receiver) {
		super(Label.SAVE, receiver, xxl -> xxl.getSpreadsheet() != null);
	}

	/**
     * Executa o comando.
     * Guarda a spreadsheet atual em um arquivo com o nome atual. Se a spreadsheet não tiver nome, pede por um nome.
     * 
     * @throws CommandException se ocorrer algum erro durante a execução do comando
     */
	@Override
	protected final void execute() throws CommandException{
		if(_receiver.getSpreadsheet().hasChanged()){
			if(_receiver.getFile() == null){
				_receiver.setFile(Form.requestString(Message.newSaveAs()));
			}
			try{
				_receiver.saveAs(_receiver.getFile());
			}
			catch(Exception ex){
				throw new FileOpenFailedException(ex);
			}
		}
	}
}
