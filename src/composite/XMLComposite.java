package composite;

/**
 *
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

/**
 * XMLComposite
 * A XML node that may have children, and has both an opening and closing tag.
 */
public class XMLComposite extends XMLComponent {
	/**
	 * Constructor; closing tag is set based on tag from input.
	 *
	 * @param tag XML opening tag.
	 * @param text text in the input file that is not XML.
	 * @param parent parent of this node.
	 */
	public XMLComposite(String tag, String text, XMLComponent parent) {
		super(tag, text, parent);
		super.closeTag = "</" + tag.substring(1, tag.length());
	}

	/**
	 * Getter for opening tag value.
	 *
	 * @return the opening XML tag value associated with this node.
	 */
	public String getOpenTag() {
		return super.getOpenTag();
	}

	/**
	 * Getter for closing tag value.
	 *
	 * @return the closing XML tag value associated with this node.
	 */
	public String getCloseTag() {
		return super.getCloseTag();
	}

	/**
	 * Checks to see if tag was properly closed.
	 *
	 * @return true is tag was closed; false, otherwise.
	 */
	public boolean isClosed() {
		return this.isClosed;
	}

	/**
	 * Sets the boolean value to true if tag was properly closed.
	 *
	 * @return true if tag was able to be closed.
	 */
	public boolean setClosed(String givenCloseTag) {
		return super.setClosed(givenCloseTag);
	}

	/**
	 * Attempts to add a child to the parent node.
	 *
	 * @param child node to add to parent node in tree structure.
	 * @return true if child was added successfully.
	 */
	public boolean addChild(XMLComponent child) {
		super.children.add(child);
		return true;
	}

	/**
	 * @return textual representation of the node; output.
	 */
	public String printText() {
		//System.out.println("comp tag: " + this.openTag + " closed: " + this.isClosed());
		String result = this.getOpenTag();

		if(super.text != null)
			result += super.text;
		for(XMLComponent node : children)
			result += node.printText();
		if(this.isClosed() &&
				(this.getCloseTag().length() < 20 &&
				!this.getCloseTag().equals("<//unit>"))) {

			System.out.flush(); // okay
			// print exceptions for closing tags
			if(getOpenTag().equals("<comment type=\"block\">"))
				result += "</comment>";
			else
				result += this.getCloseTag();
		}
		return result + "\r\n";
	}

	/**
	 * @return the text associated with the node.
	 */
	public String getText() {
		return super.getText();
	}

	/**
	 * Sets the text associated with the node.
	 *
	 * @param replace the text to replace the original text.
	 */
	public void setText(String replace) {
		super.setText(replace);
	}
}
