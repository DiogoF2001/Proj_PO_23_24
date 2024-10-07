package xxl.app.edit;

import java.util.ArrayList;
import pt.tecnico.uilib.menus.Command;
import xxl.core.Spreadsheet;
import xxl.core.Cell;

/**
 * Comando para exibir o conteúdo do buffer de corte.
 * Este comando permite ao usuário visualizar o conteúdo atual do buffer de corte, que contém as células que foram cortadas ou copiadas.
 */
class DoShowCutBuffer extends Command<Spreadsheet> {

	/**
     * Construtor do comando de exibir buffer de corte.
     * Inicializa o comando com o rótulo apropriado e associa-o à Spreadsheet destinada para a operação de exibir o buffer de corte.
     *
     * @param receiver a Spreadsheet onde será realizada a operação de exibir o buffer de corte.
     */
	DoShowCutBuffer(Spreadsheet receiver) {
		super(Label.SHOW_CUT_BUFFER, receiver);
	}

	/**
     * Executa o comando de exibir o buffer de corte.
     * Este método processa o buffer de corte, exibindo o conteúdo das células que estão atualmente no buffer de corte.
     *
     */
	@Override
	protected final void execute() {
		ArrayList<Cell> cells = _receiver.getCutBuffer().getCells();
		if(cells != null){
			for(Cell c: cells)
				_display.addLine(c.toString());
			_display.display();
		}
	}
}
