/**
 * 
 */
package com.jbote.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbote.RagService;
import com.jbote.TrainingService;
import com.jbote.dto.InfoDto;

/**
 * @author jobert
 * 
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {
	@Autowired
	private RagService ragService;
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public String question(@RequestParam("q") String question) {
		return ragService.ask(question);
	}

	@PostMapping
	public void train(@RequestBody InfoDto info) {
		trainingService.train(info);
	}
}
