package com.exam.service.exam;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.paramecium.ioc.annotation.Service;

/**
 * 判分系统,通过不同算法，比对原文的相似度.
 * 
 * @author caoyang
 * 
 */
@Service
public class EvaluateService {

	public static void main(String[] args) {
		System.out.println(dimensionValidation("我不知道你是谁，我喜欢你。", "我道欢"));
		System.out.println(stepValidation("我不知道你是谁，我喜欢你。", "我道欢",false));
	}

	/**
	 * 逐字节验证
	 * @param source
	 * @param target
	 * @param isSourceLength是否按照原文件的总字数算正确率
	 * 			当等于true时，一般为按照原文进行判分，距离原文相差多少！
	 * 			当等于false时，一般为按照现有文字进行判分，如计时考试，
	 * @return
	 */
	public static String stepValidation(String source, String target,boolean isSourceLength) {
		int score = 0;
		int d = 0;
		for (char c : source.toCharArray()) {
			char[] c2 = target.toCharArray();
			for (int i = d; i < c2.length; i++) {
				if (c2[i] == c) {
					d = i;
					score++;
					break;
				}
			}
		}
		int length = source.length();
		if(!isSourceLength){
			length = target.length();
		}
		return (double) score / length * 100 + "%";
	}

	/**
	 * 通过二维方式进行xy的维度比较，这种算法可提高相似度验证，但只提高匹配了相似度，并不能有效提高的统计精确性
	 * @param doc1
	 * @param doc2
	 * @return
	 */
	public static String dimensionValidation(String doc1, String doc2) {
		if (doc1 != null && doc1.trim().length() > 0 && doc2 != null&& doc2.trim().length() > 0) {
			Map<Integer, int[]> AlgorithmMap = new LinkedHashMap<Integer, int[]>();
			// 将两个字符串中的中文字符以及出现的总数封装到，AlgorithmMap中
			for (int i = 0; i < doc1.length(); i++) {
				char d1 = doc1.charAt(i);
				int charIndex = getGB2312Id(d1);
				if (charIndex != -1) {
					int[] fq = AlgorithmMap.get(charIndex);
					if (fq != null && fq.length == 2) {
						fq[0]++;
					} else {
						fq = new int[2];
						fq[0] = 1;
						fq[1] = 0;
						AlgorithmMap.put(charIndex, fq);
					}
				}
			}

			for (int i = 0; i < doc2.length(); i++) {
				char d2 = doc2.charAt(i);
				int charIndex = getGB2312Id(d2);
				if (charIndex != -1) {
					int[] fq = AlgorithmMap.get(charIndex);
					if (fq != null && fq.length == 2) {
						fq[1]++;
					} else {
						fq = new int[2];
						fq[0] = 0;
						fq[1] = 1;
						AlgorithmMap.put(charIndex, fq);
					}
				}
			}
			Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
			double sqdoc1 = 0;
			double sqdoc2 = 0;
			double denominator = 0;
			while (iterator.hasNext()) {
				int id = iterator.next();
				int[] c = AlgorithmMap.get(id);
				denominator += c[0] * c[1];
				sqdoc1 += c[0] * c[0];
				sqdoc2 += c[1] * c[1];

			}
			return (denominator / Math.sqrt(sqdoc1 * sqdoc2) * 100) + "%";
		} else {
			throw new NullPointerException(" the Document is null or have not cahrs!!");
		}
	}

	/**
	 * 根据输入的Unicode字符，获取它的GB2312编码或者ascii编码，
	 * 
	 * @param ch
	 *            输入的GB2312中文字符或者ASCII字符(128个)
	 * @return ch在GB2312中的位置，-1表示该字符不认识
	 */

	private static short getGB2312Id(char ch) {
		try {
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			if (buffer.length != 2) {
				// 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'?'，此时说明不认识该字符
				buffer = Character.toString(ch).getBytes();
				return (short) (buffer[0] & 0x0FF);
			}
			int b0 = (int) (buffer[0] & 0x0FF) - 161; // 编码从A1开始，因此减去0xA1=161
			int b1 = (int) (buffer[1] & 0x0FF) - 161; // 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字
			return (short) (b0 * 94 + b1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
