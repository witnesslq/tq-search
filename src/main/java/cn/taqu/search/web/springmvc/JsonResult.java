package cn.taqu.search.web.springmvc;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.taqu.search.web.api.constant.CodeStatus;

/**
 * json转化工具类
 * @author:laikunzhen
 */
public class JsonResult implements Serializable {

	private static final long serialVersionUID = 2096892482295250355L;

	private static final Logger logger = LoggerFactory.getLogger(JsonResult.class);
	
	/**
	 * {@code 0 OK}
	 */
	public final static String CODE_NORMAL = "0";
	
	/**
	 * {@code -1 ERROR}
	 */
	public final static String CODE_ERROR = "-1";
	
	private String code = CODE_NORMAL;
	private String msg;
	private Object data;

	public static JsonResult success(Object data) {
		JsonResult result = new JsonResult();
		result.setData(data);
		return result;
	}

	public static JsonResult success() {
		JsonResult result = new JsonResult();
		return result;
	}
	
	public static JsonResult success(String description){
		JsonResult result = new JsonResult();
		result.setMsg(description);
		return result;
	}

	public static JsonResult failed(String message) {
		JsonResult result = new JsonResult();
		result.setCode(CODE_ERROR);
		result.setMsg(message);
		return result;
	}
	
	public static JsonResult failedCode(CodeStatus codeStatus) {
		JsonResult result = new JsonResult();
		result.setMsg(codeStatus.getReasonPhrase());
		result.setCode(codeStatus.value());
		return result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
