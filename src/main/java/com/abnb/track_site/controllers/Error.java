package com.abnb.track_site.controllers;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Error implements ErrorController {

	private static final String ERROR_PATH = "/error";

	@RequestMapping(ERROR_PATH)
	public Map<String, Object> error(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		int status = (int) request.getAttribute("javax.servlet.error.status_code");
		map.put("status", request.getAttribute("javax.servlet.error.status_code"));
		map.put("reason", org.springframework.http.HttpStatus.valueOf(status).name());
		return map;
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
