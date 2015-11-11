/**
 * Copyright (c) 2015 taqu.cn
 * All rights reserved.
 * @Project Name:tq-account
 * @ClassName:CodeStatus.java
 * @Package:cn.taqu.account.web.api.constant
 * @Description:
 * @author:laikunzhen
 * @date:2015年9月22日
 */
package cn.taqu.search.web.api.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.taqu.search.web.springmvc.JsonResult;


/**
 * @ClassName:CodeStatus.java
 * @Description: Code status constant
 * @author:laikunzhen
 * @date:2015年9月22日
 */
public enum CodeStatus {

	/**
	 * normal:正常
	 */
	SUCCESS(JsonResult.CODE_NORMAL, "OK"),
	
	/**
	 * error:系统异常
	 */
	ERROR(JsonResult.CODE_ERROR, "出错了啊！请骚候再试，错误码（a1000）"),
	
	/**
	 * 服务地址错误
	 */
	NOT_FOUND("404", "出错了啊！请骚候再试，错误码（a1001）"),
	
	/**
	 * 找不到方法
	 */
	NOT_METHOD("405", "出错了啊！请骚候再试，错误码（a1002）"),
	
	/**
	 * 内部服务错误
	 */
	INTERNAL_SERVER_ERROR("500", "出错了啊！请骚候再试，错误码（a1003）"),
	
	/**
	 * 服务异常
	 */
	HTTP_SERVER_ERROR("501", "出错了啊！请骚候再试，错误码（a1004）"),
	
	/**
	 * error code undefined
	 */
	UNDEFINED("undefined_error", "出错了啊！请骚候再试，错误码（a1005）"),
	
	/**
	 * 参数有误
	 */
	REQUEST_PARA_ERROR("request_param_error", "请求参数不对，检查一下再试，错误码（a1006）");
	
	
	private static final Logger logger = LoggerFactory.getLogger(CodeStatus.class);
	
	private final String value;//code

	private final String reasonPhrase;//message description
	
	private CodeStatus(String value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public String value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return reasonPhrase;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static CodeStatus getCodeStatus(String statusCode) {
		for (CodeStatus status : values()) {
			if (status.value.equals(statusCode)) {
				return status;
			}
		}
		logger.error("No matching constant for [" + statusCode + "]");
		return CodeStatus.UNDEFINED;
	}
}
