package org.tbrc.common.shared;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
abstract public class SortEWTS implements Comparator {
	
	protected SortEWTS() { }
	
	protected String ConvertToSimpleWylie(String wylie)
	{   // in order to be able to process wylie easier, we map two- and three-character
		// wylie letters to one character
		// replace two- and three-letter wylie characters into one-letter characters,
		// use chars from 129 to 140 for mapping ("a simple wylie")
		String pre;
		pre = wylie;
		pre = pre.replace("kh", "" + (char)129); // replace all occurences of "kh" with char(129)
		pre = pre.replace("ng", "" + (char)130);
		pre = pre.replace("ch", "" + (char)131);
		pre = pre.replace("ny", "" + (char)132);
		pre = pre.replace("ph", "" + (char)133);
		pre = pre.replace("th", "" + (char)134);
		pre = pre.replace("tsh", "" + (char)135);
		pre = pre.replace("ts", "" + (char)136);
		pre = pre.replace("dz", "" + (char)137);
		pre = pre.replace("zh", "" + (char)138);
		pre = pre.replace("sh", "" + (char)139);
		pre = pre.replace("g.", "" + (char)140);

		return pre;
	}

	protected String ConvertFromSimpleWylie(String simplewylie)
	{
		String pre;
		pre = simplewylie;
		pre = pre.replace("" + (char)129, "kh");
		pre = pre.replace("" + (char)130, "ng");
		pre = pre.replace("" + (char)131, "ch");
		pre = pre.replace("" + (char)132, "ny");
		pre = pre.replace("" + (char)133, "ph");
		pre = pre.replace("" + (char)134, "th");
		pre = pre.replace("" + (char)135, "tsh");
		pre = pre.replace("" + (char)136, "ts");
		pre = pre.replace("" + (char)137, "dz");
		pre = pre.replace("" + (char)138, "zh");
		pre = pre.replace("" + (char)139, "sh");
		pre = pre.replace("" + (char)140, "g");
		return pre;
	}

	protected int indexOfAny(String wylie, char[] vowels) {
		for (int i = 0; i < vowels.length; i++) {
			int x = wylie.indexOf(vowels[i]);
			if (x >= 0)
				return x;
		}

		return -1;
	}

	String prefix, superscript, subscript, root, vowel, suffix;
	
	protected int ParseWylieSyllable(String wylie) {
		// take a wylie syllable and identify root letter, vowels pre- and postfixes
		char[] vowels ={ 'a', 'i', 'u', 'e', 'o' };
		int ind;
		String pre;
		String valid_prefixes = "gdbm'";
		String valid_superprefixes = "" + (char)140;// the g. case
		String valid_superscripts = "rsl";
		String valid_subscripts = "wyrl'";

		prefix = "";
		superscript = "";
		root = "";
		subscript = "";
		vowel = "";
		suffix = "";

		ind = indexOfAny(wylie, vowels);
		if (ind == -1)
		{
			wylie += "a";
			ind = indexOfAny(wylie, vowels);
		}
		if (ind > 0) pre = wylie.substring(0, ind);
		else pre = "";
		if (ind + 1 < wylie.length()) suffix = wylie.substring(ind+1);
		else suffix = "";
		vowel = wylie.substring(ind, ind + 1);
		if (wylie.length() == 1)
		{
			root = vowel;
			return 0;
		}

		pre = ConvertToSimpleWylie(pre);
		switch (pre.length())
		{
		case 0: // just a vowel
		break;
		case 1: // just root letter
		root = ConvertFromSimpleWylie(pre);
		break;
		case 2: // prefix+root or superscript+root or root+subscript
			if (valid_superprefixes.indexOf(pre.substring(0, 1)) >= 0)
			{
				prefix = ConvertFromSimpleWylie(pre.substring(0, 1));
				root = ConvertFromSimpleWylie(pre.substring(1, 2));
			}
			else if (valid_subscripts.indexOf(pre.substring(1, 2)) >= 0)
			{
				root = ConvertFromSimpleWylie(pre.substring(0, 1));
				subscript = ConvertFromSimpleWylie(pre.substring(1, 2));
			}
			else if (valid_superscripts.indexOf(pre.substring(0, 1)) >= 0)
			{
				superscript = ConvertFromSimpleWylie(pre.substring(0, 1));
				root = ConvertFromSimpleWylie(pre.substring(1, 2));
			}
			else if (valid_prefixes.indexOf(pre.substring(0, 1)) >= 0)
			{
				prefix = ConvertFromSimpleWylie(pre.substring(0, 1));
				root = ConvertFromSimpleWylie(pre.substring(1, 2));
			}
			else
			{ // that shouldn't happen!
				root = ConvertFromSimpleWylie(pre.substring(1, 2));
			prefix = ConvertFromSimpleWylie(pre.substring(0, 1));
			}
			break;
		case 3: // superscript+root+subsrcipt or prefix+superscript+root or prefix+root+subscript
			if (valid_prefixes.indexOf(pre.substring(0, 1)) >= 0)
			{
				if (valid_superscripts.indexOf(pre.substring(1, 2)) >= 0)
				{ // prefix+superscript+root
					prefix = ConvertFromSimpleWylie(pre.substring(0, 1));
					superscript = ConvertFromSimpleWylie(pre.substring(1, 2));
					root = ConvertFromSimpleWylie(pre.substring(2, 3));
				}
				else
				{ // prefix+root+subscript
					prefix = ConvertFromSimpleWylie(pre.substring(0, 1));
					root = ConvertFromSimpleWylie(pre.substring(1, 2));
					subscript = ConvertFromSimpleWylie(pre.substring(2, 3));
				}
			}
			else
			{ // superscript+root+subscript
				superscript = ConvertFromSimpleWylie(pre.substring(0, 1));
				root = ConvertFromSimpleWylie(pre.substring(1, 2));
				subscript = ConvertFromSimpleWylie(pre.substring(2, 3));
			}
			break;
		case 4: // prefix, superscript, root, subscript
			prefix = ConvertFromSimpleWylie(pre.substring(0, 1));
			superscript = ConvertFromSimpleWylie(pre.substring(1, 2));
			root = ConvertFromSimpleWylie(pre.substring(2, 3));
			subscript = ConvertFromSimpleWylie(pre.substring(3, 4));
			break;
		default: // Sanskrit stack or faulty. Do something nice.
			root = ConvertFromSimpleWylie(pre.substring(pre.length() - 1, pre.length()));
		prefix = ConvertFromSimpleWylie(pre.substring(0, pre.length() - 1));
		break;
		}
		if ("".equals(root)) root = vowel;
		return 0;
	}

	protected int CompareWylieSyllable(String w1, String w2)
	{ // compare two wylie syllables according to the algorithm described in Watson's grammar.
		String con_order = "kkhgngcchjnytthdnpphbmtstshdzwzhz'yrlshsQhaiueoAv0123456789";
		String vow_order = "aiueo";
		String co, vo;
		int i1, i2;
		char sf1, sf2;
		String f1, f2;

		w1 = w1.trim(); w2 = w2.trim();

		co = ConvertToSimpleWylie(con_order);
		vo = vow_order;

		if (w1.equals(w2)) return 0;
		if (!"".equals(w1) && "".equals(w2)) return 1;
		if (!"".equals(w2) && "".equals(w1)) return -1;
		
		String suf1 = "", suf2 = "", sup1 = "", sup2 = "", root1 ="", root2 = "", sub1 = "", sub2 = "", vow1 = "", vow2 = "", pre1 = "", pre2 = "";
		
		ParseWylieSyllable(w1);
		pre1 = prefix; sup1 = superscript; sub1 = subscript; root1 = root; vow1 = vowel; suf1 = suffix;
		ParseWylieSyllable(w2);
		pre2 = prefix; sup2 = superscript; sub2 = subscript; root2 = root; vow2 = vowel; suf2 = suffix;

		// Order of root letter
		i1 = co.indexOf(ConvertToSimpleWylie(root1)); // give the position of a char in a String
		i2 = co.indexOf(ConvertToSimpleWylie(root2));
		if (i1 == -1) i1 = 99;
		if (i2 == -1) i2 = 99;
		if (i1 < i2) return -1;
		if (i1 > i2) return 1;

		// Compare superscripts
		if (sup1.length() > 0 || sup2.length() > 0)
		{
			if (sup1.length() == 0) return -1;
			if (sup2.length() == 0) return 1;
			sf1 = ConvertToSimpleWylie(sup1).charAt(0);
			sf2 = ConvertToSimpleWylie(sup2).charAt(0);
			i1 = co.indexOf(sf1);
			i2 = co.indexOf(sf2);
			if (i1 == -1) i1 = 99;
			if (i2 == -1) i2 = 99;
			if (i1 < i2) return -1;
			if (i1 > i2) return 1;
		}

		// Compare prefixes
		if (pre1.length() > 0 || pre2.length() > 0)
		{
			if (pre1.length() == 0) return -1;
			if (pre2.length() == 0) return 1;
			sf1 = ConvertToSimpleWylie(pre1).charAt(0);
			sf2 = ConvertToSimpleWylie(pre2).charAt(0);
			i1 = co.indexOf(sf1);
			i2 = co.indexOf(sf2);
			if (i1 == -1) i1 = 99;
			if (i2 == -1) i2 = 99;
			if (i1 < i2) return -1;
			if (i1 > i2) return 1;
		}

		// Compare subscripts
		if (sub1.length() > 0 || sub2.length() > 0)
		{
			if (sub1.length() == 0) return -1;
			if (sub2.length() == 0) return 1;
			sf1 = ConvertToSimpleWylie(sub1).charAt(0);
			sf2 = ConvertToSimpleWylie(sub2).charAt(0);
			i1 = co.indexOf(sf1);
			i2 = co.indexOf(sf2);
			if (i1 == -1) i1 = 99;
			if (i2 == -1) i2 = 99;
			if (i1 < i2) return -1;
			if (i1 > i2) return 1;
		}

		// Compare vowels
		i1 = vo.indexOf(ConvertToSimpleWylie(vow1));
		i2 = vo.indexOf(ConvertToSimpleWylie(vow2));
		if (i1 == -1) i1 = 99;
		if (i2 == -1) i2 = 99;
		if (i1 < i2) return -1;
		if (i1 > i2) return 1;

		// Compare suffixes
		if (suf1.length() > 0 || suf2.length() > 0)
		{
			if (suf1.length() == 0) return -1;
			if (suf2.length() == 0) return 1;
			sf1 = ConvertToSimpleWylie(suf1).charAt(0);
			sf2 = ConvertToSimpleWylie(suf2).charAt(0);
			i1 = co.indexOf(sf1);
			i2 = co.indexOf(sf2);
			if (i1 == -1) i1 = 99;
			if (i2 == -1) i2 = 99;
			if (i1 < i2) return -1;
			if (i1 > i2) return 1;
			f1 = ConvertToSimpleWylie(suf1);
			f2 = ConvertToSimpleWylie(suf2);
			if (f1.length() > 1 && f2.length() == 1) return 1;
			if (f2.length() > 1 && f1.length() == 1) return -1;
			if (f1.length() > 1 && f2.length() > 1)
			{
				i1 = co.indexOf(f1.charAt(1));
				i2 = co.indexOf(f2.charAt(1));
				if (i1 == -1) i1 = 99;
				if (i2 == -1) i2 = 99;
				if (i1 < i2) return -1;
				if (i1 > i2) return 1;
			}
		}

		// This point should never be reached!
		return 1; // Just decide!
	}

	protected int DoCompare(String x, String y)
	{ // compare to wylie expressions of arbitrary lenght: result is -1, 0 or 1
		x = (x == null ? "" : x);
		y = (y == null ? "" : y);
		
		String bsep = " ";
		String[] WylSyl1 = new String[1000];
		String[] WylSyl2 = new String[1000];
		int i, m, ret;
		String w1, w2;

		w1 = (String)x; w2 = (String)y;

		if (w1.equals(w2)) return 0;
		if (!"".equals(w1) && "".equals(w2)) return 1;
		if (!"".equals(w2) && "".equals(w1)) return -1;

		WylSyl1 = w1.split(bsep); // split a String into an array using blank as separator
		WylSyl2 = w2.split(bsep);
		if (WylSyl1.length > WylSyl2.length) m = WylSyl2.length; // length() is the number of array members generated by splitting wylie into single syllables
		else m = WylSyl1.length;

		for (i = 0; i < m; i++)
		{
			ret = CompareWylieSyllable(WylSyl1[i], WylSyl2[i]);
			if (ret != 0) return ret;
		}
		if (WylSyl1.length < WylSyl2.length) return 1;
		if (WylSyl1.length > WylSyl2.length) return -1;

		// Should never be reached, just decide:
			return 1;
	}
}
