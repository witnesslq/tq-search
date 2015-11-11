package cn.taqu.search.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import cn.taqu.search.web.api.common.RequestParams;
import cn.taqu.search.web.api.vo.RequestBaseParams;

/**
 * 自定义日志记录器，该日志记录器能自动记录客户端传送过来的distinctRequestId
 * @ClassName TQLogger.java
 * @Description TODO
 * @author Chenquanan
 * @date 2015年9月15日 下午5:10:20
 */
public class Log {

	private static Logger logger = LoggerFactory.getLogger(Log.class);
	
	public static void trace(String message) {
		logger.trace(warpMessage(message));
	}
	
	public static void trace(String message, Object...args) {
		logger.trace(warpMessage(message), args);
	}
	
	public static void trace(String message, Throwable t) {
		logger.trace(warpMessage(message), t);
	}
	
	public static void debug(String message) {
		logger.debug(warpMessage(message));
	}
	
	public static void debug(String message, Object...args) {
		logger.debug(warpMessage(message), args);
	}
	
	public static void debug(String message, Throwable t) {
		logger.debug(warpMessage(message), t);
	}
	
	public static void info(String message) {
		logger.info(warpMessage(message));
	}
	
	public static void info(String message, Object...args) {
		logger.info(warpMessage(message), args);
	}
	
	public static void info(String message, Throwable t) {
		logger.info(warpMessage(message), t);
	}
	
	public static void warn(String message) {
		logger.warn(warpMessage(message));
	}
	
	public static void warn(String message, Object...args) {
		logger.warn(warpMessage(message), args);
	}
	
	public static void warn(String message, Throwable t) {
		logger.warn(warpMessage(message), t);
	}
	
	public static void error(String message) {
		logger.error(warpMessage(message));
	}
	
	public static void error(String message, Object...args) {
		logger.error(warpMessage(message), args);
	}
	
	public static void error(String message, Throwable t) {
		logger.error(warpMessage(message), t);
	}
	
	//记录的日志中自动加入distinctRequestId
	private static String warpMessage(String message) {
		RequestBaseParams r = RequestParams.getSoa_basic_java();
		if(r==null){
			r = new RequestBaseParams();
		}
		Gson gson = new Gson();
		return new StringBuilder().append("baseParams=")
				.append(gson.toJson(r) + "&").append("distinctRequestId=")
				.append(RequestParams.getDistinctRequestId()).append("&from=")
				.append(RequestParams.getFormJsonArray()).append("\n")
				.append(message).toString();
	}
}
