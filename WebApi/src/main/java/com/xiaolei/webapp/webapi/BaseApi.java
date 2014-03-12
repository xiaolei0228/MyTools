/**  
 * @Title: BaseApi.java
 * @Package com.ds.vwps.webapi
 * @ClassName: BaseApi
 * @Description: 
 * @author xiaolei-0228@163.com
 * @date 2014年3月8日 上午11:24:21
 * @version V1.0  
 */ 
package com.xiaolei.webapp.webapi;

import jodd.util.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BaseApi {

	/**
	 * @Title: getParamsObj
	 * @Description: 获取页面参数，并转成json对象
	 * @author xiaolei-0228@163.com
	 * @param @param jsonData
	 * @param @return
	 * @return JSONObject    返回类型
	 * @throws @param jsonData
	 * @throws
	 */
	public JSONObject getParamsObj(String jsonData) {
		if (StringUtil.isNotBlank(jsonData)) {
			JSONObject jsonDataObj = JSONObject.parseObject(jsonData);
			return jsonDataObj.getJSONObject("params");
		} else {
			return null;
		}
	}
	
	/**
	 * @Title: getParamsArray
	 * @Description: 获取页面参数，并转成json数组
	 * @author xiaolei-0228@163.com
	 * @param @param jsonData
	 * @param @return
	 * @return JSONArray    返回类型
	 * @throws @param jsonData
	 * @throws
	 */
	public JSONArray getParamsArray(String jsonData) {
		if (StringUtil.isNotBlank(jsonData)) {
			JSONObject jsonDataObj = JSONObject.parseObject(jsonData);
			return jsonDataObj.getJSONArray("params");
		} else {
			return null;
		}
	}
}
