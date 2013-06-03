import composite.XMLComponent;
import composite.XMLComposite;

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
	private Scanner input;
	private XMLComponent root, current;

	public XMLParser(String XMLFile) {
		try {
			input = new Scanner(new File(XMLFile));
		} catch (FileNotFoundException e) {
			System.err.println("Usage: java XMLParser.java INPUT_FILENAME");
			System.exit(0);
		}
		run();
	}

	private void run() {
		String[] line;
		int lineNumber = 1;

		while(input.hasNext()) {
			line = input.nextLine().split("<");
			parseLine(line, lineNumber);
			lineNumber++;
		}
	}

	private void parseLine(String[] line, int lineNumber) {
		String tempPiece, tempTag, tempText;
		int tempIndex; // end index
		XMLComponent newNode;

		// for each tag
		for(int i = 0; i < line.length - 1; i++) {
			// get the tag and information
			tempPiece = line[1];
			tempIndex = tempPiece.indexOf(">");
			tempTag = "<" + tempPiece.substring(0, tempIndex + 1);
			tempText = tempPiece.substring(tempIndex + 1);

			// check if opening tag
			if(!(tempTag.charAt(1) == '/')) {
				newNode = new XMLComposite(tempTag, tempText, current);

				if(root == null)
					root = newNode;
				current = newNode;
			}

			// check if closing tag
			else if(tempTag.charAt(1) == '/') {
				if(current.getCloseTag().equals(tempTag)) {
					if(!current.setClosed(tempTag)) {
						System.err.println(
							"Error: Invalid XML tag on line: " + lineNumber);
						System.exit(0);
					}
				}
			} // end else-if
		} // end for-loop
	}
}
