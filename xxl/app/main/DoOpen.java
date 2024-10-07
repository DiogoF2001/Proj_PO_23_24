package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.FileOpenFailedException;
import xxl.core.exception.UnavailableFileException;
import xxl.core.Calculator;

/**
 * Comando para abrir um arquivo existente.
 */
class DoOpen extends Command<Calculator> {

	/**
     * Construtor para o comando DoOpen.
     * 
     * @param receiver o Calculator que executará o comando
     */
	DoOpen(Calculator receiver) {
		super(Label.OPEN, receiver);
	}

	/**
     * Executa o comando.
     * Se houver uma spreadsheet existente com alterações não salvas, 
     * o usuário será solicitado a guardar essas alterações.
     * Depois, o usuário será solicitado a inserir o nome do arquivo para abrir.
     * Por fim, o arquivo é carregado.
     * 
     * @throws CommandException se ocorrer algum erro durante a execução do comando
     */
	@Override
	protected final void execute() throws CommandException {
		String name;
		try {
			if(_receiver.getSpreadsheet() != null){
				if(_receiver.getSpreadsheet().hasChanged()){
					if(Form.confirm(Message.saveBeforeExit())){
						if(_receiver.getFile() == null){
							_receiver.saveAs(Form.requestString(Message.newSaveAs()));
						}
						else
							_receiver.saveAs(_receiver.getFile());
					}
				}	
			}
		} catch(Exception e) {
			throw new FileOpenFailedException(e);
		}
		name = Form.requestString(Message.openFile());
		try {
			_receiver.load(name);
		} 
		catch (UnavailableFileException e) 
		{
			throw new FileOpenFailedException(e);
		}
	}
}
