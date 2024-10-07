package xxl.app.main;

import java.util.ArrayList;
import java.util.List;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.core.Calculator;
import xxl.core.User;

public class DoRemoveUsers extends Command<Calculator>{
	DoRemoveUsers(Calculator receiver) {
		super("Remover Utilizadores", receiver);
		addStringField("name", "Insira o nome do(s) Utilizador(es) a remover: ");
	}

	@Override
	protected final void execute() throws CommandException{
		String name = stringField("name");
		List<User> l = _receiver.getUsers();
		List<User> removed = new ArrayList<User>();
		ArrayList<User> newList = new ArrayList<User>();
		
		for(User u: l){
			if(u.getName().contains(name)){
				removed.add(new User(u.getName()));
			}
			else
				newList.add(new User(u.getName()));
		}
		_receiver.setUsers(newList);
		_display.addLine("" + removed.size());
		for (User user : removed) {
			_display.addLine(user.getName());
		}
		_display.display();
	}
}
