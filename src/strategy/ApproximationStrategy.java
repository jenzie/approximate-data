package strategy;

import composite.XMLComponent;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

/**
 * ApproximationStrategy
 * Interface for the strategy design pattern.
 */
public interface ApproximationStrategy {
	/**
	 * @return the number of variables changed using the strategy.
	 */
	public int getCount();

	/**
	 * Perform approximation based on the strategy's specific algorithm.
	 * @param node current node to approximate.
	 * @param find data type to find
	 * @param replace data type to replace the original/find with
	 * @return the root node of the modified tree, which is necessary for
	 * 			comparing results of multiple strategies.
	 */
	public XMLComponent approximate(
			XMLComponent node, String find, String replace);
}
