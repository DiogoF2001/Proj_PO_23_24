package xxl.app.edit;

import java.util.ArrayList;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.core.Spreadsheet;
import xxl.app.exception.InvalidCellRangeException;
import xxl.core.Parser;
import xxl.core.content.Range;
import xxl.core.exception.InvalidCoordinatesException;
import xxl.core.exception.InvalidRangeFormatException;
import xxl.core.Cell;

/**
 * Comando para exibir o conteúdo das células.
 * Este comando permite ao usuário visualizar o conteúdo de um conjunto de células especificado por um intervalo de endereços.
 */
class DoShow extends Command<Spreadsheet> {

	/**
     * Construtor do comando de exibir.
     * Inicializa o comando com o rótulo apropriado, associando-o à Spreadsheet destinada para a operação de exibir,
     * e adiciona o campo de entrada para o usuário especificar o intervalo de células que deseja visualizar.
     *
     * @param receiver a Spreadsheet onde será realizada a operação de exibir.
     */
	DoShow(Spreadsheet receiver) {
		super(Label.SHOW, receiver);
		addStringField("range", Message.address());
	}

	/**
     * Executa o comando de exibir.
     * Este método processa o intervalo de células especificado pelo usuário,
     * exibindo o conteúdo das células no intervalo.
     * Se o intervalo de células for inválido, uma exceção será lançada.
     *
     * @throws CommandException se ocorrer um erro durante a execução do comando.
     */
	@Override
	protected final void execute() throws CommandException{
		String s = stringField("range");
		Range range;
		ArrayList<Cell> cells = null;
		try {
			range = (new Parser(_receiver)).createRange(s);
			cells = range.getCells();
		} catch (InvalidRangeFormatException | InvalidCoordinatesException ex){
			throw new InvalidCellRangeException(s);
		}

		for(Cell c: cells)
			_display.addLine(c.toString());
		_display.display();

	}
}
