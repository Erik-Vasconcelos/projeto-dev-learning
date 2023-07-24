package br.edu.ifrn.portal.dl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AbertoController {

	@GetMapping
	public String get() {
		return "teste";
	}
	
}
