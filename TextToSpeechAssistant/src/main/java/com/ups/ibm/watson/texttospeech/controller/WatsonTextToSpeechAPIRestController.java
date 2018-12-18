package com.ups.ibm.watson.texttospeech.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ups.ibm.watson.texttospeech.entity.FileDetails;
import com.ups.ibm.watson.texttospeech.entity.TextDetails;
import com.ups.ibm.watson.texttospeech.service.WatsonAPICallService;


@RestController
@RequestMapping(value={"/text_convert_to_speech"})
public class WatsonTextToSpeechAPIRestController {

//	@Autowired
//	private RestTemplate restTemplate;
	
	@Autowired 
	WatsonAPICallService apiCallService;

	@RequestMapping(method=RequestMethod.POST)
	public void getSoundFile(@RequestBody TextDetails textDetails,HttpServletResponse response) throws IOException {
		
		InputStream myStream = apiCallService.getSoundFile(textDetails);

		response.addHeader("Content-disposition", "attachment;filename="+textDetails.getText().trim()+".wav");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/plain");

		// Copy the stream to the response's output stream.
		IOUtils.copy(myStream, response.getOutputStream());
		response.flushBuffer();
	}
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Voice>> getAPIDetails() throws Exception {
		
		List<Voice> vioceList = apiCallService.getAPIDetails();
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<List<Voice>>(vioceList, header, HttpStatus.OK);
	}
	
	@RequestMapping(value="/files", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<FileDetails>> listFiles(String directoryName) throws Exception {
		
		List<FileDetails> audioFilesList = apiCallService.listFiles("C:\\Work\\Text_To_Speech_Audio_File");
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<List<FileDetails>>(audioFilesList, header, HttpStatus.OK);
	}

	@RequestMapping(value="/download", method=RequestMethod.GET)
	public void getDownload(@RequestParam String fileName,HttpServletResponse response) throws Exception {

		// Get your file stream from wherever.
		InputStream myStream = apiCallService.getDownloadedFile(fileName);

		// Set the content type and attachment header.
		response.addHeader("Content-disposition", "attachment;filename="+fileName.trim()+".wav");
		response.setContentType("text/plain");

		// Copy the stream to the response's output stream.
		IOUtils.copy(myStream, response.getOutputStream());
		response.flushBuffer();
	}
		
}
