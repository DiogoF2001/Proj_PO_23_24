package xxl.core;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

import xxl.app.exception.InvalidCellRangeException;
import xxl.core.content.*;
import xxl.core.content.functions.*;
import xxl.core.exception.*;

public class Parser {

  private Spreadsheet _spreadsheet;
  
  public Parser() {
  }

  public Parser(Spreadsheet spreadsheet) {
    _spreadsheet = spreadsheet;
  }

  Spreadsheet parseFile(String filename) throws IOException, UnrecognizedEntryException, InvalidFunctionException, InvalidCellRangeException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      parseDimensions(reader);

      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }

    return _spreadsheet;
  }

  private void parseDimensions(BufferedReader reader) throws IOException, UnrecognizedEntryException, InvalidCellRangeException {
    int rows = -1;
    int columns = -1;
    
    for (int i = 0; i < 2; i++) {
		String line = reader.readLine();
      String[] dimension = line.split("=");
      if (dimension[0].equals("linhas"))
        rows = Integer.parseInt(dimension[1]);
      else if (dimension[0].equals("colunas"))
        columns = Integer.parseInt(dimension[1]);
      else
        throw new UnrecognizedEntryException("A seguinte linha não tem o formato esperado: " + line);
    }

	if(rows <= 2*columns)
		throw new InvalidCellRangeException("" + rows + ";" + columns);

    if (rows <= 0 || columns <= 0)
      throw new UnrecognizedEntryException("Dimensões inválidas para a folha");

    _spreadsheet = new Spreadsheet(rows, columns, new User("root"));
  }

  private void parseLine(String line) throws UnrecognizedEntryException, InvalidFunctionException /*, more exceptions? */{
    String[] components = line.split("\\|");

    if (components.length == 1) // do nothing
      return;
    
    if (components.length == 2) {
      String[] address = components[0].split(";");
      Content content = parseContent(components[1]);
      _spreadsheet.insertContent(Integer.parseInt(address[0]) - 1, Integer.parseInt(address[1]) - 1, content);
    } else
      throw new UnrecognizedEntryException("Wrong format in line: " + line);
  }

  // parse the begining of an expression
  public Content parseContent(String contentSpecification) throws UnrecognizedEntryException, InvalidFunctionException{
    char c = contentSpecification.charAt(0);

    if (c == '=')
      return parseContentExpression(contentSpecification.substring(1));
    else
      return parseLiteral(contentSpecification);
  }

  private Literal parseLiteral(String literalExpression) throws UnrecognizedEntryException {
    if (literalExpression.charAt(0) == '\'')
      return new LiteralString(literalExpression.substring(1));
    else {
      try {
        int val = Integer.parseInt(literalExpression);
        return new LiteralInteger(val);
      } catch (NumberFormatException nfe) {
        throw new UnrecognizedEntryException("Número inválido: " + literalExpression);
      }
    }
  }

  // contentSpecification is what comes after '='
  private Content parseContentExpression(String contentSpecification) throws UnrecognizedEntryException, InvalidFunctionException /*more exceptions */ {
    if (contentSpecification.contains("("))
      return parseFunction(contentSpecification);
    // It is a reference
    String[] address = contentSpecification.split(";");
    return new Reference(Integer.parseInt(address[0].trim()), Integer.parseInt(address[1]), _spreadsheet);
  }

  	private Content parseFunction(String functionSpecification) throws UnrecognizedEntryException, InvalidFunctionException /*more exceptions */ {
		String[] components = functionSpecification.split("[()]");

		if (components[1].contains(","))
			return parseBinaryFunction(components[0], components[1]);
		else
			return parseIntervalFunction(components[0], components[1]);
		
  	}

  private Content parseBinaryFunction(String functionName, String args) throws InvalidFunctionException, UnrecognizedEntryException {
    String[] arguments = args.split(",");
    Content arg0 = parseArgumentExpression(arguments[0]);
    Content arg1 = parseArgumentExpression(arguments[1]);
	Content ret;
    
    switch (functionName) {
      case "ADD": 
	  	ret = new Add(arg0, arg1);
		break;
      case "SUB":
	  	ret = new Sub(arg0, arg1);
		break;
      case "MUL":
	  	ret = new Mul(arg0, arg1);
		break;
      case "DIV":
	  	ret = new Div(arg0, arg1);
		break;
      default:
	  	throw new InvalidFunctionException(functionName);
    };
	return ret;
  }

  private Content parseArgumentExpression(String argExpression) throws UnrecognizedEntryException {
    if (argExpression.contains(";")  && argExpression.charAt(0) != '\'') {
      String[] address = argExpression.split(";");
      return new ReferenceAsArgument(Integer.parseInt(address[0].trim()), Integer.parseInt(address[1]), _spreadsheet);
    } 
	else
      return parseLiteral(argExpression);
  }

  private Content parseIntervalFunction(String functionName, String rangeDescription) throws UnrecognizedEntryException, InvalidFunctionException {
    Range range;
	Content ret;
	try{
		range = createRange(rangeDescription);
	} catch(InvalidRangeFormatException | InvalidCoordinatesException e){
		throw new UnrecognizedEntryException(rangeDescription, e);
	}
    
	switch (functionName) {
      case "CONCAT":
	  	ret = new Concat(range);
		break;
      case "COALESCE":
	  	ret = new Coalesce(range);
		break;
	  case "PRODUCT":
	  	ret = new Product(range);
		break;
      case "AVERAGE":
	  	ret = new Average(range);
		break;
      default:
	  	throw new InvalidFunctionException(functionName);
    };

	return ret;
  }

  /* Na classe Spreadsheet preciso de algo com a seguinte funcionalidade*/
  public Range createRange(String range) throws InvalidCoordinatesException, InvalidRangeFormatException {
    String[] rangeCoordinates;
    int firstRow, firstColumn, lastRow, lastColumn;
    try {
		if (range.indexOf(':') != -1) {
			rangeCoordinates = range.split("[:;]");
			firstRow = Integer.parseInt(rangeCoordinates[0]) - 1;
			firstColumn = Integer.parseInt(rangeCoordinates[1]) - 1;
			lastRow = Integer.parseInt(rangeCoordinates[2]) - 1;
			lastColumn = Integer.parseInt(rangeCoordinates[3]) - 1;
		} 
		else {
			rangeCoordinates = range.split(";");
			firstRow = lastRow = Integer.parseInt(rangeCoordinates[0]) - 1;
			firstColumn = lastColumn = Integer.parseInt(rangeCoordinates[1]) - 1;
		}
	} 
	catch (NumberFormatException e) {
		throw new InvalidRangeFormatException("Invalid range format: " + range);
	}

    // check if coordinates are valid
    // if yes
	if(_spreadsheet.checkCoords(lastRow, lastColumn) && _spreadsheet.checkCoords(firstRow, firstColumn))
		return new Range(firstRow, firstColumn, lastRow, lastColumn, _spreadsheet);
	else
		throw new InvalidCoordinatesException(""+range);
  }
  
}