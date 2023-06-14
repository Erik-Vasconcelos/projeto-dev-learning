package br.edu.ifrn.portal.dl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping
	public ModelAndView getindex() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
}
