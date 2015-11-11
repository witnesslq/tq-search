/*
 * Copyright (c) 2015 taqu.cn
 * All rights reserved.
 */
package cn.taqu.search.web.springmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.taqu.search.web.api.constant.CodeStatus;
import cn.taqu.core.modules.web.exception.ServiceException;

@Controller
public class ExceptionHandlingController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

	@ResponseBody
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public JsonResult error404() {
		logger.error(CodeStatus.NOT_FOUND.getReasonPhrase());
    	throw new ServiceException(CodeStatus.NOT_FOUND.value());
    }
    
    @ResponseBody
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public JsonResult error500() {
    	logger.error(CodeStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    	throw new ServiceException(CodeStatus.INTERNAL_SERVER_ERROR.value());
    }
    
    @ResponseBody
	@RequestMapping(value = "/uncaughtException", method = RequestMethod.GET)
	public JsonResult uncaughtException() {
    	logger.error(CodeStatus.HTTP_SERVER_ERROR.getReasonPhrase());
    	throw new ServiceException(CodeStatus.HTTP_SERVER_ERROR.value());
    }
    
}
