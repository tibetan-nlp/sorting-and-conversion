package org.tbrc.common.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SortEWTSStrings extends SortEWTS {
	public static SortEWTSStrings INSTANCE = new SortEWTSStrings();
	
	private SortEWTSStrings() { }

	@Override
	public int compare(Object x, Object y ) {
		return DoCompare((String)x, (String)y);
	}
	
	public ArrayList<String> sort(List<String> list) {
		ArrayList<String> tmp = new ArrayList<String>();
		
		if (list == null) {
			return tmp;
		}
		
		for (int i = 0; i < list.size(); i++) {
			tmp.add(list.get(i));
		}
		
		Collections.sort(tmp, this);
		
		return tmp;
	}
}
