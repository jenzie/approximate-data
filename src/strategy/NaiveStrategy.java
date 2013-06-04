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
	private XMLComponent root;

	public int getCount() {
		return this.count;
	}

	public XMLComponent approximate(
			XMLComponent root, String find, String replace) {
		this.root = root;
		XMLComponent pointer = root;
		find = find.toLowerCase();
		replace = replace.toLowerCase();

		if(pointer.parent == null)
			this.count = 0;

		if(pointer.getText().toLowerCase().equals(find)) {
			pointer.setText(replace);
			this.count++;
		}
		for(XMLComponent child : pointer.children)
			this.approximate(child, find, replace);

		return this.root;
	}
}
