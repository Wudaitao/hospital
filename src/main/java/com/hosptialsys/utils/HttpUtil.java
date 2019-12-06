package com.hosptialsys.utils;

import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.parser.Entity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

/**
 * @fuction ：封装Http方法 get,post
 * @author  ：NONWORD
 * @version ：2019年8月15日 下午2:12:55
 */
public class HttpUtil {

	private static final Gson gson = new Gson();
	
	/**
	 * 封装Get方法
	 * @param url
	 * @return
	 */
	public static Map<String, Object> doGet(String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		CloseableHttpClient client = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)           //连接超时
							  .setConnectionRequestTimeout(5000) //请求超时
							  .setSocketTimeout(5000)            //socket连接超时
							  .setRedirectsEnabled(true)         //允许重定向
							  .build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		try {
			CloseableHttpResponse httpResponse = client.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()==200) {
				String jsonResult = EntityUtils.toString(httpResponse.getEntity());
				map = gson.fromJson(jsonResult, map.getClass());
			}
		} catch (Exception e) {
			
		} finally {
			try {
				client.close();
			} catch (Exception e2) {
		
			}
		}
		return map;
	}
	
	/**
	 * 封装Post方法
	 * @param url
	 * @param Data
	 * @param timeOut
	 * @return
	 */
	public static String doPost(String url, String data, int timeOut) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeOut)           //连接超时
				  .setConnectionRequestTimeout(timeOut) //请求超时
				  .setSocketTimeout(timeOut)            //socket连接超时
				  .setRedirectsEnabled(true)         //允许重定向
				  .build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type","text/html;chartset=UTF-8");
		if(data!=null && data instanceof String) {    //使用字符串传参数
			StringEntity stringEntity = new StringEntity(data,"UTF-8");
			httpPost.setEntity(stringEntity);			
		}
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if(httpResponse.getStatusLine().getStatusCode()==200) {
				String result = EntityUtils.toString(httpEntity);
				return result;
			}
		} catch (Exception e) {

		}finally {
			try {
				httpClient.close();
			} catch (Exception e2) {

			}
		}
		return null;
	}
}
