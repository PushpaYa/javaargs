package com.cleancoder.args;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

//Class for parsing the arguments
public class Args {
  private Map<Character, ArgumentMarshaler> marshalers;	//Map to store the arguments and its type
  private Set<Character> argsFound;						//Set is a Collection that cannot contain duplicate elements
  private ListIterator<String> currentArgument;			//To store the list of arguments

  //Constructor for argument class
  public Args(String schema, String[] args) throws ArgsException {
    marshalers = new HashMap<Character, ArgumentMarshaler>();
    argsFound = new HashSet<Character>();

    parseSchema(schema);	//Function call to parse the schema
    parseArgumentStrings(Arrays.asList(args));	//Function call to parse the arguments
  }

  //Function for splitting the schema with ','
  private void parseSchema(String schema) throws ArgsException {
    for (String element : schema.split(","))	//Splitting the schemas 
      if (element.length() > 0)
        parseSchemaElement(element.trim());
  }

  //Function to classify the argument types and store into a map.
  private void parseSchemaElement(String element) throws ArgsException {
    char elementId = element.charAt(0);
    String elementTail = element.substring(1);
    validateSchemaElementId(elementId);
    if (elementTail.length() == 0)								 //For checking if argument is of type boolean
      marshalers.put(elementId, new BooleanArgumentMarshaler()); //Store boolean type argument in map
    else if (elementTail.equals("*"))
      marshalers.put(elementId, new StringArgumentMarshaler());  //Store string type argument in map
    else if (elementTail.equals("#"))
      marshalers.put(elementId, new IntegerArgumentMarshaler()); //Store integer type argument in map
    else if (elementTail.equals("##"))
      marshalers.put(elementId, new DoubleArgumentMarshaler()); //Store double type argument in map
    else if (elementTail.equals("[*]"))
      marshalers.put(elementId, new StringArrayArgumentMarshaler()); //Store string array type argument in map
    else if (elementTail.equals("&"))
      marshalers.put(elementId, new MapArgumentMarshaler()); //Store map type argument in map
    else
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
  }

  //Function to schema type which is specified by first character 
  private void validateSchemaElementId(char elementId) throws ArgsException {
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
  }

  //Function to parse the string argument
  private void parseArgumentStrings(List<String> argsList) throws ArgsException {
    for (currentArgument = argsList.listIterator(); currentArgument.hasNext();) {
      String argString = currentArgument.next();
      if (argString.startsWith("-")) { //To check if argument starts with '-'
        parseArgumentCharacters(argString.substring(1));
      } else {
        currentArgument.previous();
        break;
      }
    }
  }

  //Function to parse all the characters of single argument
  private void parseArgumentCharacters(String argChars) throws ArgsException {
    for (int i = 0; i < argChars.length(); i++)
      parseArgumentCharacter(argChars.charAt(i));
  }

  //Function to parse single character of single argument
  private void parseArgumentCharacter(char argChar) throws ArgsException {
    ArgumentMarshaler m = marshalers.get(argChar);
    if (m == null) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    } else {
      argsFound.add(argChar);
      try {
        m.set(currentArgument);
      } catch (ArgsException e) {
        e.setErrorArgumentId(argChar);
        throw e;
      }
    }
  }

  //Function to check if the argument is provided or not
  public boolean has(char arg) {
    return argsFound.contains(arg);
  }

  //Function to get the next argument while parsing
  public int nextArgument() {
    return currentArgument.nextIndex();
  }

  //Function to get the boolean type argument option provided to the program
  public boolean getBoolean(char arg) {
    return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
  }

  //Function to get the string type argument option provided to the program
  public String getString(char arg) {
    return StringArgumentMarshaler.getValue(marshalers.get(arg));
  }

  //Function to get the integer type argument option provided to the program
  public int getInt(char arg) {
    return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
  }

  //Function to get the double type argument option provided to the program
  public double getDouble(char arg) {
    return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
  }

  //Function to get the string array type argument option provided to the program
  public String[] getStringArray(char arg) {
    return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
  }

  //Function to get the list of arguments provided to the program
  public Map<String, String> getMap(char arg) {
    return MapArgumentMarshaler.getValue(marshalers.get(arg));
  }
  
  //Function to get all the arguments passed to program  <Character, ArgumentMarshaler> marshalers;
  public void getAllParameters() {
	  for (Map.Entry<Character,ArgumentMarshaler> entry : marshalers.entrySet()) {
		  if (entry.getValue().toString().contains("BooleanArgumentMarshaler"))
			  System.out.println("Argument = " + entry.getKey() + ", Value = " + getBoolean(entry.getKey()));
		  else if (entry.getValue().toString().contains("IntegerArgumentMarshaler"))
			  System.out.println("Argument = " + entry.getKey() + ", Value = " + getInt(entry.getKey()));
		  else if (entry.getValue().toString().contains("DoubleArgumentMarshaler"))
			  System.out.println("Argument = " + entry.getKey() + ", Value = " + getDouble(entry.getKey()));
		  else if (entry.getValue().toString().contains("StringArgumentMarshaler"))
			  System.out.println("Argument = " + entry.getKey() + ", Value = " + getString(entry.getKey()));
		  else if (entry.getValue().toString().contains("StringArrayArgumentMarshaler"))
			  System.out.println("Argument = " + entry.getKey() + ", Value = " + Arrays.toString(getStringArray(entry.getKey())));
		  else
			  System.out.println("No handler written to parse this parameter"); 
			  
	  }
          

  }
  
}
