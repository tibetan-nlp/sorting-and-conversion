/*******************************************************************************
 * Copyright (c) 2014 Tibetan Buddhist Resource Center (TBRC)
 * 
 * If this file is a derivation of another work the license header will appear below; 
 * otherwise, this work is licensed under the Apache License, Version 2.0 
 * (the "License"); you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
