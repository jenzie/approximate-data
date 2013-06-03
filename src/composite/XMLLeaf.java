package composite;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

/**
 * XMLLead
 * A XML node that cannot have children, and does not need a closing tag.
 */
public class XMLLeaf extends XMLComponent {
	/**
	 * Constructor.
	 *
	 * @param tag XML opening tag.
	 * @param text text in the input file that is not XML.
	 * @param parent parent of this node.
	 */
	public XMLLeaf(String tag, String text, XMLComponent parent) {
		super(tag, text, parent);
		super.isClosed = true;
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
	 * Checks to see if tag was properly closed.
	 *
	 * @return always returns true.
	 */
	public boolean isClosed() {
		return this.isClosed();
	}

	/**
	 * Sets the boolean value to true if tag was properly closed.
	 *
	 * @return true if tag was able to be closed; always false.
	 */
	public boolean setClosed(String givenCloseTag) {
		return super.setClosed(givenCloseTag);
	}

	/**
	 * Unable to add children to leaf nodes.
	 *
	 * @param node child node to add.
	 * @return false, always.
	 */
	public boolean addChild(XMLComponent node) {
		return false;
	}

	/**
	 * @return textual representation of the node; output.
	 */
	public String getText() {
		return this.getOpenTag() + super.text;
	}
}
