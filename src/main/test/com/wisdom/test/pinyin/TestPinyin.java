package com.wisdom.test.pinyin;

import com.wisdom.core.utils.PinYinUtils;

public class TestPinyin {
	
	public static void main(String[] args) {
		String str = "中国银行";
		System.out.println(PinYinUtils.getPinyin(str));
	}
	
}
