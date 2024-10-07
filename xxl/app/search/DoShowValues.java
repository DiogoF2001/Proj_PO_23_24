package xxl.app.search;

import java.util.ArrayList;

import pt.tecnico.uilib.menus.Command;
import xxl.core.Cell;
import xxl.core.Spreadsheet;
import xxl.core.exception.NoContentException;

/**
 * Comando para pesquisar valores de conteúdo nas células da spreadsheet.
 * Este comando permite ao usuário pesquisar por valores específicos em todas as células da spreadsheet,
 * listando as células que contêm o valor pesquisado.
 */
class DoShowValues extends Command<Spreadsheet> {

	/**
     * Construtor para o comando de mostrar valores.
     * Este construtor inicializa o comando com a spreadsheet que será utilizada para a busca,
     * e também adiciona um campo de texto para o usuário inserir o valor que deseja pesquisar.
     *
     * @param receiver a spreadsheet que será utilizada para a busca
     */
	DoShowValues(Spreadsheet receiver) {
		super(Label.SEARCH_VALUES, receiver);
		addStringField("value", Message.searchValue());
	}

	/**
     * Executa o comando que pesquisa valores de conteúdo nas células da spreadsheet.
     * Este método percorre todas as células da spreadsheet, verificando se o conteúdo de cada célula
     * é igual ao valor pesquisado. As células que contêm o valor são então listadas e exibidas para o usuário.
     */
	@Override
	protected final void execute() {
		String value = stringField("value");
		int i, j;
		Cell c;
		ArrayList<Cell> l = new ArrayList<Cell>();
		for (i = 0; i < _receiver.getRows(); i++) {
			for(j = 0; j < _receiver.getColumns(); j++){
				try{
					c = _receiver.getCell(i, j);
					if(c.value().toString().equals(value))
						l.add(c);
				} catch(NoContentException | NullPointerException e){
					/*Não faz nada porque simplesmente significa que o conteúdo desta célula está vazio*/
				}
			}
		}
		for (Cell cell : l) {
			_display.addLine(cell.toString());
		}
		_display.display();
	}
}
