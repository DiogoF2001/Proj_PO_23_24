package xxl.app.exception;

import pt.tecnico.uilib.menus.CommandException;

public class InvalidContentException extends CommandException{
	public InvalidContentException(String s){
		super(s);
	}

	public InvalidContentException(String s, Exception e){
		super(s,e);
	}
}
