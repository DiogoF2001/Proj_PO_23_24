package xxl.app.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pt.tecnico.uilib.menus.Command;
import xxl.core.Cell;
import xxl.core.FunctionNameVisitor;
import xxl.core.Spreadsheet;

/**
 * Comando para pesquisar nomes de funções na spreadsheet.
 * Este comando permite ao usuário pesquisar por funções específicas em todas as células da spreadsheet,
 * listando as células que contêm a função pesquisada.
 */
class DoShowFunctions extends Command<Spreadsheet> {

	/**
     * Construtor para o comando de mostrar funções.
     * Este construtor inicializa o comando com a spreadsheet que será utilizada para a busca,
     * e também adiciona um campo de texto para o usuário inserir o nome da função que deseja pesquisar.
     *
     * @param receiver a spreadsheet que será utilizada para a busca
     */
	DoShowFunctions(Spreadsheet receiver) {
		super(Label.SEARCH_FUNCTIONS, receiver);
		addStringField("name", Message.searchFunction());
	}

	/**
     * Executa o comando que pesquisa nomes de funções na spreadsheet.
     * Este método percorre todas as células da spreadsheet, verificando se o conteúdo de cada célula
     * contém o nome da função pesquisada. As células que contêm a função são então listadas e exibidas para o usuário.
     */
	@Override
	protected final void execute() {
		String name = stringField("name");
		int i, j;
		Cell c;
		ArrayList<Cell> l = new ArrayList<Cell>();
		FunctionNameVisitor v = new FunctionNameVisitor();
		for (i = 0; i < _receiver.getRows(); i++) {
			for(j = 0; j < _receiver.getColumns(); j++){
				c = _receiver.getCell(i, j);
				c.accept(v);
				if(v.getName() != "" && v.getName().contains(name))
					l.add(c);
			}
		}

		/*Ordena a lista por nome de função*/
		Collections.sort(l, new Comparator<Cell>() {
			public int compare(Cell a, Cell b) {
				String aName;
				String bName;
				FunctionNameVisitor v = new FunctionNameVisitor();
				a.accept(v);
				aName = v.getName();
				b.accept(v);
				bName = v.getName();
				return aName.compareTo(bName);
			}
		});

		for (Cell cell : l) {
			_display.addLine(cell.toString());
		}
		_display.display();
	}
}
