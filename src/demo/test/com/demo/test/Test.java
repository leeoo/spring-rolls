package com.demo.test;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.validation.annotation.IDCard;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;
import org.paramecium.validation.annotation.base.Size;

public class Test {
	@NotNull(empty = true)
	@ShowLabel(name = "姓名")
	private String name;

	@NotNull
	@Length(min=4,max=10)
	@ShowLabel(name = "地址")
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
