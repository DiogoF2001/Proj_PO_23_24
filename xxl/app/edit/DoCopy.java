package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.app.exception.InvalidContentException;
import xxl.core.Parser;
import xxl.core.Spreadsheet;
import xxl.core.content.Range;
import xxl.core.exception.ContentException;
import xxl.core.exception.InvalidCoordinatesException;
import xxl.core.exception.InvalidRangeFormatException;

/**
 * Comando para copiar células.
 * Este comando permite ao usuário copiar um conjunto de células especificado por um intervalo de endereços.
 */
class DoCopy extends Command<Spreadsheet> {

  	/**
     * Construtor do comando de cópia.
     * Inicializa o comando com o rótulo apropriado, associando-o à Spreadsheet destinada para a operação de cópia,
     * e adiciona o campo de entrada para o usuário especificar o intervalo de células a serem copiadas.
     *
     * @param receiver a Spreadsheet onde será realizada a operação de cópia.
     */
	DoCopy(Spreadsheet receiver) {
		super(Label.COPY, receiver);
		addStringField("range", Message.address());
	}
  
	/**
     * Executa o comando de cópia.
     * Este método processa o intervalo de células especificado pelo usuário, copiando-as para o buffer de cópia.
     * Se o intervalo de células for inválido ou ocorrer um erro ao acessar o conteúdo das células, uma exceção será lançada.
     *
     * @throws CommandException se ocorrer um erro durante a execução do comando.
     */
	@Override
	protected final void execute() throws CommandException {
		Range range;
		Parser p = new Parser(_receiver);
		try {
			range = p.createRange(stringField("range"));
			_receiver.getCutBuffer().setCells(range.getCells());
		} catch (InvalidRangeFormatException | InvalidCoordinatesException ex){
			throw new InvalidCellRangeException(stringField("range"));
		} catch (ContentException e){
			throw new InvalidContentException(e.getMessage(), e);
		}
	}
}
