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

import java.util.List;
import org.springframework.stereotype.Component;
import com.gxl.kratos.jdbc.exception.ShardException;
import com.gxl.kratos.jdbc.exception.SqlParserException;

/**
 * Sql解析器接口实现,目前仅支持一些较为简单的Sql语句,并且不支持表别名,如下所示:
 * 
 * *************************SELECT*****************************
 * 
 * 1、SELECT * FROM tab WHERE param1 = ?;
 * 
 * 2、SELECT * FROM tab WHERE param1 = ? AND|OR ...;
 * 
 * 3、SELECT param2... FROM tab WHERE param1 = ? AND|OR...;
 * 
 * ************************************************************
 * *************************INSERT*****************************
 * 
 * 1、INSERT INTO tab(param1) VALUES(?);
 * 
 * 2、INSERT INTO tab(param1,param2...) VALUES(?,?...);
 * 
 * ************************************************************
 * 
 * **************************DELETE****************************
 * 
 * 1、DELETE FROM tab WHERE param1 = ?;
 * 
 * 2、DELETE FROM tab WHERE param1 = ? AND|OR ...;
 * 
 * ************************************************************
 * 
 * **************************UPDATE****************************
 * 
 * 1、UPDATE tab SET param2 = ? WHERE param1 = ?;
 * 
 * 2、UPDATE tab SET param2 = ?,... WHERE param1 = ? AND|OR ...;
 * 
 * ************************************************************
 * 
 * @author gaoxianglong
 */
@Component("sqlParser")
public class KratosSqlParser implements SqlParser {
	/**
	 * 封装java.lang.String的substring()字符串截取方法
	 * 
	 * @author gaoxianglong
	 * 
	 * @param str
	 *            目标字符串
	 * 
	 * @param begin
	 *            起始位置的字符串
	 * 
	 * @param end
	 *            结束为止的字符串
	 * 
	 * @throws SqlParserException
	 * 
	 * @return String 截取后的字符串信息
	 */
	private String substring(String str, String begin, String end) {
		try {
			int beginIndex = str.indexOf(begin) + begin.length();
			int endIndex = str.indexOf(end);
			str = str.substring(beginIndex, endIndex).trim();
		} catch (Exception e) {
			throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + str);
		}
		return str;
	}

	@Override
	public String getTbName(String sql) {
		String tbName = null;
		/* 将sql语句中的单词全部转换为小写 */
		sql = sql.toLowerCase();
		/* 获取sql语句的操作类型 */
		String operationType = sql.split("\\s")[0];
		switch (operationType) {
		case Token.SELECT:
			tbName = substring(sql, Token.FROM, Token.WHERE);
			break;
		case Token.INSERT:
			tbName = substring(sql, Token.INTO, Token.LPAREN);
			break;
		case Token.UPDATE:
			tbName = substring(sql, Token.UPDATE, Token.SET);
			break;
		case Token.DELETE:
			tbName = substring(sql, Token.FROM, Token.WHERE);
		}
		return tbName;
	}

	@Override
	public long getKey(String sql, List<String> rules) {
		long key = -1;
		/* 将sql语句中的单词全部转换为小写 */
		sql = sql.toLowerCase();
		/* 获取sql语句的操作类型 */
		String operationType = sql.split("\\s")[0];
		if (operationType.equals(Token.INSERT)) {
			/* 解析所有的数据库参数字段 */
			String keys = substring(sql, Token.LPAREN, Token.RPAREN);
			/* 检测是否包含多字段参数,比如intset INTO tab(param1,param2) value(...) */
			if (keys.indexOf(Token.COMMA) != -1) {
				/* 检测第一个字段是否是分库分表字段 */
				if (rules.contains(keys.split(Token.COMMA)[0])) {
					try {
						/* 获取values的所有参数 */
						String values = sql.split(Token.VALUES)[1];
						/* 获取分库分表条件 */
						key = Long.parseLong(substring(values, Token.LPAREN, Token.RPAREN).split(Token.COMMA)[0]);
					} catch (Exception e) {
						throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + sql);
					}
				} else {
					throw new ShardException("kratos无法找到分库分表条件,Sql-->" + sql);
				}
			} else {
				/* 单字段参数,比如intset INTO tab(param1) value(...) */
				/* 检测字段是否是分库分表条件 */
				if (rules.contains(keys)) {
					try {
						/* 获取values的所有参数 */
						String values = sql.split(Token.VALUES)[1];
						/* 获取分库分表条件 */
						key = Long.parseLong(substring(values, Token.LPAREN, Token.RPAREN).split(Token.COMMA)[0]);
					} catch (Exception e) {
						throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + sql);
					}
				} else {
					throw new ShardException("kratos无法找到分库分表条件,Sql-->" + sql);
				}
			}
		}
		/* 解析除了INSERT之外的Sql(SELECT、UPDATE、DELETE)语句 */
		else if (operationType.equals(Token.SELECT) || operationType.equals(Token.UPDATE)
				|| operationType.equals(Token.DELETE)) {
			String keys = substring(operationType.equals(Token.UPDATE) ? Token.WHERE + sql.split(Token.WHERE)[1] : sql,
					Token.WHERE, Token.EQ);
			/* 检测第一个字段是否是分库分表字段 */
			if (rules.contains(keys)) {
				try {
					/* 获取分库分表条件 */
					key = Long.parseLong(
							sql.split(Token.WHERE)[1].split(Token.EQ)[1].split(Token.AND + Token.BAR + Token.OR)[0]
									.trim());
				} catch (Exception e) {
					throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + sql);
				}
			} else {
				throw new ShardException("kratos无法找到分库分表条件,Sql-->" + sql);
			}
		} else {
			throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + sql);
		}
		return key;
	}
}