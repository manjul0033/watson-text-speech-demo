package com.ups.ibm.watson.texttospeech.entity;

import org.springframework.stereotype.Repository;

@Repository
public class TextDetails {
	
	String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "TextDetails [text=" + text + "]";
	}
	
}
