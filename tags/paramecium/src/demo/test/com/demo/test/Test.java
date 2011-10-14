package com.demo.test;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.search.annotation.Index;
import org.paramecium.search.annotation.KeyWord;
import org.paramecium.search.annotation.SortWord;
import org.paramecium.search.annotation.TextWord;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;
import org.paramecium.validation.annotation.base.Size;
@Index("index_test")
public class Test {
	
	@NotNull(empty = true)
	@ShowLabel("姓名")
	@KeyWord
	private String name;

	@NotNull
	@Length(min=4,max=20)
	@ShowLabel("地址")
	@TextWord
	private String address;
	
	@Size(max=50,min=20)
	@ShowLabel("长度")
	@SortWord
	private int size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
