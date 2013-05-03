package org.kemri.wellcome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Timothy Tuti
 * @version 1.0
 *
 */

@Controller
public class IndexController {

	@RequestMapping(Views.INDEX)
	public String index(Model model) {
		return Views.INDEX;
	}
	@RequestMapping(Views.HOME)
	public String home(Model model) {
		return Views.HOME;
	}
}
