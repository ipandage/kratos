/*
 * Copyright 1999-2101 Alibaba Group Holding Ltd.
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
package com.gxl.kratos.sql.dialect.mysql.ast.clause;

import java.util.ArrayList;
import java.util.List;

import com.gxl.kratos.sql.ast.SQLExpr;
import com.gxl.kratos.sql.ast.statement.SQLSelect;
import com.gxl.kratos.sql.dialect.mysql.ast.statement.MySqlStatementImpl;
import com.gxl.kratos.sql.dialect.mysql.visitor.MySqlASTVisitor;
/**
 * 
 * @Description: MySql procedure select into statement
 * @author zz email:455910092@qq.com
 * @date 2015-9-14
 * @version V1.0
 */
public class MySqlSelectIntoStatement extends MySqlStatementImpl{

	//select statement
	private SQLSelect select;
	//var list
	private List<SQLExpr> varList=new ArrayList<SQLExpr>();
	
	public SQLSelect getSelect() {
		return select;
	}

	public void setSelect(SQLSelect select) {
		this.select = select;
	}

	public List<SQLExpr> getVarList() {
		return varList;
	}

	public void setVarList(List<SQLExpr> varList) {
		this.varList = varList;
	}

	
	
	@Override
	public void accept0(MySqlASTVisitor visitor) {
		if (visitor.visit(this)) {
            acceptChild(visitor, select);
            acceptChild(visitor, varList);
        }
        visitor.endVisit(this);
	}

}