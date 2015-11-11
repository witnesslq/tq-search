package cn.taqu.search.web.springmvc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cn.taqu.search.util.ApiException;
import cn.taqu.search.util.Log;
import cn.taqu.search.web.api.constant.CodeStatus;
import cn.taqu.core.modules.web.exception.ServiceException;

import com.google.gson.Gson;

@ControllerAdvice
public class SpringmvcExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringmvcExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
    public @ResponseBody String handleUncaughtException(Exception ex,HttpServletResponse response,HttpServletRequest request) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        PrintStream ps = new PrintStream(baos);  
        ex.printStackTrace(ps);
        String msg = baos.toString();
		Log.error(",错误信息:" + CodeStatus.ERROR.getReasonPhrase() +msg);
        return httpErrorJson(response,CodeStatus.ERROR);
    }
	
	@ExceptionHandler(value = { ServiceException.class })
	public @ResponseBody String serviceHandelException(ServiceException ex,HttpServletResponse response,HttpServletRequest request) throws IOException{
		CodeStatus codeStatus = CodeStatus.getCodeStatus(ex.getMessage());
		Log.error(",错误信息:"+ codeStatus.getReasonPhrase() + ex.getMessage());
		if(codeStatus.equals(CodeStatus.UNDEFINED)){
			Gson gson = new Gson();
			return gson.toJson(JsonResult.failed(ex.getMessage()));
		}
        return httpErrorJson(response, codeStatus);
	}
	
	@ResponseBody
	@ExceptionHandler(value = { ApiException.class })
	public final JsonResult handleException(ApiException ex,HttpServletRequest request) {
		Log.error(",错误信息:" +ex.getMessage());
		return JsonResult.failed(ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(value = { UnsatisfiedServletRequestParameterException.class })
	public final JsonResult unsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException ex,HttpServletRequest request) {
		String message = MessageFormat.format("错误码[{0}]:{1}", CodeStatus.NOT_METHOD.value(),CodeStatus.NOT_METHOD.getReasonPhrase());
		LOGGER.error(request.getQueryString() + "\n" + message);
		return JsonResult.failedCode(CodeStatus.NOT_METHOD);
	}
	
	private String httpErrorJson(HttpServletResponse response,CodeStatus codeStatus){
		response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new Gson();
        return gson.toJson(JsonResult.failedCode(codeStatus));
	}
	
}
