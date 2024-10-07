package xxl.app.edit;

import java.util.ArrayList;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.app.exception.InvalidContentException;
import xxl.app.exception.UnknownFunctionException;
import xxl.core.Cell;
import xxl.core.Content;
import xxl.core.Parser;
import xxl.core.Spreadsheet;
import xxl.core.content.Range;
import xxl.core.exception.InvalidCoordinatesException;
import xxl.core.exception.InvalidFunctionException;
import xxl.core.exception.InvalidRangeFormatException;
import xxl.core.exception.UnrecognizedEntryException;

/**
 * Classe para inserir dados em células.
 * Este comando permite ao usuário inserir um conteúdo específico em um conjunto de células especificado por um intervalo de endereços.
 */
class DoInsert extends Command<Spreadsheet> {

	/**
     * Construtor do comando de inserir.
     * Inicializa o comando com o rótulo apropriado, associando-o à Spreadsheet destinada para a operação de inserção,
     * e adiciona os campos de entrada para o usuário especificar o intervalo de células e o conteúdo a ser inserido.
     *
     * @param receiver a Spreadsheet onde será realizada a operação de inserção.
     */
	DoInsert(Spreadsheet receiver) {
		super(Label.INSERT, receiver);
		addStringField("range", Message.address());
		addStringField("content", Message.contents());
	}
	
	/**
     * Executa o comando de inserir.
     * Este método processa o intervalo de células e o conteúdo especificados pelo usuário,
     * inserindo o conteúdo nas células do intervalo.
     * Se o intervalo de células for inválido ou o conteúdo não for reconhecido, uma exceção será lançada.
     *
     * @throws CommandException se ocorrer um erro durante a execução do comando.
     */
	@Override
	protected final void execute() throws CommandException {
		Range range;
		Content content;
		ArrayList<Cell> cells = null;
		Parser p = new Parser(_receiver);
		try {
			range = p.createRange(stringField("range"));
			content = p.parseContent(stringField("content"));
			cells = range.getCells();
		} catch (InvalidRangeFormatException | InvalidCoordinatesException ex){
			throw new InvalidCellRangeException(ex.getMessage());
		} catch (UnrecognizedEntryException e){
			throw new InvalidContentException(e.getMessage(),e);
		} catch (InvalidFunctionException e2){
			throw new UnknownFunctionException(e2.getFunctionName());
		}

		for(Cell c: cells)
			c.setContent(content);
		
	}
}
