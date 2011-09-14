package com.demo.test;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.search.annotation.Index;
import org.paramecium.search.annotation.TextWord;
import org.paramecium.validation.annotation.IDCard;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;
import org.paramecium.validation.annotation.base.Size;
@Index(indexName="index_test",keywordPropertyName="name")
public class Test {
	@NotNull(empty = true)
	@ShowLabel(name = "姓名")
	private String name;

	@NotNull
	@Length(min=4,max=20)
	@ShowLabel(name = "地址")
	@TextWord
	private String address;
	
	@Size(max=50,min=20)
	@ShowLabel(name = "长度")
	private int size;

	@IDCard
	private String idCard;
	
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
