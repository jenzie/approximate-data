package composite;

import java.util.ArrayList;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

/**
 * XMLComponent
 * Provides an interface that XML nodes may use or override if
 * extending from this class (particularly, XML Composite and XMLLeaf).
 */
public abstract class XMLComponent {
	protected String openTag, closeTag, text;
	protected boolean isClosed;
	public XMLComponent parent;
	public ArrayList<XMLComponent> children = new ArrayList<XMLComponent>();

	/**
	 * Constructor; closing tag is set based on tag from input.
	 *
	 * @param tag XML opening tag.
	 * @param text text in the input file that is not XML.
	 * @param parent parent of this node.
	 */
	public XMLComponent(String tag, String text, XMLComponent parent) {
		this.openTag = tag;
		this.closeTag = tag;
		this.isClosed = false;
		this.text = text;
		this.parent = parent;
	}

	/**
	 * Getter for opening tag value.
	 *
	 * @return the opening XML tag value associated with this node.
	 */
	public String getOpenTag() {
		return this.openTag;
	}

	/**
	 * Getter for closing tag value.
	 *
	 * @return the closing XML tag value associated with this node.
	 */
	public String getCloseTag() {
		return this.closeTag;
	}

	/**
	 * Checks to see if tag was properly closed.
	 *
	 * @return true if tag was closed; false, otherwise.
	 */
	public abstract boolean isClosed();

	/**
	 * Sets the boolean value to true if tag was properly closed.
	 *
	 * @return true if tag was able to be closed.
	 */
	public boolean setClosed(String givenCloseTag) {
		if(this.isClosed())
			return false; // tag was already closed
		else if(this.closeTag.equals(givenCloseTag)) {
			this.isClosed = true;
			return true; // close this tag
		}
		return false; // givenCloseTag is invalid
	}

	/**
	 * Attempts to add a child to this parent node, if and only if, the child
	 * node is a valid XML tag. Otherwise, ignore attempt to add.
	 *
	 * @param child node to add to parent node in tree structure.
	 * @return true if child was added successfully.
	 */
	public abstract boolean addChild(XMLComponent child);

	/**
	 * @return textual representation of the node; output.
	 */
	public abstract String printText();

	/**
	 * @return the text associated with the node.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text associated with the node.
	 *
	 * @param replace the text to replace the original text.
	 */
	public void setText(String replace) {
		this.text = replace;
	}
}
