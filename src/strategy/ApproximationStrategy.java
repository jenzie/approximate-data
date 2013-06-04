package strategy;

import composite.XMLComponent;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

public interface ApproximationStrategy {
	public int getCount();

	public XMLComponent approximate(
			XMLComponent root, String find, String replace);
}
