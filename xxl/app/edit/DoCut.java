package xxl.app.edit;

import java.util.ArrayList;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.app.exception.InvalidContentException;
import xxl.core.Cell;
import xxl.core.Parser;
import xxl.core.Spreadsheet;
import xxl.core.content.Range;
import xxl.core.exception.ContentException;
import xxl.core.exception.InvalidCoordinatesException;
import xxl.core.exception.InvalidRangeFormatException;

/**
 * Comando para cortar células.
 * Este comando permite ao usuário cortar um conjunto de células especificado por um intervalo de endereços.
 * As células cortadas são armazenadas num buffer de corte para que possam ser coladas noutro lugar posteriormente.
 */
class DoCut extends Command<Spreadsheet> {

	/**
     * Construtor do comando de corte.
     * Inicializa o comando com o rótulo apropriado, associando-o à Spreadsheet destinada para a operação de corte,
     * e adiciona o campo de entrada para o usuário especificar o intervalo de células a serem cortadas.
     *
     * @param receiver a Spreadsheet onde será realizada a operação de corte.
     */
	DoCut(Spreadsheet receiver) {
		super(Label.CUT, receiver);
		addStringField("range", Message.address());
	}
  
	/**
     * Executa o comando de corte.
     * Este método processa o intervalo de células especificado pelo usuário, cortando-as e armazenando-as no buffer de corte.
     * Se o intervalo de células for inválido ou ocorrer um erro ao acessar o conteúdo das células, uma exceção será lançada.
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
			_receiver.getCutBuffer().setCells(cells);
		} catch (InvalidRangeFormatException | InvalidCoordinatesException ex){
			throw new InvalidCellRangeException(ex.getMessage());
		} catch (ContentException e){
			throw new InvalidContentException(e.getMessage());
		}
		for (Cell c : cells) {
			c.setContent(null);
		}
  	}
}
