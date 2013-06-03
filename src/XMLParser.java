import composite.XMLComponent;
import composite.XMLComposite;
import composite.XMLLeaf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

public class XMLParser {
	private static final String FILE_EXTENSION = ".xml";
	private Scanner input;
	private XMLComponent root, current;

	public XMLParser(String XMLFile) {
		try {
			input = new Scanner(new File(XMLFile));
		} catch (FileNotFoundException e) {
			System.err.println("Usage: java XMLParser.java FILENAME.xml");
			System.exit(0);
		}
		run();
	}

	private void run() {
		String[] line;
		int lineNumber = 1;

		while(input.hasNext()) {


			String inputLine = input.nextLine();
			System.out.println(inputLine);



			line = inputLine.split("<");


			int ind = 0;
			System.out.println(lineNumber + "...");
			for(String el : line) {
				System.out.println(ind + ":" + el + ":");
				ind++;
			}


			parseLine(line, lineNumber);
			lineNumber++;
		}
		//System.out.println(root.getText());
	}

	private void parseLine(String[] line, int lineNumber) {
		String tempPiece, tempTag, tempText;
		int tempIndex; // end index
		XMLComponent newNode;

		// parsing the first 2 lines of an XML file for text declaration
		if(lineNumber == 0 || lineNumber == 1)
			parseSpecialLine(line[1], lineNumber);

		// store leading whitespace/characters from first element as leaf
		newNode = new XMLLeaf(null, line[0], current);
		//System.out.println(newNode.getText());

		// for each tag after the leading whitespace/braces
		for(int i = 1; i < line.length - 1; i++) {
			// check if tag is empty; should never happen.
			if(line[i].length() == 0) {
				System.err.println(
					"Error: Empty tag; this should never happen.");
				continue;
			}

			// get the tag and information
			tempPiece = line[i]; //.trim()
			tempIndex = tempPiece.indexOf(">");
			tempTag = "<" + tempPiece.substring(0, tempIndex + 1);
			tempText = tempPiece.substring(tempIndex + 1);

			//System.out.println(tempTag + lineNumber + "..." + i);


			// check if opening tag
			if(!(tempTag.charAt(1) == '/')) {
				System.out.println("going through 1");
				newNode = new XMLComposite(tempTag, tempText, current);

				if(root == null)
					root = newNode;
				current = newNode;
			}

			// check if closing tag
			else if(tempTag.charAt(1) == '/') {
				System.out.println("going through 2");
				if(current.getCloseTag().equals(tempTag)) {
					System.out.println("going through 3");
					if(!current.setClosed(tempTag)) {
						System.out.println("going through 4");
						System.err.println(
							"Error: Invalid XML tag on line: " + lineNumber);
						System.exit(0);
					}
					current = current.parent;
				}
			} // end else-if
			//System.out.println(current.getText());
		} // end for-loop
	}

	public static void main(String[] args) {
		String fileExtension;
		boolean status = false;

		if(args.length != 0) {
			fileExtension =
				args[0].substring(args[0].
						length() - FILE_EXTENSION.length());
			if(fileExtension.equals(FILE_EXTENSION))
				status = true;
		}

		if(!status) {
			System.err.println("Usage: java XMLParser.java FILENAME.xml");
			System.exit(0);
		}

		new XMLParser(args[0]);
	}

	private void parseSpecialLine(String tag, int lineNumber) {
		XMLComponent newNode;

		if(lineNumber == 0) {
			root = new XMLComposite("<" + tag, null, null);
			root.setClosed("</" + tag);
			current = root;
		} else if(lineNumber == 1) {
			newNode = new XMLComposite("<" + tag, null, current);
			newNode.setClosed("</" + tag);
			current = newNode;
		}
	}
}
