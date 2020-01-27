package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

public class ArgsException extends Exception {
  private char errorArgumentId = '\0';	//Default value for error argument ID
  private String errorParameter = null; //Default value for error parameter
  private ErrorCode errorCode = OK;		//Default value for error code

  public ArgsException() {}

  //Constructor with string type parameters
  public ArgsException(String message) {super(message);}

  //Constructor with error code type parameters
  public ArgsException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  //Constructor with error code and string type parameters
  public ArgsException(ErrorCode errorCode, String errorParameter) {
    this.errorCode = errorCode;
    this.errorParameter = errorParameter;
  }

  //Constructor with error code, error argument ID and string type parameters
  public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
    this.errorCode = errorCode;
    this.errorParameter = errorParameter;
    this.errorArgumentId = errorArgumentId;
  }

  //Function to get the error argument ID
  public char getErrorArgumentId() {
    return errorArgumentId;
  }

  //Function to set the error argument ID
  public void setErrorArgumentId(char errorArgumentId) {
    this.errorArgumentId = errorArgumentId;
  }

  //Function to get the error parameter
  public String getErrorParameter() {
    return errorParameter;
  }

  //Function to set the error parameter
  public void setErrorParameter(String errorParameter) {
    this.errorParameter = errorParameter;
  }

  //Function to get the error code
  public ErrorCode getErrorCode() {
    return errorCode;
  }

  //Function to set the error codes
  public void setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  //List of error messages
  public String errorMessage() {
    switch (errorCode) {
      case OK:
        return "TILT: Should not get here.";
      case UNEXPECTED_ARGUMENT:
        return String.format("Argument -%c unexpected.", errorArgumentId);
      case MISSING_STRING:
        return String.format("Could not find string parameter for -%c.", errorArgumentId);
      case INVALID_INTEGER:
        return String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter);
      case MISSING_INTEGER:
        return String.format("Could not find integer parameter for -%c.", errorArgumentId);
      case INVALID_DOUBLE:
        return String.format("Argument -%c expects a double but was '%s'.", errorArgumentId, errorParameter);
      case MISSING_DOUBLE:
        return String.format("Could not find double parameter for -%c.", errorArgumentId);
      case INVALID_ARGUMENT_NAME:
        return String.format("'%c' is not a valid argument name.", errorArgumentId);
      case INVALID_ARGUMENT_FORMAT:
        return String.format("'%s' is not a valid argument format.", errorParameter);
      case MISSING_MAP:
        return String.format("Could not find map string for -%c.", errorArgumentId);
      case MALFORMED_MAP:
        return String.format("Map string for -%c is not of form k1:v1,k2:v2...", errorArgumentId);
    }
    return "";
  }

  //List of error codes
  public enum ErrorCode {
    OK, INVALID_ARGUMENT_FORMAT, UNEXPECTED_ARGUMENT, INVALID_ARGUMENT_NAME,
    MISSING_STRING,
    MISSING_INTEGER, INVALID_INTEGER,
    MISSING_DOUBLE, MALFORMED_MAP, MISSING_MAP, INVALID_DOUBLE}
}
