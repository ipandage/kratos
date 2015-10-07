/*
 * Copyright 2015-2101 gaoxianglong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gxl.kratos.utils.web.http;

import java.util.Date;
import com.alibaba.fastjson.JSONObject;
import com.gxl.kratos.core.KratosJdbcTemplate;

/**
 * 获取index首页数据
 * 
 * @author gaoxianglong
 */
public abstract class GetIndexData {
	/**
	 * 返回首页数据的json字符串
	 * 
	 * @author gaoxianglong
	 * 
	 * @param jdbcTemplate
	 *            jdbc模板
	 * 
	 * @return String 首页数据
	 */
	@SuppressWarnings("deprecation")
	public static String getData(KratosJdbcTemplate jdbcTemplate) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("kratosVersion", "1.3.3");
		jsonObj.put("osName", System.getProperty("os.name"));
		jsonObj.put("javaVersion", System.getProperty("java.version"));
		jsonObj.put("JvmName", System.getProperty("java.vm.name"));
		jsonObj.put("JavaPath", System.getProperty("java.home"));
		if (jdbcTemplate.getIsShard()) {
			if (jdbcTemplate.getShardMode()) {
				if (jdbcTemplate.getConsistent()) {
					jsonObj.put("shardType", "片名连续的一库一片模式");
				} else {
					jsonObj.put("shardType", "非片名连续的一库一片模式");
				}
			} else {
				if (jdbcTemplate.getConsistent()) {
					jsonObj.put("shardType", "片名连续的库内分片模式");
				} else {
					jsonObj.put("shardType", "非片名连续的库内分片模式");
				}
			}
			String dbKeyName = jdbcTemplate.getDbRuleArray().split("\\#")[1];
			jsonObj.put("route", dbKeyName);
		} else {
			jsonObj.put("shardType", "未开启分库分表开关");
			jsonObj.put("route", "");
		}
		jsonObj.put("startTime", new Date().toLocaleString());
		return jsonObj.toString();
	}
}
