package com.hepp.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CDStore {

	private List<CD> cdList = new ArrayList<CD>();
	private String cdName;

	@XmlElement(name = "CD")
	public List<CD> getCdList() {
		return cdList;
	}

	public String getCdName() {
		return cdName;
	}

	public void setCdName(String cdName) {
		this.cdName = cdName;
	}
}
