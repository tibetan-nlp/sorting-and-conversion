package org.tbrc.common.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

import org.tbrc.common.shared.SortEWTS;


import com.google.gwt.xml.client.Element;

public class SortEWTSElements extends SortEWTS {
	public static SortEWTSElements INSTANCE = new SortEWTSElements();
	
	private SortEWTSElements() { }

	public int compare(Object x, Object y ) {
		String xStr = Utils.getTextU((Element) x);
		String yStr = Utils.getTextU((Element) y);
		
		return DoCompare(xStr, yStr);
	}
	
	public ArrayList<Element> sort(List<Element> list) {
		ArrayList<Element> tmp = new ArrayList<Element>();
		
		if (list == null) return tmp;
		
		for (int i = 0; i < list.size(); i++) {
			tmp.add(list.get(i));
		}
		
		Collections.sort(tmp, this);
		
		return tmp;
	}
}
