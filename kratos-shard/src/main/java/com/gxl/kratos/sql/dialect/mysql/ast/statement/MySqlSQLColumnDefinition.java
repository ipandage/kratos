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

import com.gxl.kratos.sql.ast.SQLExpr;
import com.gxl.kratos.sql.ast.statement.SQLColumnDefinition;

public class MySqlSQLColumnDefinition extends SQLColumnDefinition {

    private boolean autoIncrement = false;

    private SQLExpr onUpdate;

    private SQLExpr storage;

    public MySqlSQLColumnDefinition(){

    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public SQLExpr getOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(SQLExpr onUpdate) {
        this.onUpdate = onUpdate;
    }

    public SQLExpr getStorage() {
        return storage;
    }

    public void setStorage(SQLExpr storage) {
        this.storage = storage;
    }
}
