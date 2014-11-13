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

import java.util.List;
import java.util.ArrayList;

/**
 * This library provides calendar data and inter-conversion between Western and Tibetan 
 * designations for years.
 * 
 * The library has been written since there are no Java resources for manipulating Tibetan
 * date information. There are a few Windows applications and some online tools but nothing
 * that can just be dropped into an application.
 * 
 * When converting from Tibetan forms to Western there are four interfaces. First one can
 * supply the element and the animal with or without a specified rab byung (number of a
 * 60 year cycle starting in 1027 CE). This accounts for two of the interfaces. The other
 * two are defined based on supplying an Alternative Name (from the rigpawiki site) for
 * a year within a 60 year cycle along with or without a cycle number. In addition to these
 * four choices one may supply the element and animal names in English, Tibetan Wylie or
 * Tibetan Unicode. The Alternative Names may be encoded either in Tibetan Wylie or
 * Tibetan Unicode. There is a language argument to each of the four interfaces which takes
 * the values: "en", "wy" or "un" as appropriate.
 * 
 * If the cycle number (rab byung) is not provided then a list of Western years in which
 * the given element-animal pair or alternative year name would / will start is returned. 
 * If a cycle number is provided then the Western year in which the given element-animal-cycle  
 * or alternative-year-cycle would have started is returned.
 * 
 * When converting from Western year to Tibetan forms there are two interfaces. One which
 * returns a triple of Strings (as a List<String>) consisting of the element, animal and
 * rab byung number as a String. The other interface returns a pair of the alternative
 * year name and the rab byung number as a String. There is a choice of language of Tibetan
 * Wylie or Unicode for both interfaces and additionally English for the first interface.
 * 
 * This library is based on information at:
 *     http://www.hermetic.ch/cal_stud/tib_year.htm
 * and
 *     http://www.rigpawiki.org/index.php?title=Calendrical_cycle
 * We learned about the first reference from:
 *     http://www.phlonx.com/resources/tibetan_calendar/
 *     
 * @author chris
 *
 */
public class TibetanCalendar {
	public static String[] animalsEnglish = new String[] {"Hare","Dragon","Snake","Horse","Sheep","Monkey","Bird","Dog","Pig","Rat","Ox","Tiger"};
	public static String[] animalsPhonetic = new String[] {"yö","druk","trü","ta","luk","tre","ja","khyi","pak","tsi","lang","tak"};
	public static String[] animalsWylie = new String[] {"yos","'brug","sbrul","rta","lug","spre","bya","khyi","phag","tsi","glang","stag"};
	public static String[] animalsUnicode = new String[] {"ཡོས","འབྲུག","སྦྲུལ","རྟ","ལུག","སྤྲེ","བྱ","ཁྱི","ཕག","ཙི","གླང","སྟག"};

	public static String[] elementsEnglish = new String[] {"Fire","Earth","Iron","Water","Wood"};
	public static String[] elementsPhonetic = new String[] {"me","sa","chak","chu","shing"};
	public static String[] elementsWylie = new String[] {"me","sa","lcags","chu","zhing"};
	public static String[] elementsUnicode = new String[] {"མེ","ས","ལྕགས","ཆུ","ཞིང"};
	
	public static String[] daysEnglish = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static String[] daysTibetanWylie = new String[] {"gza' nyi ma", "gza' zla ba", "gza' mig dmar", "gza' lhag pa", "gza' phur bu", "gza' pa sangs", "gza' spen pa"};
	public static String[] daysTibetanUnicode = new String[] {"གཟའ་ཉི་མ", "གཟའ་ཟླ་བ", "གཟའ་མིག་དམར", "གཟའ་ལྷག་པ", "གཟའ་ཕུར་བུ", "གཟའ་པ་སངས", "གཟའ་སྤེན་པ"};
	public static String[] daysTibetanWylieShort = new String[] {"nyi", "zla", "dmar", "lhag", "phur", "sangs", "spen"};
	public static String[] daysTibetanUnicodeShort = new String[] {"ཉི", "ཟླ", "དམར", "ལྷག", "ཕུར", "སངས", "སྤེན"};
	
	
	public static String[] alternativeNamesWylie = 
		new String[] {"rab byung", "rnam byung", "dkar po", "rab myos", "skyes bdag", 
			"ang gi ra", "dpal gdong", "dngos po", "na tshod ldan", "'dzin byed", 
			"bdang phyug", "'bru mang po", "myos ldan", "rnam gnon", "khyu mchog", 
			"sna tshogs", "nyi ma", "nyi sgrol byed", "sa skyong", "mi zad", 
			"thams cad 'dul", "kun dzin", "'gal ba", "rnam gyur", "bong bu", 
			"dga' ba", "rnam rgyal", "rgyal ba", "myos byed", "gdong ngan", 
			"gser 'phyang", "rnam 'phyang", "sgyur byed", "kun ldan", "'phar ba", 
			"dge byed", "mdzes byed", "khro mo", "sna tshogs dbyig", "zil gnon", 
			"spre'u", "phur bu", "zhi ba", "thun mong", "'gal byed", 
			"yongs 'dzin", "bag med", "kun dga'", "srin po", "me", 
			"dmar ser can", "dus kyi pho nya", "don grub", "drag po", "blo ngan", 
			"rnga chen", "khrag skyug", "mig dmar", "khro bo", "zad pa"};
	
	public static String[] alternativeNamesUnicode =
		new String[] {"རབ་བྱུང", "རྣམ་བྱུང", "དཀར་པོ", "རབ་མྱོས", "སྐྱེས་བདག", 
			"ཨང་གི་ར", "དཔལ་གདོང", "དངོས་པོ", "ན་ཚོད་ལྡན", "འཛིན་བྱེད", 
			"བདང་ཕྱུག", "འབྲུ་མང་པོ", "མྱོས་ལྡན", "རྣམ་གནོན", "ཁྱུ་མཆོག", 
			"སྣ་ཚོགས", "ཉི་མ", "ཉི་སྒྲོལ་བྱེད", "ས་སྐྱོང", "མི་ཟད", 
			"ཐམས་ཅད་འདུལ", "ཀུན་ཛིན", "འགལ་བ", "རྣམ་གྱུར", "བོང་བུ", 
			"དགའ་བ", "རྣམ་རྒྱལ", "རྒྱལ་བ", "མྱོས་བྱེད", "གདོང་ངན", 
			"གསེར་འཕྱང", "རྣམ་འཕྱང", "སྒྱུར་བྱེད", "ཀུན་ལྡན", "འཕར་བ", 
			"དགེ་བྱེད", "མཛེས་བྱེད", "ཁྲོ་མོ", "སྣ་ཚོགས་དབྱིག", "ཟིལ་གནོན", 
			"སྤྲེའུ", "ཕུར་བུ", "ཞི་བ", "ཐུན་མོང", "འགལ་བྱེད", 
			"ཡོངས་འཛིན", "བག་མེད", "ཀུན་དགའ", "སྲིན་པོ", "མེ", 
			"དམར་སེར་ཅན", "དུས་ཀྱི་ཕོ་ཉ", "དོན་གྲུབ", "དྲག་པོ", "བློ་ངན", 
			"རྔ་ཆེན", "ཁྲག་སྐྱུག", "མིག་དམར", "ཁྲོ་བོ", "ཟད་པ"};
	
	protected String[] getAnimals(String lang) {
		if ("en".equals(lang))
			return animalsEnglish;
		else if ("wy".equals(lang))
			return animalsWylie;
		else if ("un".equals(lang))
			return animalsUnicode;
		else
			return null;
	}
	
	protected int getAnimalNumber(String animal, String lang) {
		if (lang == null || animal == null) return -1;
		
		String[] animals = getAnimals(lang);
		
		if (animals == null)
			return -1;
		
		for (int i = 0; i < animals.length; i++) {
			if (animal.equals(animals[i]))
				return i;
		}
		
		return -1;
	}
	
	protected String[] getElements(String lang) {
		if ("en".equals(lang))
			return elementsEnglish;
		else if ("wy".equals(lang))
			return elementsWylie;
		else if ("un".equals(lang))
			return elementsUnicode;
		else
			return null;
	}
	
	protected int getElementNumber(String element, String lang) {
		if (lang == null || element == null) return -1;
		
		String[] elements = getElements(lang);
		
		if (elements == null)
			return -1;
		
		for (int i = 0; i < elements.length; i++) {
			if (element.equals(elements[i]))
				return i;
		}
		
		return -1;
	}
	
	protected String[] getAltNames(String lang) {
		if ("wy".equals(lang))
			return alternativeNamesWylie;
		else if ("un".equals(lang))
			return alternativeNamesUnicode;
		else
			return null;
	}
	
	protected int getM(int e, int a) {
		int j = 55;
		if (2*e >= a && (a % 2 == 0))
			j = 1;
		else if (2*e < a && (a % 2 == 0))
			j = 61;
		else if (2*e > a && (a % 2 == 1))
			j = -5;
		else if (e == 0 && a == 11)
			j = 115;

		return 12*e - 5*a + j;
	}
	
	protected int getM(String altName, String lang) {
		if (altName == null)
			return -1;
		
		String[] altNames = getAltNames(lang);
		
		for (int m = 0; m < altNames.length; m++) {
			if (altName.equals(altNames[m]))
				return m+1;
		}
		
		return -1;
	}

	/**
	 * Given element and animal names and the number of a rab byung return the
	 * corresponding Western year.
	 * 
	 * @param element name in the selected language
	 * @param animal name in the selected language
	 * @param k number of the rab byung. Must be > 0.
	 * @param lang of the element and animal: "en", "wy" or "un"
	 * @return Western year corresponding to the given element and animal and cycle number
	 */
	public int getWesternYear(String element, String animal, int k, String lang) {
		int e = getElementNumber(element, lang);
		int a = getAnimalNumber(animal, lang);
		
		int m = getM(e, a);
		
		return (m + 60*k + 966);
	}
	
	/**
	 * Given element and animal names returns a list of the Western years
	 * corresponding for the first 20 rab byung (through 2227 CE)
	 * 
	 * @param element name in the selected language
	 * @param animal name in the selected language
	 * @param lang of the element and animal: "en", "wy" or "un"
	 * @return list of Western years corresponding to the given element and animal
	 */
	public List<Integer> getWesternYears(String element, String animal, String lang) {
		int e = getElementNumber(element, lang);
		int a = getAnimalNumber(animal, lang);
		
		int m = getM(e, a);
		
		List<Integer> list = new ArrayList<Integer>();
		for (int k = 0; k < 20; k++) {
			list.add(new Integer(m + 60*k + 966));
		}
		
		return list;
	}

	/**
	 * Given an alternative year name and the number of a rab byung return the
	 * corresponding Western year.
	 * 
	 * @param altName for a year in the selected language
	 * @param k number of the rab byung. Must be > 0.
	 * @param lang of the element and animal: "en", "wy" or "un"
	 * @return Western year corresponding to the year name and cycle number
	 */
	public int getWesternYear(String altName, int k, String lang) {		
		int m = getM(altName, lang);
		
		return (m + 60*k + 966);
	}
	
	/**
	 * Given an alternative year name returns a list of the Western years
	 * corresponding for the first 20 rab byung (through 2227 CE)
	 * 
	 * @param altName for a year in the selected language
	 * @param lang of the element and animal: "en", "wy" or "un"
	 * @return list of Integer years the given year name
	 */
	public List<Integer> getWesternYears(String altName, String lang) {
		int m = getM(altName, lang);
		
		List<Integer> list = new ArrayList<Integer>();
		for (int k = 0; k < 20; k++) {
			list.add(new Integer(m + 60*k + 966));
		}
		
		return list;
	}
	
	protected String getElement(int e, String lang) {
		if (e < 0 || e > 4)
			return "no-such-element";
		
		if ("en".equals(lang))
			return elementsEnglish[e];
		else if ("wy".equals(lang))
			return elementsWylie[e];
		else if ("un".equals(lang))
			return elementsUnicode[e];
		
		return "no-such-animal";
	}
	
	protected String getAnimal(int a, String lang) {
		if (a < 0 || a > 11)
			return "no-such-animal";
		
		if ("en".equals(lang))
			return animalsEnglish[a];
		else if ("wy".equals(lang))
			return animalsWylie[a];
		else if ("un".equals(lang))
			return animalsUnicode[a];
		
		return "no-such-animal";
	}
	
	protected String getAltName(int k, String lang) {
		if (k < 0 || k > 59)
			return "no-such-year-name";
		
		if ("wy".equals(lang))
			return alternativeNamesWylie[k - 1];
		else if ("un".equals(lang))
			return alternativeNamesUnicode[k -1];
		
		return "no-such-year-name";
	}
	
	/**
	 * Given a Western year >= 1027 returns a List of three strings:
	 *     element
	 *     animal
	 *     rab byung number
	 *     
	 * in the specified language: English, Tibetan Wylie or Tibetan Unicode
	 * 
	 * @param n Western year >= 1027
	 * @param lang "en", "wy" or "un"
	 * @return if n < 1027 returns null otherwise triple of element, animal and cycle
	 */
	public List<String> getTibetanYear(int n, String lang) {
		if (n < 1027)
			return null;
		
		int i;
		if (n % 2 == 0)
			i = (n + 4) % 10;
		else
			i = ((n + 4) % 10) - 1;
		
		int e = i / 2;
		int a = (n + 5) % 12;
		int k = (n - 967) / 60;
		
		String element = getElement(e, lang);
		String animal = getAnimal(a, lang);
		String cycle = Integer.toString(k);
		
		List<String> list = new ArrayList<String>();
		list.add(element);
		list.add(animal);
		list.add(cycle);
		
		return list;
	}
	
	/**
	 * Given a Western year >= 1027 returns a List of three strings:
	 *     alternative year name within a rab byung
	 *     rab byung number
	 *     
	 * in the specified language: Tibetan Wylie or Tibetan Unicode
	 * 
	 * @param n Western year >= 1027
	 * @param lang "wy" or "un"
	 * @return if n < 1027 returns null otherwise pair of year name and cycle
	 */
	public List<String> getTibetanYearAlt(int n, String lang) {
		if (n < 1027)
			return null;
		
		int y = (n - 967) % 60;
		int k = (n - 967) / 60;
		
		String year = getAltName(y, lang);
		String cycle = Integer.toString(k);
		
		List<String> list = new ArrayList<String>();
		list.add(year);
		list.add(cycle);
		
		return list;
	}
}
