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
/*
 * This implements an ICU-4J collator based on the rule set published at
 * http://unicode.org/repos/cldr/trunk/common/collation/dz.xml
 * by Pema Geyleg of the IT Department of the government of Bhutan.
 * 
 * Copyright 2011 TBRC
 * 
 *  This library is Free Software.  You can redistribute it or modify it, under
 *  the terms of, at your choice, the GNU General Public License (version 2 or
 *  higher), the GNU Lesser General Public License (version 2 or higher), the
 *  Mozilla Public License (any version) or the Apache License version 2 or
 *  higher.
 *
 *  Please contact support@tbrc.org if you wish to use it under some terms not covered
 *  here.
 */
import java.util.Comparator;

import com.ibm.icu.text.RuleBasedCollator;

@SuppressWarnings("rawtypes")
abstract public class SortTibetanUnicode implements Comparator {

	 protected static final String dz_BTRules = "[normalization on] "
	 	+ "& ཀ ; ྈྐ < དཀ < བཀ < རྐ < ལྐ < སྐ < བརྐ < བསྐ "
	 	+ "& ཁ ; ྈྑ < མཁ < འཁ " 
	 	+ "& ག < དགག < དགང < དགད < དགབ < དགཝ < དགའ < དགར < དགལ " 
	 		+ "< དགས < དགུ < དགེ < དགོ < དགྱ < དགྲ < བགག < བགད " 
	 		+ "< བགམ , བགཾ < བགཝ < བགའ < བགར < བགེ < བགོ < བགྱ " 
	 		+ "< བགྲ < བགླ < མགར < མགལ < མགུ < མགེ < མགོ < མགྱ " 
	 		+ "< མགྲ < འགག < འགང < འགད < འགན < འགབ < འགམ , འགཾ " 
	 		+ "< འགའ < འགར < འགལ < འགས < འགི < འགུ < འགེ < འགོ " 
	 		+ "< འགྱ < འགྲ < རྒ < ལྒ < སྒ < བརྒ < བསྒ "
	 	+ "& ང < དངག < དངང < དངན < དངར < དངུ < དངོ < མངག < མངན " 
	 		+ "< མངའ < མངར < མངལ < མངོ < རྔ < ལྔ < སྔ < བརྔ < བསྔ "
	 	+ "& ཅ < གཅ < བཅ < ལྕ < བལྕ " 
	 	+ "& ཆ < མཆ < འཆ " 
	 	+ "& ཇ < མཇ < འཇ < རྗ < ལྗ < བརྗ " 
	 	+ "& ཉ < ྋྙ < གཉ < མཉ < རྙ < སྙ < བརྙ < བསྙ "
	 	+ "& ཏ , ཊ < གཏ < བཏ < རྟ < ལྟ < སྟ < བརྟ < བལྟ < བསྟ " 
		+ "& ཐ , ཋ < མཐ < འཐ " 
		+ "& ད , ཌ < གདག < གདང < གདན < གདབ < གདམ , གདཾ < གདའ " 
			+ "< གདར < གདལ < གདས < གདི < གདུ < གདེ < གདོ < བདག " 
			+ "< བདམ , བདཾ < བདའ < བདར < བདལ < བདས < བདུ < བདེ " 
			+ "< བདོ < མདག < མདང < མདན < མདའ < མདར < མདུ < མདེ " 
			+ "< མདོ < འདག < འདང < འདད < འདན < འདབ < འདམ , འདཾ " 
			+ "< འདཝ < འདའ < འདར < འདལ < འདས < འདི < འདུ < འདེ " 
			+ "< འདོ < འདྲ < རྡ < ལྡ < སྡ < བརྡ < བལྡ < བསྡ " 
		+ "& ན , ཎ < གནག < གནང < གནད < གནན < གནམ , གནཾ < གནཝ " 
			+ "< གནའ < གནས < གནུ < གནོ < མནག < མནང < མནན < མནབ " 
			+ "< མནམ , མནཾ < མནའ < མནར < མནལ < མནུ < མནེ < མནོ " 
			+ "< རྣ < སྣ < བརྣ < བསྣ "
		+ "& པ ; ྉྤ < དཔག < དཔང < དཔད < དཔའ < དཔར < དཔལ < དཔས " 
			+ "< དཔུ < དཔེ < དཔོག < དཔོང < དཔོད < དཔོན < དཔོར < དཔྱ " 
			+ "< དཔྲ < ལྤ < སྤ " 
		+ "& ཕ ; ྉྥ < འཕ " 
		+ "& བ < དབག < དབང < དབད < དབན < དབབ < དབའ < དབར < དབལ " 
			+ "< དབས < དབུ < དབེ < དབོ < དབྱ < དབྲ < འབག < འབང "
		 	+ "< འབད < འབན < འབབ < འབམ , འབཾ < འབའ < འབར < འབལ " 
		 	+ "< འབི < འབུ < འབེ < འབོ < འབྱ < འབྲ < རྦ < ལྦ < སྦ " 
		+ "& མ , ཾ , ྂ , ྃ < དམག < དམང < དམན < དམཝ < དམའ " 
			+ "< དམར < དམས < དམི < དམུ < དམེ < དམོད < དམྱ < རྨ < སྨ "
		+ "& ཙ < གཙ < བཙ < རྩ < སྩ < བརྩ < བསྩ "
		+ "& ཚ < མཚ < འཚ "
		+ "& ཛ < མཛ < འཛ < རྫ < བརྫ "
		+ "& ཞ < གཞ < བཞ "
		+ "& ཟ < གཟ < བཟ " 
		+ "& ཡ < གཡ " 
		+ "& ར , ཪ < བརླ " 
		+ "& ཤ , ཥ < གཤ < བཤ " 
		+ "& ས < གསག < གསང < གསད < གསན < གསབ < གསའ < གསར < གསལ " 
			+ "< གསས < གསི < གསུ < གསེ < གསོ < བསག < བསང < བསད " 
			+ "< བསབ < བསམ , བསཾ < བསར < བསལ < བསི < བསུ < བསེ " 
			+ "< བསོ < བསྭ < བསྲ < བསླ " 
		+ "& ཧ < ལྷ " 
		+ "&  ི ,  ྀ " 
		+ "&  ེ ,  ཻ " 
		+ "&  ོ ,  ཽ " 
		+ "< ྐ < ྑ < ྒ < ྔ < ྕ < ྖ < ྗ < ྙ < ྟ , ྚ < ྠ , ྛ < ྡ , ྜ < ྣ , ྞ " 
			+ "< ྤ < ྥ < ྦ < ྨ < ྩ < ྪ < ྫ < ྭ , ྺ < ྮ < ྯ < ྰ < ྱ , ྻ < ྲ < ྼ " 
			+ "< ླ < ྴ < ྵ < ྶ < ྷ < ྸ " 
		+ "& ༹ < ྄ < ཱ < ༹ < ཿ < ྅ < ྈ < ྉ < ྊ < ྋ " 
		+ "& ྲཱྀ < ཷ " 
		+ "& ླཱྀ < ཹ " 
		+ "& དགགས , དགཊ , དགཌ " 
		+ "& བགགས , བགཊ , བགཌ " 
		+ "& འགགས , འགཊ , འགཌ " 
		+ "& དངགས , དངཊ , དངཌ " 
		+ "& མངགས , མངཊ , མངཌ " 
		+ "& གདགས , གདཊ , གདཌ " 
		+ "& བདགས , བདཊ , བདཌ " 
		+ "& མདགས , མདཊ , མདཌ " 
		+ "& འདགས , འདཊ , འདཌ " 
		+ "& གནགས , གནཊ , གནཌ " 
		+ "& མནགས , མནཊ , མནཌ " 
		+ "& དཔགས , དཔཊ , དཔཌ " 
		+ "& དབགས , དབཊ , དབཌ " 
		+ "& འབགས , འབཊ , འབཌ " 
		+ "& དམགས , དམཊ , དམཌ " 
		+ "& གསགས , གསཊ , གསཌ " 
		+ "& བསགས , བསཊ , བསཌ "
	    + "& ྿ < ༀ " 
	    + "& ໆ < ་ , ༌ ; ། ; ༎ ; ༏ ; ༐ ; ༑ ; ༔ ; ༴ ";

	 protected static final RuleBasedCollator dz_BTCollator = createCollator();
	 
	 protected static final RuleBasedCollator createCollator() {
		 try {
			 return new RuleBasedCollator(dz_BTRules);
		 } catch (Exception ex) {
			 return null;
		 }
	 }
}
