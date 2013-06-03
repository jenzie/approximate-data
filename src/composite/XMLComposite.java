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
		return this.isClosed();
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
	public String getText() {
		String result = this.getOpenTag();
		for(XMLComponent node : children)
			result += node.getText();
		result += this.getCloseTag();
		return result;
	}
}
