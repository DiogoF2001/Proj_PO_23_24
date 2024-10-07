package xxl.app.edit;

import java.util.ArrayList;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.core.Cell;
import xxl.core.Parser;
import xxl.core.Spreadsheet;
import xxl.core.exception.InvalidCoordinatesException;
import xxl.core.exception.InvalidRangeFormatException;


/**
 * Comando para colar dados em células.
 * Este comando permite ao usuário colar o conteúdo presente no buffer de corte em um conjunto de células especificado por um intervalo de endereços.
 */
class DoPaste extends Command<Spreadsheet> {

	/**
     * Construtor do comando de colar.
     * Inicializa o comando com o rótulo apropriado, associando-o à Spreadsheet destinada para a operação de colar,
     * e adiciona o campo de entrada para o usuário especificar o intervalo de células onde o conteúdo será colado.
     *
     * @param receiver a Spreadsheet onde será realizada a operação de colar.
     */
	DoPaste(Spreadsheet receiver) {
		super(Label.PASTE, receiver);
		addStringField("range", Message.address());
	}

	/**
     * Executa o comando de colar.
     * Este método processa o intervalo de células especificado pelo usuário,
     * colando o conteúdo presente no buffer de corte nas células do intervalo.
     * Se o intervalo de células for inválido, uma exceção será lançada.
     *
     * @throws CommandException se ocorrer um erro durante a execução do comando.
     */
	@Override
	protected final void execute() throws CommandException {
		int i, row, col;
		String s = stringField("range");
		ArrayList<Cell> cells = null;
		ArrayList<Cell> cutBuffer = _receiver.getCutBuffer().getCells();
		if(cutBuffer == null || cutBuffer.isEmpty())
			return;
		try {
			cells = (new Parser(_receiver)).createRange(s).getCells();
		} catch (InvalidRangeFormatException | InvalidCoordinatesException ex){
			throw new InvalidCellRangeException(s);
		}
		if(cells.size() == 1){
			row = cells.get(0).getRow();
			col = cells.get(0).getColumn();
			if(_receiver.getCutBuffer().isVertical()){
				for (i = 0; i < cutBuffer.size() && i + col <= _receiver.getColumns(); i++) {
					_receiver.getCell(row-1, col+i-1).setContent(cutBuffer.get(i).copy());
				}
			}
			else{
				for (i = 0; i < cutBuffer.size() && i + row <= _receiver.getRows(); i++) {
					_receiver.getCell(row+i-1, col-1).setContent(cutBuffer.get(i).copy());
				}
			}
			return;
		}
		if(_receiver.getCutBuffer().getCells().size() != cells.size() && cells.size() != 1)
			return;
		for (i = 0; i < cells.size(); i++) {
			cells.get(i).setContent(cutBuffer.get(i).copy());
		}
	}
}
