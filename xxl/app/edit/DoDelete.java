package xxl.app.edit;

import java.util.ArrayList;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.core.Cell;
import xxl.core.Parser;
import xxl.core.Spreadsheet;
import xxl.core.content.Range;
import xxl.core.exception.InvalidCoordinatesException;
import xxl.core.exception.InvalidRangeFormatException;


/**
 * Comando para eliminar o conteúdo de células.
 * Este comando permite ao usuário eliminar o conteúdo de um conjunto de células especificado por um intervalo de endereços.
 */
class DoDelete extends Command<Spreadsheet> {

	/**
     * Construtor do comando de eliminar.
     * Inicializa o comando com o rótulo apropriado, associando-o à Spreadsheet destinada para a operação de eliminação,
     * e adiciona o campo de entrada para o usuário especificar o intervalo de células cujo conteúdo será eliminado.
     *
     * @param receiver a Spreadsheet onde será realizada a operação de eliminação.
     */
	DoDelete(Spreadsheet receiver) {
		super(Label.DELETE, receiver);
		addStringField("range", Message.address());
	}

	/**
     * Executa o comando de eliminar.
     * Este método processa o intervalo de células especificado pelo usuário, eliminando o conteúdo dessas células.
     * Se o intervalo de células for inválido, uma exceção será lançada.
     *
     * @throws CommandException se ocorrer um erro durante a execução do comando.
     */
	@Override
	protected final void execute() throws CommandException {
		Range range;
		ArrayList<Cell> cells = null;
		Parser p = new Parser(_receiver);
		try {
			range = p.createRange(stringField("range"));
			cells = range.getCells();
		} catch (InvalidRangeFormatException | InvalidCoordinatesException ex){
			throw new InvalidCellRangeException(ex.getMessage());
		}

		for(Cell c: cells)
			c.setContent(null);
  }
}
