import composite.XMLComponent;
import composite.XMLComposite;
import composite.XMLLeaf;
import strategy.ApproximationStrategy;
import strategy.LoopStrategy;
import strategy.NaiveStrategy;
import strategy.RandomStrategy;

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
	private String XMLFile;
	private Scanner input;
	private XMLComponent root, current;

	public XMLParser(String XMLFile) {
		this.XMLFile = XMLFile;

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
			line = input.nextLine().split("<");

			/**
			int ind = 0;
			System.out.println(lineNumber + "...");
			for(String el : line) {
				System.out.println(ind + ":" + el + ":");
				ind++;
			}*/


			parseLine(line, lineNumber);
			lineNumber++;
		}
		parseSpecialLine("/unit>", lineNumber);
		//System.out.println(root.printText());
		//System.out.println("root's child: " + root.children.get(0).printText());
		//System.out.println("current: " + current.printText());
		performApproximation("double", "float");
	}

	private void parseLine(String[] line, int lineNumber) {
		String tempPiece, tempTag, tempText;
		int tempIndex; // end index
		XMLComponent newNode;

		// parsing the first 2 lines of an XML file for text declaration
		if(lineNumber == 1 || lineNumber == 2)
			parseSpecialLine(line[1], lineNumber);

		// store leading whitespace/characters from first element as leaf
		newNode = new XMLLeaf(null, line[0], current);
		current.addChild(newNode);

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
				newNode = new XMLComposite(tempTag, tempText, current);

				// should never happen
				if(root == null) {
					System.err.println(
						"Error: Root should never be null from here.");
					root = newNode;
				}

				current.addChild(newNode);
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
					current = current.parent;
				}
			} // end else-if
			//System.out.println(current.printText());
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

		if(lineNumber == 1) {
			root = new XMLComposite("<" + tag, null, null);
			root.setClosed("</" + tag);
			current = root;
		} else if(lineNumber == 2) {
			newNode = new XMLComposite("<" + tag, null, root);
			newNode.setClosed("</" + tag);
			current.addChild(newNode);
			current = newNode;
		} else {
			newNode = new XMLComposite("<" + tag, null, current);
			newNode.setClosed("</" + tag);
			current.addChild(newNode);
			current = newNode;
		}
	}

	private String performApproximation(String find, String replace) {
		String resultA, resultB, resultC;
		int countA, countB, countC;
		XMLComponent rootA, rootB, rootC;

		// create all strategy objects
		ApproximationStrategy naive = new NaiveStrategy(); // Strategy A
		ApproximationStrategy random = new RandomStrategy(); // Strategy B
		ApproximationStrategy loop = new LoopStrategy(); // Strategy B

		// perform strategy A
		rootA = naive.approximate(this.root, find, replace);
		countA = naive.getCount();
		resultA = "There were " + countA + " variables " +
			"changed from " + find + " to " + replace + ".";
		System.out.println(resultA);
		System.out.println(rootA.printText());

		// perform strategy B

		// perform strategy C
		return "";
	}
}
