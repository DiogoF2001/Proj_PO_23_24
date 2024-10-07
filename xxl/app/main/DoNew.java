package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.FileOpenFailedException;
import xxl.core.Calculator;

/**
 * Este comando é usado para criar um novo arquivo da spreadsheet.
 * Se houver uma spreadsheet existente com alterações não guardadas, 
 * o usuário será solicitado a guardar essas alterações antes de prosseguir.
 */
class DoNew extends Command<Calculator> {

	/**
     * Construtor para o comando DoNew.
     * 
     * @param receiver o Calculator que executará o comando
     */
	DoNew(Calculator receiver) {
		super(Label.NEW, receiver);
	}

	/**
     * Executa o comando.
     * Se houver uma spreadsheet existente com alterações não guardadas, 
     * o usuário será solicitado a guardar essas alterações.
     * Após isso, o usuário será solicitado a inserir o número de linhas e colunas para a nova spreadsheet.
     * Por fim, a nova spreadsheet é criada.
     * 
     * @throws CommandException se ocorrer algum erro durante a execução do comando
     */
	@Override
	protected final void execute() throws CommandException {
		Integer rows;
		Integer cols;
		String name;
		try {
			if(_receiver.getSpreadsheet() != null){
				if(_receiver.getSpreadsheet().hasChanged()){
					_display.clear();
					if(Form.confirm(Message.saveBeforeExit())){
						if(_receiver.getFile() == null){
							name = Form.requestString(Message.newSaveAs());
							_receiver.saveAs(name);
						}
						else
							_receiver.saveAs(_receiver.getFile());	
					}
				}
			}
			
		} catch(Exception e) {
			throw new FileOpenFailedException(e);
		}
		rows = Form.requestInteger(Message.lines());
		cols = Form.requestInteger(Message.columns());
		_receiver.createNewSpreadsheet(rows, cols);
	}
}

