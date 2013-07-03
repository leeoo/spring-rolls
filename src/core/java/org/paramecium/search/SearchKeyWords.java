package org.paramecium.search;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
/**
 * 搜索全文的关键字
 * @author hadoop
 *
 */
public class SearchKeyWords {

	public static Map<String, Integer> getKeyWords(String text, int top){
		Map<String, Integer> wordsFren = new HashMap<String, Integer>();
		IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(text), true);
		Lexeme lexeme;
		try {
			while ((lexeme = ikSegmenter.next()) != null) {
				if (lexeme.getLexemeText().length() > 1) {
					if (wordsFren.containsKey(lexeme.getLexemeText())) {
						wordsFren.put(lexeme.getLexemeText(),
								wordsFren.get(lexeme.getLexemeText()) + 1);
					} else {
						wordsFren.put(lexeme.getLexemeText(), 1);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Map.Entry<String, Integer>> wordFrenList = new ArrayList<Map.Entry<String, Integer>>(
				wordsFren.entrySet());
		Collections.sort(wordFrenList,
				new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> obj1,
							Map.Entry<String, Integer> obj2) {
						return obj2.getValue() - obj1.getValue();
					}
				});
		wordsFren = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < top && i < wordFrenList.size(); i++) {
			Map.Entry<String, Integer> wordFrenEntry = wordFrenList.get(i);
			if (wordFrenEntry.getValue() > 1) {
				wordsFren.put(wordFrenEntry.getKey(), wordFrenEntry.getValue());
			}
		}
		return wordsFren;
	}

}
