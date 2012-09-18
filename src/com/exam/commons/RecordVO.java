package com.exam.commons;

import java.util.Map;

public class RecordVO {
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
