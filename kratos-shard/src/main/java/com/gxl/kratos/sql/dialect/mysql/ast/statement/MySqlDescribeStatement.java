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
package com.gxl.kratos.sql.dialect.mysql.ast.statement;

import com.gxl.kratos.sql.ast.SQLName;
import com.gxl.kratos.sql.ast.statement.SQLDescribeStatement;
import com.gxl.kratos.sql.dialect.mysql.visitor.MySqlASTVisitor;
import com.gxl.kratos.sql.visitor.SQLASTVisitor;

public class MySqlDescribeStatement extends SQLDescribeStatement implements MySqlStatement {

    private SQLName colName;

    public void accept0(MySqlASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, object);
            acceptChild(visitor, colName);
        }
        visitor.endVisit(this);
    }

    @Override
    public void accept0(SQLASTVisitor visitor) {
        if (visitor instanceof MySqlASTVisitor) {
            accept0((MySqlASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    public SQLName getColName() {
        return colName;
    }

    public void setColName(SQLName colName) {
        this.colName = colName;
    }

}
