package org.tbrc.common.client;

import java.util.List;

import org.tbrc.common.shared.Converter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class Utils {
	
	public static boolean isTibetanUnicode(String str) {
		if (str == null) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= '\u0F00' && str.charAt(i) < '\u1000') {
				return true;
			}
		}
		return false;
	}
	
	protected static boolean isWylie(String str, String enc) {
		return ("extendedWylie".equals(enc) || !isTibetanUnicode(str));
	}
	
	public static String getCData(Element elem) {
		StringBuilder str = new StringBuilder();
		
		NodeList children = elem.getChildNodes();
		for (int j = 0; j < children.getLength();j++) {
			Node child = children.item(j);
			if (child.getNodeType() == Node.TEXT_NODE) {
				String s = child.getNodeValue();
				str.append(s);
			} else if (child.getNodeType() == Node.ELEMENT_NODE) {
				str.append(getCData((Element) child));
			}
		}

		
		return str.toString();
	}
	
	public static String getTextU(Element elem) {
		return getTextU(elem, false);
	}

	public static String getTextU(Element elem, boolean deep) {
		if (elem == null) {
			return null;
		}

		String str;

		if (deep) {
			str = getCData(elem);
			GWT.log("getTextU deep: " + str, null);
		} else {
			Node node = elem.getFirstChild();

			if (node == null) {
				return null;
			}

			str = node.getNodeValue();
		}

		if (str == null) {
			return null;
		}
		return str.replaceAll("&apos;", "\'");
	}
	
	public static String getTextU(List<Element> elems, int i) {
		if (elems != null && i >= 0 && i < elems.size()) {
			return getTextU(elems.get(i));
		} else {
			return null;
		}
	}
	
	public static Converter converter = new Converter(false, false, false, true);
	
	public static String getStringsU(String textXml) {
		Document objectResults = XMLParser.parse(textXml);
		String texts = objectResults.getChildNodes().toString();
		return texts.replaceAll("&apos;", "\'");
	}
	
	public static String getNodeText(Node node)
	{
		StringBuilder sbValue = new StringBuilder();
		NodeList nodeList = node.getChildNodes();
		if(nodeList != null && nodeList.getLength() > 0)
		{
			for(int i=0; i<nodeList.getLength(); i++)
			{
				if(i>0) {
					sbValue.append(" ");
				}
				sbValue.append(nodeList.item(i).getNodeValue());
			}
		}
		return sbValue.toString();
	}

	/**
	 * This method encodes a query pattern so that it may be safely used in a URL. This means
	 * that '+' used in Wylie needs to be converted to '|' so that ' ' can be encoded as '+' which
	 * is conventional in URLs. Also it is necessary to convert '"' to "&quot;" so that '"' characters
	 * don't confuse contexts in which the URL will be used.
	 * 
	 * @param query
	 * @return
	 */
	public static String query2url(String query) {
		return query.replace('+', '|').replace(' ', '+').replace("\"", "&quot;");
	}
	
	/**
	 * This method undoes the previous method so that the resulting string is "what the user
	 * originally entered". Typically this will have to be processed with query2lucene or a
	 * similar method that will present the query in a form that is expected in eXist.
	 * 
	 * @param url
	 * @return
	 */
	public static String url2query(String url) {
		return url.replace('+', ' ').replace('|', '+').replace("&quot;", "\"");
	}
	
	public static String squeeze(String s) {
		return s.trim().replaceAll("\\s+", " ");
	}
	
	/**
	 * This method takes a query of the form:
	 * 
	 *    "some text" token1 "more text" token2 token3
	 *    
	 * and so on, and converts it to a form that makes sense in eXist's integration of lucene:
	 * 
	 *    &amp;quot;some text&amp;quot; AND token1 AND &amp;quot;more text&amp;quot; AND token2 AND token3
	 * 
	 * It is essential that queries of the form:
	 * 
	 *    token1 token2 token3
	 * 
	 * and such do not get through to lucene since these are disjunctive and take combinatorially long by
	 * comparison with the conjunctive form. Unfortunately lucene defauts to disjunctive rather than
	 * conjunctive so rewriting is essential
	 * 
	 * @param query
	 * @return
	 */
	public static String query2lucene(String query) {
		if (query == null || query.isEmpty()) {
			return "";
		}
		
		// get rid of leading and trailing ' ' and repeated ' '
		query = squeeze(query);
		
		StringBuilder lucene = new StringBuilder();

		char[] chars = query.toCharArray();
		boolean inQ = false;
		
		for (char c : chars) {
			if (c == '"') {
				inQ = ! inQ;
				lucene.append("&amp;quot;");
			} else if (! inQ && c == ' ') {
				lucene.append(" AND ");
			} else {
				lucene.append(c);
			}
		}

		GWT.log("Utils.query2lucene: " + query + " >>> " + lucene.toString());
		return lucene.toString();
	}
	
	/**
	 * This method undoes query2lucene with the aim of reconstituting the original user input.
	 * 
	 * @param lucene
	 * @return
	 */
	public static String lucene2query(String lucene) {
		String query = lucene.replace("&amp;quot;", "\"").replace(" AND ", " ");
		GWT.log("Utils.lucene2query: " + lucene + " >>> " + query);
		return query;
	}
	
	public static String lucene2queryIndium(String lucene) {
		String query = lucene.replace("&quot;", "\"").replace(" AND ", " ");
		GWT.log("Utils.lucene2query: " + lucene + " >>> " + query);
		return query;
	}
}
