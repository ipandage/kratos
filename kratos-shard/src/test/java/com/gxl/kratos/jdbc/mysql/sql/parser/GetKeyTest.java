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
 * See the License for the specific language governing permissions AND
 * limitations under the License.
 */
package com.gxl.kratos.jdbc.mysql.sql.parser;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.Assert;

/**
 * 解析路由条件测试类
 * 
 * @author gaoxianglong
 */
public class GetKeyTest {
	private static SqlParser sqlParser;
	private static List<String> keyNames;

	public @BeforeClass static void init() {
		sqlParser = new KratosSqlParser();
		keyNames = new ArrayList<String>();
		keyNames.add("userinfo_id");
		keyNames.add("email_hash");
		keyNames.add("phoneNumber");
	}

	/**
	 * 测试解析INSERT语句中的路由条件
	 *
	 * @author gaoxianglong
	 */
	public @Test void testGetKeybyINSERT() {
		if (null != sqlParser && null != keyNames) {
			Assert.assertEquals(10000, sqlParser.getKey("INSERT INTO tab(userinfo_id) VALUES(10000)", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("INSERT INTO tab(userinfo_id,sex,address) VALUES(10000,?,?)", keyNames));
		}
	}

	/**
	 * 测试解析SELECT语句中的路由条件
	 *
	 * @author gaoxianglong
	 */
	public @Test void testGetKeybySELECT() {
		if (null != sqlParser && null != keyNames) {
			Assert.assertEquals(10000, sqlParser.getKey("SELECT * FROM tab WHERE userinfo_id = 10000", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("SELECT * FROM tab WHERE userinfo_id = 10000 AND username=?", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("SELECT * FROM tab WHERE userinfo_id = 10000 AND username=? AND sex=?", keyNames));
			Assert.assertEquals(10000, sqlParser.getKey(
					"SELECT * FROM tab WHERE userinfo_id = 10000 AND username=? AND sex=? OR address=?", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("SELECT username FROM tab WHERE userinfo_id = 10000", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("SELECT username,sex,address FROM tab WHERE userinfo_id = 10000", keyNames));
		}
	}

	/**
	 * 测试解析UPDATE语句中的路由条件
	 *
	 * @author gaoxianglong
	 */
	public @Test void testGetKeybyUPDATE() {
		if (null != sqlParser && null != keyNames) {
			Assert.assertEquals(10000,
					sqlParser.getKey("UPDATE tab SET username = ? WHERE userinfo_id = 10000", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("UPDATE tab SET username = ?,sex=? WHERE userinfo_id = 10000", keyNames));
			Assert.assertEquals(10000, sqlParser
					.getKey("UPDATE tab SET username = ?,sex=? WHERE userinfo_id = 10000 AND address=?", keyNames));
			Assert.assertEquals(10000, sqlParser
					.getKey("UPDATE tab SET username = ? WHERE userinfo_id = 10000 AND address=? od sex=?", keyNames));
		}
	}

	/**
	 * 测试解析DELETE语句中的路由条件
	 *
	 * @author gaoxianglong
	 */
	public @Test void testGetKeybyDELETE() {
		if (null != sqlParser && null != keyNames) {
			Assert.assertEquals(10000, sqlParser.getKey("DELETE FROM tab WHERE userinfo_id = 10000", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("DELETE FROM tab WHERE userinfo_id = 10000 OR sex = ?", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("DELETE FROM tab WHERE userinfo_id = 10000 AND sex = ?", keyNames));
			Assert.assertEquals(10000,
					sqlParser.getKey("DELETE FROM tab WHERE userinfo_id = 10000 AND sex = ? AND address=?", keyNames));
		}
	}
}