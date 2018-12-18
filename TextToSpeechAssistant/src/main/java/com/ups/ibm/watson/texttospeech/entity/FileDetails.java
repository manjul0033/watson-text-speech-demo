package com.ups.ibm.watson.texttospeech.entity;

import org.springframework.stereotype.Repository;

@Repository
public class FileDetails {
	
	String fileName;
	String extensionType;
	String dateCreated;
	String dateModified;
	String timeModified;
	String language;
	String size;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getExtensionType() {
		return extensionType;
	}
	public void setExtensionType(String extensionType) {
		this.extensionType = extensionType;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public String getTimeModified() {
		return timeModified;
	}
	public void setTimeModified(String timeModified) {
		this.timeModified = timeModified;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "FileDetails [fileName=" + fileName + ", extensionType=" + extensionType + ", dateCreated=" + dateCreated
				+ ", dateModified=" + dateModified + ", timeModified=" + timeModified + ", language=" + language
				+ ", size=" + size + "]";
	}	
}
