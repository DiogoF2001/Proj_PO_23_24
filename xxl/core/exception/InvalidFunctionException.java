package xxl.core.exception;

/**
 * Exception for invalid functions.
 */
public class InvalidFunctionException extends Exception {
  /** Invalid function name. */
  private String functionName;
  
  /**
   * @param functionName the invalid function name causing the exception
   */
  public InvalidFunctionException(String functionName) {
    super("Invalid function name: " + functionName);
    this.functionName = functionName;
  }
  
  /**
   * @return the invalid function name.
   */
  public String getFunctionName() {
    return functionName;
  }
}

