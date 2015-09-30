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

import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.Assert;

/**
 * 获取数据库表名测试类
 * 
 * @author gaoxianglong
 */
public class GetTbNameTest {
	private static SqlParser sqlParser;

	public @BeforeClass static void init() {
		sqlParser = new KratosSqlParser();
	}

	/**
	 * 测试解析SELECT语句中的数据库表名
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testGetTbNamebySELECT() {
		if (null != sqlParser) {
			String tbName = sqlParser.getTbName("SELECT * FROM tab WHERE param1 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("SELECT * FROM tab WHERE param1 = ? OR param2 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("SELECT * FROM tab WHERE param1 = ? AND param2 = ? AND param3 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("SELECT * FROM tab WHERE param1 = ? AND param2 = ? OR param3 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("SELECT param2,param3 FROM tab WHERE param1 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("SELECT param3 FROM tab WHERE param1 = ? AND param2 = ?");
			Assert.assertEquals("tab", tbName);
		}
	}

	/**
	 * 测试解析INSERT语句中的数据库表名
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testGetTbNamebyINSERT() {
		if (null != sqlParser) {
			String tbName = sqlParser.getTbName("INSERT INTO tab(param1) values(?)");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("INSERT INTO tab(param1,param2) values(?,?)");
			Assert.assertEquals("tab", tbName);
		}
	}

	/**
	 * 测试解析DELETE语句中的数据库表名
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testGetTbNamebyDELETE() {
		if (null != sqlParser) {
			String tbName = sqlParser.getTbName("DELETE FROM tab WHERE param1 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("DELETE FROM tab WHERE param1 = ? OR param2 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("DELETE FROM tab WHERE param1 = ? AND param2 = ? AND param3 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("DELETE FROM tab WHERE param1 = ? AND param2 = ? OR param3 = ?");
			Assert.assertEquals("tab", tbName);
		}
	}

	/**
	 * 测试解析UPDATE语句中的数据库表名
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testGetTbNamebyUPDATE() {
		if (null != sqlParser) {
			String tbName = sqlParser.getTbName("UPDATE tab SET param2 = ? WHERE param1 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("UPDATE tab SET param2 = ?,param3 = ? WHERE param1 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("UPDATE tab SET param3 = ?,param4 = ? WHERE param1 = ? AND param2 = ?");
			Assert.assertEquals("tab", tbName);
			tbName = sqlParser.getTbName("UPDATE tab SET param3 = ?,param4 = ? WHERE param1 = ? OR param2 = ?");
			Assert.assertEquals("tab", tbName);
		}
	}
}