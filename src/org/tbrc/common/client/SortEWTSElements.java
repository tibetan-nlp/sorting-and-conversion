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
