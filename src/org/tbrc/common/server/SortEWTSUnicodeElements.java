package org.tbrc.common.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tbrc.common.shared.Converter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SortEWTSUnicodeElements extends SortTibetanUnicode {
	public static SortEWTSUnicodeElements INSTANCE = new SortEWTSUnicodeElements();
	
	public static String getTextU(Element elem) {
		if (elem == null) {
			return null;
		}
		Node node = elem.getFirstChild();
		if (node == null) {
			return null;
		}
		String str = node.getTextContent();
		if (str == null) {
			return null;
		}
		return str.replaceAll("&apos;", "\'");
	}

	public SortEWTSUnicodeElements() { }
	
	private final Converter converter = new Converter(false, false, false, true);

	@Override
	public int compare(Object x, Object y ) {
		String xStr = getTextU((Element) x);
		String yStr = getTextU((Element) y);

		try {
			String xStrU = converter.toUnicode(xStr);
			String yStrU = converter.toUnicode(yStr);
			return dz_BTCollator.compare(xStrU, yStrU);
		} catch (Exception ex) {
				System.err.println("Fail in dz_BTCollator.compare: xStr==" + xStr + ", yStr==" + yStr);
			return 0;
		}
	}
	
	public ArrayList<Element> sort(List<Element> list) {
		ArrayList<Element> tmp = new ArrayList<Element>();
		
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
