package strategy;

import composite.XMLComponent;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

/**
 * NaiveStrategy
 * Concrete strategy for strategy design pattern.
 * Changes all variables of type 'find' to type 'replace'.
 */
public class NaiveStrategy implements ApproximationStrategy {
	private int count;

	@Override
	/**
	 * @return the number of variables changed using the strategy.
	 */
	public int getCount() {
		return this.count;
	}

	@Override
	/**
	 * Performs the approximation based on the strategy to replace all
	 * existing variables.
	 *
	 * @param node current node to approximate.
	 * @param find data type to find
	 * @param replace data type to replace the original/find with
	 * @return the root node of the modified tree, which is necessary for
	 * 			comparing results of multiple strategies.
	 */
	public XMLComponent approximate(
			XMLComponent node, String find, String replace) {

		// ignore case and whitespace
		find = find.toLowerCase().trim();
		replace = replace.toLowerCase().trim();

		// reset the count at the beginning of approximation
		if(node.parent == null)
			this.count = 0;

		// if the current node has replaceable text
		if(node.getText() != null) {
			if(node.getText().toLowerCase().equals(find)) {
				node.setText(replace);
				this.count++;
			}
		}

		// check the current node's children
		for(XMLComponent child : node.children)
			this.approximate(child, find, replace);

		// return the root at the end of the first (final) iteration
		return node;
	}
}
