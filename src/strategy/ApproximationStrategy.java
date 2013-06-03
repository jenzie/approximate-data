package strategy;

import composite.XMLComponent;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu date: 06.03.2013 language: Java
 * project: approximate-data
 */

public class ApproximationStrategy {
	private XMLComponent root;

	public ApproximationStrategy(XMLComponent root) {
		this.root = root;
	}

	private XMLComponent approximate() {
		return root;
	}
}
