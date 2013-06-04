package strategy;

import composite.XMLComponent;

/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.03.2013
 * language: Java
 * project: approximate-data
 */

public class NaiveStrategy implements ApproximationStrategy {
	private int count;

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public XMLComponent approximate(
			XMLComponent node, String find, String replace) {
		find = find.toLowerCase();
		replace = replace.toLowerCase();

		if(node.parent == null)
			this.count = 0;

		if(node.getText() != null) {
			if(node.getText().toLowerCase().equals(find)) {
				node.setText(replace);
				this.count++;
			}
		}
		for(XMLComponent child : node.children)
			this.approximate(child, find, replace);

		return node;
	}
}
