package com.ups.ibm.watson.texttospeech.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ups.ibm.watson.texttospeech.entity.FileDetails;
import com.ups.ibm.watson.texttospeech.entity.TextDetails;

@Service
public interface WatsonAPICallService {
	
	public InputStream getSoundFile(TextDetails textDetails) throws IOException; 
	
	public List<Voice> getAPIDetails() throws Exception;
	
	public List<FileDetails> listFiles(String directoryName) throws Exception;
	
	public InputStream getDownloadedFile(String fileName) throws IOException;

}
