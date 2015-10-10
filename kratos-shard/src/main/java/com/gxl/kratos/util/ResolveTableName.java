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
package com.gxl.kratos.util;

import java.util.List;
import org.springframework.stereotype.Component;
import com.gxl.kratos.sql.ast.SQLStatement;
import com.gxl.kratos.sql.ast.statement.SQLDeleteStatement;
import com.gxl.kratos.sql.ast.statement.SQLInsertStatement;
import com.gxl.kratos.sql.ast.statement.SQLSelect;
import com.gxl.kratos.sql.ast.statement.SQLSelectQueryBlock;
import com.gxl.kratos.sql.ast.statement.SQLSelectStatement;
import com.gxl.kratos.sql.ast.statement.SQLUpdateStatement;
import com.gxl.kratos.sql.dialect.mysql.parser.MySqlStatementParser;
import com.gxl.kratos.sql.parser.SQLStatementParser;

/**
 * 使用Druid的SqlParser解析数据库表名
 * 
 * @author gaoxianglong
 */
public abstract class ResolveTableName {
	/**
	 * 解析数据库表名
	 *
	 * @author gaoxianglong
	 * 
	 * @param sql
	 * 
	 * @return tabName 数据库表名
	 */
	public static String getTabName(String sql) {
		String tabName = null;
		/* 生成AST抽象语法树 */
		SQLStatementParser parser = new MySqlStatementParser(sql);
		List<SQLStatement> statements = parser.parseStatementList();
		if (!statements.isEmpty()) {
			SQLStatement statement = statements.get(0);
			if (statement instanceof SQLSelectStatement) {
				SQLSelectStatement selectStatement = (SQLSelectStatement) statement;
				SQLSelect sqlselect = selectStatement.getSelect();
				SQLSelectQueryBlock queryBlock = (SQLSelectQueryBlock) sqlselect.getQuery();
				tabName = queryBlock.getFrom().toString();
			} else if (statement instanceof SQLInsertStatement) {
				SQLInsertStatement insertStatement = (SQLInsertStatement) statement;
				tabName = insertStatement.getTableName().toString();
			} else if (statement instanceof SQLDeleteStatement) {
				SQLDeleteStatement deleteStatement = (SQLDeleteStatement) statement;
				tabName = deleteStatement.getTableName().toString();
			} else if (statement instanceof SQLUpdateStatement) {
				SQLUpdateStatement updateStatement = (SQLUpdateStatement) statement;
				tabName = updateStatement.getTableName().toString();
			}
		}
		return tabName;
	}

	public static String getNewTabName(int index, String tabName, String tbSuffix) {
		String newTabName = null;
		String left = String.valueOf(tbSuffix.charAt(0));
		String right = tbSuffix.split("[" + left + "]")[1];
		StringBuffer placeholder = new StringBuffer();
		int indexlength = String.valueOf(index).length();
		int rightLength = right.length();
		if (indexlength < rightLength) {
			for (int i = 0; i < rightLength - indexlength; i++)
				placeholder.append(right.charAt(0));
			newTabName = tabName + left + placeholder.toString() + index;
		} else {
			newTabName = tabName + left + index;
		}
		return newTabName;
	}
}