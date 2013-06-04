package strategy;

import composite.XMLComponent;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

public class RandomStrategy implements ApproximationStrategy {
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public XMLComponent approximate(
		XMLComponent root, String find, String replace) {
		return null;
	}
}
