package com.exam.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ContentUtils {
	
	private final static String englishStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final static String numberStr = "1234567890";
	private final static String pointStr = "。，、；：？！…―ˉˇ〃‘,.'“”々～‖∶\"＂＇｀｜〔〕〈〉《》「」『』．〖〗【】（）［］｛｝ⅠⅡⅢ Ⅳ Ⅴ Ⅵ Ⅶ Ⅷ Ⅸ Ⅹ Ⅺ ⅫＦ⒈⒉⒊Ａ⒋⒌⒍⒎⒏⒐Ｈ⒑⒒⒓⒔⒕⒖⒗⒘⒙⒚⒛①②③④⑤⑥⑦⑧⑨⑩ ㈠ ㈡ ㈢ ㈣ ㈤ ㈥ ㈦ ㈧ ㈨ ㈩ ○ 一 二 三 四 五 六 七 八 九 十 日 月年 ⑴ ⑵ ⑶ ⑷ ⑸ ⑺ ⑻ ⑼ ⑽ ⑾ ⑿ ⒀ ⒂ ⒃ ⒄ ⒅ ⒆ ⒇′″ EFBFA1 ￥ ‰ ℃ ℉ ￠ ♂ ♀ 零 壹 贰 叁 肆 伍 陆 拾 佰 仟 万 亿 吉 太 拍 艾 分 厘 毫 微 ㎎ ㎜ ㎝ ㎞ ㏄ ㏎ ㏑ ㏑ ㏒ ㏕≈ ≡ ≠ ＝ ≤ ≥ ＜ ＞ ≮ ≯ ∷ ＋ － ／ ∶ ∫ ∮ ∝ ∞ ∧ ∨ ∑ ∏ ∪ ∩ ∈ ∵ ∴ ⊥ ∥ ∠ ⌒ ⊙ ≌ ∽ √ ≒ ≦ ≧";

	private final static Map<Character, Character> englishMap = string2Map(englishStr);
	private final static Map<Character, Character> numMap = string2Map(numberStr);
	private final static Map<Character, Character> pointMap = string2Map(pointStr);

	/**
	 * 获得错误分
	 * @param oriStr
	 * @param newStr
	 * @param hanzi
	 * @param zimu
	 * @param fuhao
	 * @param shuzi
	 * @return
	 */
	public static float getErrorScore(String oriStr, String newStr, float hanzi,float zimu, float fuhao, float shuzi) {
		if(newStr==null){
			newStr = "";
		}
		if(oriStr==null){
			oriStr = "";
		}
		List<RecordVO> compareList = new ArrayList<RecordVO>();
		RecordVO recordVO = new RecordVO();
		recordVO.setMap(englishMap);
		recordVO.setRecord(zimu);
		compareList.add(recordVO);

		recordVO = new RecordVO();
		recordVO.setMap(numMap);
		recordVO.setRecord(shuzi);
		compareList.add(recordVO);

		recordVO = new RecordVO();
		recordVO.setMap(pointMap);
		recordVO.setRecord(fuhao);
		compareList.add(recordVO);

		String[] oriUnicodeArr = EncodeChanger.unicode2UnicodeEsc(oriStr).split("\\\\u");
		String[] newUnicodeArr = EncodeChanger.unicode2UnicodeEsc(newStr).split("\\\\u");
		TextUtil.MatchPair[] match = TextUtil.LCS_DN_refined(oriUnicodeArr,newUnicodeArr);
		float result = 0.0F;

		int matchLength = match.length;
		int ori = 0;
		int use = 0;

		for (int i = 1; i < matchLength; i++) {
			if ((match[i].x - ori != 1) || (match[i].y - use != 1)) {
				if ((match[i].x - ori > 1) && (match[i].y - use > 1)) {
					int temp = match[i].x > match[i].y ? match[i].y: match[i].x;
					for (; ori + 1 < temp; ori++) {
						float record = getMatchRecord(oriStr.charAt(ori),compareList, hanzi);
						result += record;
						use++;
					}
				}
				for (; ori + 1 < match[i].x; ori++) {
					float record = getMatchRecord(oriStr.charAt(ori),compareList, hanzi);
					result += record;
				}
				for (; use + 1 < match[i].y; use++) {
					float record = getMatchRecord(newStr.charAt(use),compareList, hanzi);
					result += record;
				}
				ori++;
				use++;
			} else {
				ori++;
				use++;
			}
		}
		int lastOriLength = oriStr.length() - ori;
		int lastUseLength = newStr.length() - use;
		int smallOne = lastOriLength > lastUseLength ? lastUseLength: lastOriLength;
		for (int i = 0; i < smallOne; i++) {
			float record = getMatchRecord(oriStr.charAt(ori + i), compareList,hanzi);
			result += record;
		}
		for (int i = 0; i < lastOriLength - smallOne; i++) {
			float record = getMatchRecord(oriStr.charAt(ori + smallOne + i),compareList, hanzi);
			result += record;
		}
		for (int i = 0; i < lastUseLength - smallOne; i++) {
			float record = getMatchRecord(newStr.charAt(use + smallOne + i),compareList, hanzi);
			result += record;
		}
		return result;
	}

	/**
	 * 获得总分
	 * @param content
	 * @param hanzi
	 * @param zimu
	 * @param fuhao
	 * @param shuzi
	 * @return
	 */
	public static float getTotalSoc(String content, float hanzi, float zimu,float fuhao, float shuzi) {
		if(content==null){
			content = "";
		}
		float result = 0.0F;
		List<RecordVO> list = new ArrayList<RecordVO>();
		RecordVO recordVO = new RecordVO();
		recordVO.setMap(englishMap);
		recordVO.setRecord(zimu);
		list.add(recordVO);

		recordVO = new RecordVO();
		recordVO.setMap(numMap);
		recordVO.setRecord(shuzi);
		list.add(recordVO);

		recordVO = new RecordVO();
		recordVO.setMap(pointMap);
		recordVO.setRecord(fuhao);
		list.add(recordVO);

		int length = content.length();
		for (int i = 0; i < length; i++) {
			char c = content.charAt(i);
			result += getMatchRecord(c, list, hanzi);
		}

		return result;
	}

	private static float getMatchRecord(char c, List<RecordVO> compareList,float defaultRecord) {
		if ((compareList != null) && (compareList.size() != 0)) {
			for (RecordVO vo : compareList) {
				Map<Character, Character> map = vo.getMap();
				if (map.containsKey(Character.valueOf(c))) {
					return vo.getRecord();
				}
			}
		}
		return defaultRecord;
	}

	private static Map<Character, Character> string2Map(String str) {
		Map<Character, Character> map = new HashMap<Character, Character>();
		if (str != null && !str.isEmpty()) {
			int i = 0;
			for (int n = str.length(); i < n; i++) {
				map.put(Character.valueOf(str.charAt(i)),Character.valueOf(str.charAt(i)));
			}
		}
		return map;
	}

	public static class RecordVO {
		private Map<Character,Character> map;
		private float record = 0.0F;

		public Map<Character,Character> getMap() {
			return this.map;
		}

		public void setMap(Map<Character,Character> map) {
			this.map = map;
		}

		public float getRecord() {
			return this.record;
		}

		public void setRecord(float record) {
			this.record = record;
		}
	}

}
