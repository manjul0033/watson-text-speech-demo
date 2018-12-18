package com.ups.ibm.watson.texttospeech.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslateOptions.Builder;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import com.ibm.watson.developer_cloud.language_translator.v2.util.Language;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

import org.apache.commons.io.FileDeleteStrategy;
import org.springframework.stereotype.Service;

import com.ups.ibm.watson.texttospeech.entity.FileDetails;
import com.ups.ibm.watson.texttospeech.entity.TextDetails;
import com.ups.ibm.watson.texttospeech.service.WatsonAPICallService;

@Service
public class WatsonAPICallServiceImpl implements WatsonAPICallService {
	
	InputStream finalAudioStream = null;

	@Override
	public InputStream getSoundFile(TextDetails textDetails) throws IOException {
		LanguageTranslator translator = new LanguageTranslator();
	    translator.setUsernameAndPassword("", "");

	    TextToSpeech synthesizer = new TextToSpeech();
	    synthesizer.setUsernameAndPassword("", "");

	    // translate
	    Builder build = new Builder();
	    String text = textDetails.getText();
	    StringBuilder stbr = new StringBuilder();
	    for(int i=0;i<text.length();i++) {
	    	stbr.append(text.charAt(i)+", ,");
	    }
	    System.out.println("#####################String->"+stbr.toString());
	    build.addText(stbr.toString());
	    build.source(Language.ENGLISH);
	    build.target(Language.SPANISH);
	    
	    TranslationResult translationResult = translator.translate(build.build()).execute();
	    String translation = translationResult.getTranslations().get(0).getTranslation();

	    // synthesize
	    InputStream in = synthesizer.synthesize(stbr.toString(), Voice.EN_LISA, AudioFormat.WAV).execute();
	    finalAudioStream = WaveUtils.reWriteWaveHeader(in);
	    writeToFile(finalAudioStream, new File(textDetails.getText().trim()+".wav"));
	    return in;
	 }

	  /**
	   * Write the input stream to a file.
	   */
	 private static void writeToFile(InputStream in, File file) {
	    try {
	      OutputStream out = new FileOutputStream(file);
	      System.out.println(in.read());
	      
	      byte[] buf = new byte[1024];
	      int len;
	      while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	      }
	      System.out.println(out.toString());
	      out.close();
	      in.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}

	@Override
	public List<Voice> getAPIDetails() throws Exception {
		TextToSpeech service = new TextToSpeech();
	    service.setUsernameAndPassword("", "");

	    List<Voice> voices = service.getVoices().execute();
	    System.out.println(voices);
		return voices;
	}

	@Override
    public List<FileDetails> listFiles(String directoryName) throws Exception {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        List<FileDetails> audioFilesList = new ArrayList<>();
        for (File file : fList){
            if (file.isFile()){
                FileDetails audiofile = new FileDetails();
                String[] splitedValues = file.getName().split(".");
                System.out.println(splitedValues.length);
                audiofile.setFileName(file.getName());
                audiofile.setExtensionType(file.getName()+" File");
                audiofile.setTimeModified(Long.toString(file.lastModified()));
                audiofile.setSize(Long.toString(file.length()));
                audioFilesList.add(audiofile);
            }
        }
        
        return audioFilesList;
    }

	@Override
	public InputStream getDownloadedFile(String fileName) throws IOException {
		InputStream in = new FileInputStream(fileName.trim()+".wav");
		File file = new File(fileName.trim()+".wav");
		return in;
	}
}
