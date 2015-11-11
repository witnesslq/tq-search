package cn.taqu.search.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import cn.taqu.search.web.springmvc.JsonResult;
import cn.taqu.core.modules.mapper.JsonMapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * http client invoker
 * @author:laikunzhen
 */
public class HttpClient {

	private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

	private static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
			+ "Chrome/34.0.1847.116 Safari/537.36";

	private String baseUri;
	private CloseableHttpClient client;

	/**
	 * @param client
	 * @param baseUri
	 */
	public HttpClient(String baseUri) {
		this.baseUri = baseUri;

		HttpClientBuilder httpBuilder = HttpClientBuilder.create();
		httpBuilder.setRedirectStrategy(new LaxRedirectStrategy());
		httpBuilder.setMaxConnPerRoute(10);
		httpBuilder.setMaxConnTotal(50);
		httpBuilder.setUserAgent(userAgent);

		RequestConfig config = RequestConfig.custom().setSocketTimeout(30 * 1000).setConnectTimeout(30 * 1000).build();
		httpBuilder.setDefaultRequestConfig(config);

		this.client = httpBuilder.build();
	}

	public void validataArgs(Object... args) {
		if (args.length > 0) {
			if ((args.length % 2) != 0) {
				throw new ApiException("args must be odd (key+value in order.)");
			}
		}
	}

	public String getUrl(String uri) {
		if(StringUtils.isEmpty(uri)) {
			return StringUtils.removeEnd(this.baseUri, "/");
		}
		return StringUtils.removeEnd(this.baseUri, "/") + "/" + StringUtils.removeStart(uri, "/");
	}

	/**
	 * @param result
	 * @return
	 */
	private Object parse(String result) {
		JsonResult parsed = JsonMapper.nonEmptyMapper().getBean(result, JsonResult.class);//暂定后做修改
		if (parsed == null) {
			logger.warn("api返回数据出错:{}", result);
			throw new ApiException("api返回数据出错");
		} else if (!parsed.getCode().equals(JsonResult.CODE_NORMAL)) {
			throw new ApiException(parsed.getMsg());
		}
		return parsed.getData();
	}

	/**
	 * @param args
	 * @return
	 */
	private HashMap<String, Object> convertToMap(Object... args) {
		validataArgs(args);
		HashMap<String, Object> asMap = Maps.newHashMap();
		for (int i = 0; i < args.length; i += 2) {
			Object key = args[i];
			Object value = args[i + 1];
			if ((key != null) && (value != null)) {
				asMap.put(String.valueOf(key), value);
			}
		}
		return asMap;
	}

	public String get(String uri, Object... args) {
		RequestBuilder rb = RequestBuilder.get();
		HashMap<String, Object> asMap = convertToMap(args);
		return call(rb, uri, asMap);
	}

	public Object getAndParse(String uri, Object... args) {
		String result = this.get(uri, args);
		Object parsed = parse(result);
		return parsed;
	}

	public String post(String uri, Object... args) {
		RequestBuilder rb = RequestBuilder.post();
		rb.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		HashMap<String, Object> asMap = convertToMap(args);
		return call(rb, uri, asMap);
	}

	public Object postAndParse(String uri, Object... args) {
		String result = this.post(uri, args);
		Object parsed = parse(result);
		return parsed;
	}

	public String get(String uri, Map<String, Object> argsAsMap) {
		RequestBuilder rb = RequestBuilder.get();
		return call(rb, uri, argsAsMap);
	}

	public Object getAndParse(String uri, Map<String, Object> argsAsMap) {
		String result = this.get(uri, argsAsMap);
		Object parsed = parse(result);
		return parsed;
	}

	public String post(String uri, Map<String, Object> argsAsMap) {
		RequestBuilder rb = RequestBuilder.post();
		return callWithForm(rb, uri, argsAsMap);
	}

	public Object postAndParse(String uri, Map<String, Object> argsAsMap) {
		String result = this.post(uri, argsAsMap);
		Object parsed = parse(result);
		return parsed;
	}

	public String put(String uri, Object... args) {
		RequestBuilder rb = RequestBuilder.put();
		HashMap<String, Object> asMap = convertToMap(args);
		return call(rb, uri, asMap);
	}

	public String delete(String uri, Object... args) {
		RequestBuilder rb = RequestBuilder.delete();
		HashMap<String, Object> asMap = convertToMap(args);
		return call(rb, uri, asMap);
	}

	private String call(RequestBuilder rb, String uri, Map<String, Object> args) {
		try {
			for (Entry<String, Object> entry : args.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				rb.addParameter(key, String.valueOf(value));
			}
			return doCall(rb, uri);
		} catch (Exception e) {
			throw new ApiException("api调用出错", e);
		}
	}

	private String callWithForm(RequestBuilder rb, String uri, Map<String, Object> args) {
		try {
			ArrayList<BasicNameValuePair> params = Lists.newArrayList();
			for (Entry<String, Object> entry : args.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					BasicNameValuePair param = new BasicNameValuePair(key, String.valueOf(value));
					params.add(param);
				}
			}
			rb.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			return doCall(rb, uri);
		} catch (Exception e) {
			throw new ApiException("api调用出错", e);
		}
	}

	private String doCall(RequestBuilder rb, String uri) throws ClientProtocolException, IOException {
		rb.setUri(getUrl(uri));
		HttpUriRequest request = rb.build();
		CloseableHttpResponse execute = this.client.execute(request);
		String returned = EntityUtils.toString(execute.getEntity(), "UTF-8");
		return returned;
	}

	/**
	 * post form with media
	 * 
	 * @param uri
	 * @param bodies
	 * @param argsAsMap
	 * @return
	 */
	public String post(String uri, List<ByteArrayBody> bodies, Map<String, Object> argsAsMap) {
		try {
			RequestBuilder rb = RequestBuilder.post();
			rb.setUri(getUrl(uri));

			MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
			for (ByteArrayBody body : bodies) {
				meBuilder.addPart(body.getFilename(), body);
			}

			if (!CollectionUtils.isEmpty(argsAsMap)) {
				for (Entry<String, Object> entry : argsAsMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (value != null) {
						meBuilder.addPart(key,
								new StringBody(String.valueOf(value), ContentType.create("text/plain", Consts.UTF_8)));
					}
				}
			}

			HttpEntity entity = meBuilder.build();
			rb.setEntity(entity);

			HttpUriRequest request = rb.build();
			CloseableHttpResponse execute = this.client.execute(request);
			String returned = EntityUtils.toString(execute.getEntity(), "UTF-8");
			return returned;
		} catch (Exception e) {
			throw new ApiException("api调用出错", e);
		}
	}

	public Object postAndParse(String uri, List<ByteArrayBody> bodies, Map<String, Object> argsAsMap) {
		String result = this.post(uri, bodies, argsAsMap);
		Object parsed = parse(result);
		return parsed;
	}

	/**
	 * @return the baseUri
	 */
	public String getBaseUri() {
		return baseUri;
	}

	/**
	 * @param baseUri the baseUri to set
	 */
	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}

	/**
	 * @return the client
	 */
	public CloseableHttpClient getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(CloseableHttpClient client) {
		this.client = client;
	}

	public static void main(String[] args) {

	}

}
