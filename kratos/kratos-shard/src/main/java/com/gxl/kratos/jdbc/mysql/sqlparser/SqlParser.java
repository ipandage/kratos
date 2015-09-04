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
package com.gxl.kratos.jdbc.mysql.sqlparser;

import java.util.List;

/**
 * Sql解析器接口,主要负责解析数据库表名和分库分表条件
 * 
 * @author gaoxianglong
 */
public interface SqlParser {
	/**
	 * 解析Sql(SELECT、INSERT、UPDATE、DELETE)语句中的数据库表名
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sql
	 *            数据库sql语句
	 * 
	 * @return String 解析后的数据库表名
	 */
	public String getTbName(String sql);

	/**
	 * 解析出Sql(SELECT、INSERT、UPDATE、DELETE)语句中的分库分表条件
	 *
	 * @author gaoxianglong
	 *
	 * @param sql
	 *            数据库sql语句
	 * 
	 * @param rules
	 *            分库分表关键字
	 *
	 * @return String 解析后的分库分表条件
	 */
	public long getKey(String sql, List<String> rules);
}