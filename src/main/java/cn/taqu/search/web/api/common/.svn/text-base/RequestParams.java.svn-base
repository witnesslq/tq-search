package cn.taqu.search.web.api.common;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.google.gson.Gson;

import cn.taqu.core.modules.utils.Encodes;
import cn.taqu.core.modules.web.exception.ServiceException;
import cn.taqu.search.util.Log;
import cn.taqu.search.web.api.constant.CodeStatus;
import cn.taqu.search.web.api.vo.RequestBaseParams;

/**
 * Controller中的请求参数的封装类(主要针对soa调用时，请求参数都一样，封装成对象，方便对参数进行统一处理)
 * @ClassName RequestParams.java
 * @Description TODO
 * @author Chenquanan
 * @date 2015年9月15日 上午8:49:21
 */
public class RequestParams {
	/**
	 * 线程范围的distinctRequestId变量,在同一线程中获取到的为同一个变量，将此变量用ThreadLocal保存起来，方便除了控制器以外的其他程序代码中获取该变量，获取方式为{@link RequestParams.getDistinctRequestId()}
	 */
	private static ThreadLocal<String> distinctRequestIdThreadLocal = new ThreadLocal<>();

	/**
	 * 线程范围的soa_basic_java变量,在同一线程中获取到的为同一个变量，将此变量用ThreadLocal保存起来，方便除了控制器以外的其他程序代码中获取该变量，获取方式为{@link RequestParams.getSoa_basic_java()}
	 */
	private static ThreadLocal<RequestBaseParams> soaBasicJavaThreadLocal = new ThreadLocal<RequestBaseParams>();

	private static ThreadLocal<JSONArray> formThreadLocal = new ThreadLocal<JSONArray>();

	/******************************************
	 * 由客户端传入的参数 *
	 ******************************************/
	// soa调用的方法的参数，由客户端传入，需要进行一次Base64解码，解码后的结果的JSON Array格式，如：[1,"zhangsan","28"]
	private String form;

	private JSONArray formJSONArray;

	// 附加参数，由客户端传入，需要进行一次Base64解码，解码后的结果为JSON Object格式，如：{"platform":"android4.0","version":"2.35"}
	private String soa_basic_java;

	// 请求识别码，由客户端传入，每次请求时生成的全局唯一码，用于标识每次请求的关联操作
	private String distinctRequestId;

	private String service;

	private String method;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	/******************************************
	 * getter and setter *
	 ******************************************/
	public void setForm(String form) {
		this.form = form;
		try {
			if (this.formJSONArray == null) {
				if (this.form == null || this.form.trim().equals("")) {
					throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
				}
				this.form = this.form.trim().replaceAll(" ", "+");
				// 将form进行base64解码，并转换为JSONArray对象
				this.formJSONArray = new JSONArray(new String(Encodes.decodeBase64(this.form)));
			}
		} catch (Exception e) {
			Log.error(e.toString());
		}
		RequestParams.formThreadLocal.set(this.formJSONArray);
	}

	/**
	 * 返回form转换成的JSONArray对象
	 * @Title getForm
	 * @Description TODO
	 * @return
	 * @author Chenquanan
	 * @throws JSONException
	 * @Date 2015年9月15日 下午4:48:31
	 */
	private JSONArray getForm() throws JSONException {
		if (this.formJSONArray == null) {
			if (this.form == null || !this.form.trim().equals("")) {
				throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
			}
			this.form = this.form.trim().replaceAll(" ", "+");
			// 将form进行base64解码，并转换为JSONArray对象
			this.formJSONArray = new JSONArray(new String(Encodes.decodeBase64(this.form)));
		}
		return this.formJSONArray;
	}

	/**
	 * 检查index是否超出form参数范围
	 * @Title checkFormIndex
	 * @Description TODO
	 * @param index
	 * @throws JSONException
	 * @author Chenquanan
	 * @Date 2015年9月23日 上午8:29:02
	 */
	private void checkFormIndex(int index) throws JSONException {
		if (this.getForm().length() <= index) {
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中参数的个数
	 * @Title getFormLength
	 * @Description TODO
	 * @return
	 * @author Chenquanan
	 * @Date 2015年9月28日 上午10:09:14
	 */
	public int getFormParamsNum() {
		try {
			return this.getForm().length();
		} catch (Exception e) {
			Log.error(e.getMessage());
			e.printStackTrace();
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置String参数，获取参数失败抛出ServiceException
	 * @Title getFormString
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数，没获取到或者参数为空时返回异常
	 * @author Chenquanan
	 * @Date 2015年9月22日 下午4:09:19
	 */
	public String getFormString(int index) {
		try {
			this.checkFormIndex(index);
			String value = this.getForm().getString(index);
			if (value == null || value.trim().length() == 0) {
				throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
			}
			return value;
			/*
			 * if(this.getForm().getString(index).trim().equals("")){ throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value()); } return
			 * this.getForm().getString(index);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置String参数，获取参数失败抛出ServiceException
	 * @Title getFormStringOption
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数或者null
	 * @author Chenquanan
	 * @Date 2015年9月28日 下午3:59:19
	 */
	public String getFormStringOption(int index) {
		try {
			return this.getForm().isNull(index) ? null : this.getForm().getString(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置String Array参数，获取参数失败抛出ServiceException
	 * @Title getFormStringArray
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数，没获取到或者参数为空时返回异常
	 * @author Chenquanan
	 * @Date 2015年10月14日 上午10:11:31
	 */
	public String[] getFormStringArray(int index) {
		try {
			this.checkFormIndex(index);
			Object obj = this.getForm().get(index);
			if (obj instanceof JSONArray) {
				JSONArray subArray = (JSONArray) obj;
				String[] data = new String[subArray.length()];
				for (int i = 0; i < subArray.length(); i++) {
					String value = subArray.getString(i);
					if (value == null || value.trim().length() == 0) {
						throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
					}
					data[i] = value;
				}
				return data;
			}
			return new String[] { obj.toString() };
		} catch (Exception e) {
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置String Array参数，获取参数失败抛出ServiceException
	 * @Title getFormStringArrayOption
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数或者null
	 * @author Chenquanan
	 * @Date 2015年10月14日 上午10:15:34
	 */
	public String[] getFormStringArrayOption(int index) {
		try {
			if (this.getForm().isNull(index)) {
				return null;
			}
			Object obj = this.getForm().get(index);
			if (obj instanceof JSONArray) {
				JSONArray subArray = (JSONArray) obj;
				String[] data = new String[subArray.length()];
				for (int i = 0; i < subArray.length(); i++) {
					data[i] = subArray.getString(i);
				}
				return data;
			}
			return new String[] { obj.toString() };
		} catch (Exception e) {
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Integer参数，获取参数失败抛出ServiceException
	 * @Title getFormInteger
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数，没获取到或者参数为空时返回异常
	 * @author Chenquanan
	 * @Date 2015年9月22日 下午4:10:08
	 */
	public Integer getFormInteger(int index) {
		try {
			this.checkFormIndex(index);
			return this.getForm().getInt(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Integer参数，获取参数失败抛出ServiceException
	 * @Title getFormIntegerOption
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数或者null
	 * @author Chenquanan
	 * @Date 2015年9月28日 下午4:00:03
	 */
	public Integer getFormIntegerOption(int index) {
		try {
			// this.checkFormIndex(index);
			return this.getForm().isNull(index) ? null : this.getForm().getInt(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Long参数，获取参数失败抛出ServiceException
	 * @Title getFormLong
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数，没获取到或者参数为空时返回异常
	 * @author Chenquanan
	 * @Date 2015年9月22日 下午4:10:29
	 */
	public Long getFormLong(int index) {
		try {
			this.checkFormIndex(index);
			return this.getForm().getLong(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Long Array参数，获取参数失败抛出ServiceException
	 * @Title getFormLongArray
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数，没获取到或者参数为空时返回异常
	 * @author Chenquanan
	 * @Date 2015年10月14日 上午9:04:41
	 */
	public Long[] getFormLongArray(int index) {
		try {
			this.checkFormIndex(index);
			Object obj = this.getForm().get(index);
			if (obj instanceof JSONArray) {
				JSONArray subArray = (JSONArray) obj;
				Long[] data = new Long[subArray.length()];
				for (int i = 0; i < subArray.length(); i++) {
					data[i] = subArray.getLong(i);
				}
				return data;
			}
			return new Long[] { Long.parseLong(obj.toString()) };
		} catch (Exception e) {
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Long参数，获取参数失败抛出ServiceException
	 * @Title getFormLongOption
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数或者null
	 * @author Chenquanan
	 * @Date 2015年9月28日 下午4:00:03
	 */
	public Long getFormLongOption(int index) {
		try {
			// this.checkFormIndex(index);
			return this.getForm().isNull(index) ? null : this.getForm().getLong(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Double参数，获取参数失败抛出ServiceException
	 * @Title getFormDouble
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数，没获取到或者参数为空时返回异常
	 * @author Chenquanan
	 * @Date 2015年9月22日 下午4:10:38
	 */
	public Double getFormDouble(int index) {
		try {
			this.checkFormIndex(index);
			return this.getForm().getDouble(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Double参数，获取参数失败抛出ServiceException
	 * @Title getFormDoubleOption
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数或者null
	 * @author Chenquanan
	 * @Date 2015年9月28日 下午4:00:03
	 */
	public Double getFormDoubleOption(int index) {
		try {
			// this.checkFormIndex(index);
			return this.getForm().isNull(index) ? null : this.getForm().getDouble(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Boolean参数，获取参数失败抛出ServiceException
	 * @Title getFormBoolean
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数，没获取到或者参数为空时返回异常
	 * @author Chenquanan
	 * @Date 2015年9月22日 下午4:10:53
	 */
	public Boolean getFormBoolean(int index) {
		try {
			this.checkFormIndex(index);
			return this.getForm().getBoolean(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	/**
	 * 获取form中的位于index位置的Boolean参数，获取参数失败抛出ServiceException
	 * @Title getFormBooleanOption
	 * @Description TODO
	 * @param index
	 * @return 返回获取到的参数或者null
	 * @author Chenquanan
	 * @Date 2015年9月28日 下午4:00:03
	 */
	public Boolean getFormBooleanOption(int index) {
		try {
			// this.checkFormIndex(index);
			return this.getForm().isNull(index) ? null : this.getForm().getBoolean(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			throw new ServiceException(CodeStatus.REQUEST_PARA_ERROR.value());
		}
	}

	public void setSoa_basic_java(String soa_basic_java) {
		this.soa_basic_java = soa_basic_java;
		if (this.soa_basic_java != null) {
			// JSONObject basicJsonObject = new JSONObject(new String(Encodes.decodeBase64(this.soa_basic_java)));
			Gson gson = new Gson();
			RequestBaseParams requestBaseParams = gson.fromJson(new String(Encodes.decodeBase64(this.soa_basic_java)), RequestBaseParams.class);
			requestBaseParams.setService(this.service);
			requestBaseParams.setMethod(this.method);
			// 将转换后的JSONObject对象绑定到当线线程中
			RequestParams.soaBasicJavaThreadLocal.set(requestBaseParams);
		}
	}

	public static RequestBaseParams getSoa_basic_java() {
		return RequestParams.soaBasicJavaThreadLocal.get();
	}

	public static JSONArray getFormJsonArray() {
		return RequestParams.formThreadLocal.get();
	}

	public void setDistinctRequestId(String distinctRequestId) {
		this.distinctRequestId = distinctRequestId;
		// 将distinctRequestId绑定到当前线程中
		RequestParams.distinctRequestIdThreadLocal.set(this.distinctRequestId);
	}

	/**
	 * 获取线程范围的distinctRequestId
	 * @Title getDistinctRequestId
	 * @Description TODO
	 * @return
	 * @author Chenquanan
	 * @Date 2015年9月15日 下午4:42:10
	 */
	public static String getDistinctRequestId() {
		return RequestParams.distinctRequestIdThreadLocal.get();
	}

}
