package com.travel.web.edit;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EditController {
	@RequestMapping("/smarteditor")
	public String smartEditor() {
		return "smartEditor";
	}
}