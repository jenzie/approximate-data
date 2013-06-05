import composite.XMLComponent;
import composite.XMLComposite;
import composite.XMLLeaf;
import strategy.ApproximationStrategy;
import strategy.LoopStrategy;
import strategy.NaiveStrategy;
import strategy.RandomStrategy;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 *
 * TODO: Add rules for indentation.
 * TODO: Fix bug for closing tags.
 * TODO: Implement random and loop strategies.
 */

public class XMLParser {
	private static final String FILE_EXTENSION = ".xml";
	private String XMLFile;
	private Scanner input;
	private PrintWriter out;
	private XMLComponent root, current;

	public XMLParser(String XMLFile) throws InterruptedException {
		this.XMLFile = XMLFile;

		try {
			input = new Scanner(new File("input", XMLFile)); // directory, file
		} catch (FileNotFoundException e) {
			System.err.println("Usage: java XMLParser.java FILENAME.xml");
			System.exit(0);
		}
		run();
	}

	public static void main(String[] args) throws InterruptedException {
		String fileExtension;
		boolean status = false;

		if(args.length != 0) {
			fileExtension =
				args[0].substring(args[0].length() - FILE_EXTENSION.length());
			if(fileExtension.equals(FILE_EXTENSION))
				status = true;
		}

		if(!status) {
			System.err.println("Usage: java XMLParser.java FILENAME.xml");
			System.exit(0);
		}

		new XMLParser(args[0]);
	}

	private void run() throws InterruptedException {
		String[] line;
		int lineNumber = 1;
		XMLComponent[] results;

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

		// get results, pausing between file writes
		results = performApproximation("double", "float");
		System.out.println("\nWriting results to output directory.");

		Thread.sleep(1000);
		getOutFile(results[0]);
		Thread.sleep(1000);
		getOutFile(results[1]);
		Thread.sleep(1000);
		getOutFile(results[2]);

		System.out.println("Writing complete.");
	}

	private void parseLine(String[] line, int lineNumber) {
		String tempPiece, tempTag, tempText;
		int tempIndex; // end index
		XMLComponent newNode, tempNode;

		// parsing the first 2 lines of an XML file for text declaration
		if(lineNumber == 1 || lineNumber == 2)
			parseSpecialLine(line[1], lineNumber);

		// store leading whitespace/characters from first element as leaf
		newNode = new XMLLeaf(null, line[0], current);
		current.addChild(newNode);

		// for each tag after the leading whitespace/braces
		for(int i = 1; i < line.length; i++) {
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
				// check if current tag can be closed if wasn't already closed

				if(tempTag.equals("</comment>"))
					tempTag = "</comment type=\"block\">";

				if(current.getCloseTag().equals(tempTag)) {
					if(!current.setClosed(tempTag)) {
						System.err.println(
							"Error: Invalid XML tag on line: " + lineNumber);
						System.exit(0);
					}
					current = current.parent;
				}

				// check if parent tags can be closed since current was closed
				else if(current.isClosed()) {
					System.out.println("going 1");
					tempNode = current;

					while(tempNode.isClosed())
						tempNode = tempNode.parent;
					System.out.println("going 2");

					if(!tempNode.isClosed() &&
						tempNode.getCloseTag().equals(tempTag)) {
						System.out.println("going 3");
						if(!tempNode.setClosed(tempTag)) {
							System.err.println(
								"Error: Invalid XML tag on line: " +lineNumber);
							System.exit(0);
						} // end if
					} // end if
				} // end else-if
			} // end else-if
		} // end for-loop
	}// end function

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

	/**
	 * Context of the strategy design pattern.
	 * @param find data type to find
	 * @param replace data type to replace 'find'
	 */
	private XMLComponent[] performApproximation(String find, String replace) {
		String resultA, resultB, resultC;
		int countA, countB, countC;
		XMLComponent rootA, rootB, rootC;
		XMLComponent[] roots = new XMLComponent[3];

		// create all strategy objects
		ApproximationStrategy naive = new NaiveStrategy(); // Strategy A
		ApproximationStrategy random = new RandomStrategy(); // Strategy B
		ApproximationStrategy loop = new LoopStrategy(); // Strategy B

		// perform strategy A
		rootA = naive.approximate(this.root, find, replace);
		countA = naive.getCount();
		resultA = "There were " + countA + " variables " +
			"changed from " + find + " to " + replace + ".";
		System.out.println("Strategy A: " + resultA);
		// System.out.println(rootA.printText());

		// perform strategy B
		rootB = random.approximate(this.root, find, replace);
		countB = random.getCount();
		resultB = "There were " + countB + " variables " +
				"changed from " + find + " to " + replace + ".";
		System.out.println("Strategy B: " + resultB);
		//System.out.println(rootB.printText());

		// perform strategy C
		rootC = loop.approximate(this.root, find, replace);
		countC = loop.getCount();
		resultC = "There were " + countB + " variables " +
				"changed from " + find + " to " + replace + ".";
		System.out.println("Strategy C: " + resultC);
		//System.out.println(rootC.printText());

		// store new roots to return
		roots[0] = rootA; roots[1] = rootB; roots[2] = rootC;
		return roots;
	}

	/**
	 * Get the file output .xml file.
	 */
	private void getOutFile(XMLComponent root) {
		// produce unique file names
		String filename =
			XMLFile.substring(0, XMLFile.length() - FILE_EXTENSION.length());
		SimpleDateFormat dateFormat =
			new SimpleDateFormat("MMddHHmmss");
		filename += "_" + dateFormat.format(new Date()) + FILE_EXTENSION;

		try {
			out = new PrintWriter(new FileWriter(new File("output", filename)));
		} catch (IOException ioe) {
			System.err.println("IOException: " +
				"Could not create print writer for /results/" + XMLFile);
		}
		out.write(root.printText());
		out.flush();
		out.close();
	}
}
