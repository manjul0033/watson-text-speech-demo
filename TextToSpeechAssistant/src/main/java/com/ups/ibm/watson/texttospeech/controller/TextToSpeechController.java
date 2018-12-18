package com.ups.ibm.watson.texttospeech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TextToSpeechController {
	
	@RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }

}
