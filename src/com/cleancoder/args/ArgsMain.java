package com.cleancoder.args;

public class ArgsMain {
	
  public static void main(String[] args) {
	  if(args[0].equals("-h")) {
	  helpFunction();
	  return;
	  }
  
	try {
	  Args arg = new Args("f,p#,s*,n#,a##,d[*]", args);
	  arg.getAllParameters();
	} catch (ArgsException e) {
	  System.out.printf("Argument error : %s\n", e.errorMessage());
	}
  }

  //Function to print the help content when program is run with only -h as argument
  private static void helpFunction() {
	  System.out.printf("Following are the options : \n");
	  System.out.printf("Schema : \n - char    - Boolean arg.\n - char*   - String arg.\n - char#   - Integer arg.\n - char##  - double arg.\n - char[*] - one element of a string array.\n\n");
	  System.out.printf("Example schema : (f,p#,s*,n#,a##,d[*])\n\n");
	  System.out.printf("Corresponding command line: -f -s Bob -n 1 -a 3.2 -p 22 -d /home/users/\n"); 
  }
}
