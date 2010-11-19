package com.wisdom.core.utils;

import java.util.Collection;
import java.util.HashSet;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
/**
 * 功 能 描 述:<br>
 * 带多音字的拼音工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-19下午02:14:49
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.utils.PinYinUtils.java
 */
public class PinYinUtils {
	
	public static Collection<String> getPinyin(String str){   
		if(str!=null && !str.trim().equalsIgnoreCase("")){   
			char[] strChar ;   
			strChar=str.toCharArray();   
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();   
			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);    
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);      
			String[][] temp = new String[str.length()][];   
			for(int i=0;i<strChar.length;i++){   
				char c = strChar[i];   
				if(String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")){   
					try{   
						temp[i] = PinyinHelper.toHanyuPinyinStringArray(strChar[i], hanYuPinOutputFormat);   
					}catch(BadHanyuPinyinOutputFormatCombination e) {   
						e.printStackTrace();   
					}   
				}else if(((int)c>=65 && (int)c<=90) || ((int)c>=97 && (int)c<=122)){   
					temp[i] = new String[]{String.valueOf(strChar[i])};   
				}else{   
					temp[i] = new String[]{""};   
				}   
			}   
			String[] pingyinArray = Exchange(temp);   
			Collection<String> pinyinSet = new HashSet<String>();   
			for(int i=0;i<pingyinArray.length;i++){   
				pinyinSet.add(pingyinArray[i]);   
			}   
			return pinyinSet;   
		}   
		return null;   
	}
	
	private static String[] Exchange(String[][] strJaggedArray) {
		String[][] temp = DoExchange(strJaggedArray);
		return temp[0];
	}

	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}
}
