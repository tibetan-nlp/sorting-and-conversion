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
