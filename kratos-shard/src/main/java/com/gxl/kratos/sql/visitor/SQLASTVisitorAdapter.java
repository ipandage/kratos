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
package com.gxl.kratos.sql.visitor;

import com.gxl.kratos.sql.ast.SQLCommentHint;
import com.gxl.kratos.sql.ast.SQLDataType;
import com.gxl.kratos.sql.ast.SQLExpr;
import com.gxl.kratos.sql.ast.SQLObject;
import com.gxl.kratos.sql.ast.SQLOrderBy;
import com.gxl.kratos.sql.ast.SQLOver;
import com.gxl.kratos.sql.ast.expr.SQLAggregateExpr;
import com.gxl.kratos.sql.ast.expr.SQLAllColumnExpr;
import com.gxl.kratos.sql.ast.expr.SQLAllExpr;
import com.gxl.kratos.sql.ast.expr.SQLAnyExpr;
import com.gxl.kratos.sql.ast.expr.SQLArrayExpr;
import com.gxl.kratos.sql.ast.expr.SQLBetweenExpr;
import com.gxl.kratos.sql.ast.expr.SQLBinaryExpr;
import com.gxl.kratos.sql.ast.expr.SQLBinaryOpExpr;
import com.gxl.kratos.sql.ast.expr.SQLBooleanExpr;
import com.gxl.kratos.sql.ast.expr.SQLCaseExpr;
import com.gxl.kratos.sql.ast.expr.SQLCastExpr;
import com.gxl.kratos.sql.ast.expr.SQLCharExpr;
import com.gxl.kratos.sql.ast.expr.SQLCurrentOfCursorExpr;
import com.gxl.kratos.sql.ast.expr.SQLDefaultExpr;
import com.gxl.kratos.sql.ast.expr.SQLExistsExpr;
import com.gxl.kratos.sql.ast.expr.SQLHexExpr;
import com.gxl.kratos.sql.ast.expr.SQLIdentifierExpr;
import com.gxl.kratos.sql.ast.expr.SQLInListExpr;
import com.gxl.kratos.sql.ast.expr.SQLInSubQueryExpr;
import com.gxl.kratos.sql.ast.expr.SQLIntegerExpr;
import com.gxl.kratos.sql.ast.expr.SQLListExpr;
import com.gxl.kratos.sql.ast.expr.SQLMethodInvokeExpr;
import com.gxl.kratos.sql.ast.expr.SQLNCharExpr;
import com.gxl.kratos.sql.ast.expr.SQLNotExpr;
import com.gxl.kratos.sql.ast.expr.SQLNullExpr;
import com.gxl.kratos.sql.ast.expr.SQLNumberExpr;
import com.gxl.kratos.sql.ast.expr.SQLPropertyExpr;
import com.gxl.kratos.sql.ast.expr.SQLQueryExpr;
import com.gxl.kratos.sql.ast.expr.SQLSomeExpr;
import com.gxl.kratos.sql.ast.expr.SQLTimestampExpr;
import com.gxl.kratos.sql.ast.expr.SQLUnaryExpr;
import com.gxl.kratos.sql.ast.expr.SQLVariantRefExpr;
import com.gxl.kratos.sql.ast.statement.NotNullConstraint;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableAddColumn;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableAddConstraint;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableAddIndex;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableAddPartition;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableAlterColumn;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDisableConstraint;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDisableKeys;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDisableLifecycle;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDropColumnItem;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDropConstraint;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDropForeignKey;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDropIndex;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDropPartition;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableDropPrimaryKey;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableEnableConstraint;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableEnableKeys;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableEnableLifecycle;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableRename;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableRenameColumn;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableRenamePartition;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableSetComment;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableSetLifecycle;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableStatement;
import com.gxl.kratos.sql.ast.statement.SQLAlterTableTouch;
import com.gxl.kratos.sql.ast.statement.SQLAlterViewRenameStatement;
import com.gxl.kratos.sql.ast.statement.SQLAssignItem;
import com.gxl.kratos.sql.ast.statement.SQLCallStatement;
import com.gxl.kratos.sql.ast.statement.SQLCharacterDataType;
import com.gxl.kratos.sql.ast.statement.SQLCheck;
import com.gxl.kratos.sql.ast.statement.SQLCloseStatement;
import com.gxl.kratos.sql.ast.statement.SQLColumnCheck;
import com.gxl.kratos.sql.ast.statement.SQLColumnDefinition;
import com.gxl.kratos.sql.ast.statement.SQLColumnPrimaryKey;
import com.gxl.kratos.sql.ast.statement.SQLColumnReference;
import com.gxl.kratos.sql.ast.statement.SQLColumnUniqueKey;
import com.gxl.kratos.sql.ast.statement.SQLCommentStatement;
import com.gxl.kratos.sql.ast.statement.SQLCreateDatabaseStatement;
import com.gxl.kratos.sql.ast.statement.SQLCreateIndexStatement;
import com.gxl.kratos.sql.ast.statement.SQLCreateTableStatement;
import com.gxl.kratos.sql.ast.statement.SQLCreateTriggerStatement;
import com.gxl.kratos.sql.ast.statement.SQLCreateViewStatement;
import com.gxl.kratos.sql.ast.statement.SQLDeleteStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropDatabaseStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropFunctionStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropIndexStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropProcedureStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropSequenceStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropTableSpaceStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropTableStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropTriggerStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropUserStatement;
import com.gxl.kratos.sql.ast.statement.SQLDropViewStatement;
import com.gxl.kratos.sql.ast.statement.SQLExplainStatement;
import com.gxl.kratos.sql.ast.statement.SQLExprHint;
import com.gxl.kratos.sql.ast.statement.SQLExprTableSource;
import com.gxl.kratos.sql.ast.statement.SQLFetchStatement;
import com.gxl.kratos.sql.ast.statement.SQLForeignKeyImpl;
import com.gxl.kratos.sql.ast.statement.SQLGrantStatement;
import com.gxl.kratos.sql.ast.statement.SQLInsertStatement;
import com.gxl.kratos.sql.ast.statement.SQLJoinTableSource;
import com.gxl.kratos.sql.ast.statement.SQLOpenStatement;
import com.gxl.kratos.sql.ast.statement.SQLPrimaryKeyImpl;
import com.gxl.kratos.sql.ast.statement.SQLReleaseSavePointStatement;
import com.gxl.kratos.sql.ast.statement.SQLRevokeStatement;
import com.gxl.kratos.sql.ast.statement.SQLRollbackStatement;
import com.gxl.kratos.sql.ast.statement.SQLSavePointStatement;
import com.gxl.kratos.sql.ast.statement.SQLSelect;
import com.gxl.kratos.sql.ast.statement.SQLSelectGroupByClause;
import com.gxl.kratos.sql.ast.statement.SQLSelectItem;
import com.gxl.kratos.sql.ast.statement.SQLSelectOrderByItem;
import com.gxl.kratos.sql.ast.statement.SQLSelectQueryBlock;
import com.gxl.kratos.sql.ast.statement.SQLSelectStatement;
import com.gxl.kratos.sql.ast.statement.SQLSetStatement;
import com.gxl.kratos.sql.ast.statement.SQLShowTablesStatement;
import com.gxl.kratos.sql.ast.statement.SQLSubqueryTableSource;
import com.gxl.kratos.sql.ast.statement.SQLTruncateStatement;
import com.gxl.kratos.sql.ast.statement.SQLUnionQuery;
import com.gxl.kratos.sql.ast.statement.SQLUnionQueryTableSource;
import com.gxl.kratos.sql.ast.statement.SQLUnique;
import com.gxl.kratos.sql.ast.statement.SQLUpdateSetItem;
import com.gxl.kratos.sql.ast.statement.SQLUpdateStatement;
import com.gxl.kratos.sql.ast.statement.SQLUseStatement;
import com.gxl.kratos.sql.ast.statement.SQLWithSubqueryClause;
import com.gxl.kratos.sql.ast.statement.SQLInsertStatement.ValuesClause;

public class SQLASTVisitorAdapter implements SQLASTVisitor {

    public void endVisit(SQLAllColumnExpr x) {
    }

    public void endVisit(SQLBetweenExpr x) {
    }

    public void endVisit(SQLBinaryOpExpr x) {
    }

    public void endVisit(SQLCaseExpr x) {
    }

    public void endVisit(SQLCaseExpr.Item x) {
    }

    public void endVisit(SQLCharExpr x) {
    }

    public void endVisit(SQLIdentifierExpr x) {
    }

    public void endVisit(SQLInListExpr x) {
    }

    public void endVisit(SQLIntegerExpr x) {
    }

    public void endVisit(SQLExistsExpr x) {
    }

    public void endVisit(SQLNCharExpr x) {
    }

    public void endVisit(SQLNotExpr x) {
    }

    public void endVisit(SQLNullExpr x) {
    }

    public void endVisit(SQLNumberExpr x) {
    }

    public void endVisit(SQLPropertyExpr x) {
    }

    public void endVisit(SQLSelectGroupByClause x) {
    }

    public void endVisit(SQLSelectItem x) {
    }

    public void endVisit(SQLSelectStatement selectStatement) {
    }

    public void postVisit(SQLObject astNode) {
    }

    public void preVisit(SQLObject astNode) {
    }

    public boolean visit(SQLAllColumnExpr x) {
        return true;
    }

    public boolean visit(SQLBetweenExpr x) {
        return true;
    }

    public boolean visit(SQLBinaryOpExpr x) {
        return true;
    }

    public boolean visit(SQLCaseExpr x) {
        return true;
    }

    public boolean visit(SQLCaseExpr.Item x) {
        return true;
    }

    public boolean visit(SQLCastExpr x) {
        return true;
    }

    public boolean visit(SQLCharExpr x) {
        return true;
    }

    public boolean visit(SQLExistsExpr x) {
        return true;
    }

    public boolean visit(SQLIdentifierExpr x) {
        return true;
    }

    public boolean visit(SQLInListExpr x) {
        return true;
    }

    public boolean visit(SQLIntegerExpr x) {
        return true;
    }

    public boolean visit(SQLNCharExpr x) {
        return true;
    }

    public boolean visit(SQLNotExpr x) {
        return true;
    }

    public boolean visit(SQLNullExpr x) {
        return true;
    }

    public boolean visit(SQLNumberExpr x) {
        return true;
    }

    public boolean visit(SQLPropertyExpr x) {
        return true;
    }

    public boolean visit(SQLSelectGroupByClause x) {
        return true;
    }

    public boolean visit(SQLSelectItem x) {
        return true;
    }

    public void endVisit(SQLCastExpr x) {
    }

    public boolean visit(SQLSelectStatement astNode) {
        return true;
    }

    public void endVisit(SQLAggregateExpr x) {
    }

    public boolean visit(SQLAggregateExpr x) {
        return true;
    }

    public boolean visit(SQLVariantRefExpr x) {
        return true;
    }

    public void endVisit(SQLVariantRefExpr x) {
    }

    public boolean visit(SQLQueryExpr x) {
        return true;
    }

    public void endVisit(SQLQueryExpr x) {
    }

    public boolean visit(SQLSelect x) {
        return true;
    }

    public void endVisit(SQLSelect select) {
    }

    public boolean visit(SQLSelectQueryBlock x) {
        return true;
    }

    public void endVisit(SQLSelectQueryBlock x) {
    }

    public boolean visit(SQLExprTableSource x) {
        return true;
    }

    public void endVisit(SQLExprTableSource x) {
    }

    public boolean visit(SQLOrderBy x) {
        return true;
    }

    public void endVisit(SQLOrderBy x) {
    }

    public boolean visit(SQLSelectOrderByItem x) {
        return true;
    }

    public void endVisit(SQLSelectOrderByItem x) {
    }

    public boolean visit(SQLDropTableStatement x) {
        return true;
    }

    public void endVisit(SQLDropTableStatement x) {
    }

    public boolean visit(SQLCreateTableStatement x) {
        return true;
    }

    public void endVisit(SQLCreateTableStatement x) {
    }

    public boolean visit(SQLColumnDefinition x) {
        return true;
    }

    public void endVisit(SQLColumnDefinition x) {
    }

    public boolean visit(SQLDataType x) {
        return true;
    }

    public void endVisit(SQLDataType x) {
    }

    public boolean visit(SQLDeleteStatement x) {
        return true;
    }

    public void endVisit(SQLDeleteStatement x) {
    }

    public boolean visit(SQLCurrentOfCursorExpr x) {
        return true;
    }

    public void endVisit(SQLCurrentOfCursorExpr x) {
    }

    public boolean visit(SQLInsertStatement x) {
        return true;
    }

    public void endVisit(SQLInsertStatement x) {
    }

    public boolean visit(SQLUpdateSetItem x) {
        return true;
    }

    public void endVisit(SQLUpdateSetItem x) {
    }

    public boolean visit(SQLUpdateStatement x) {
        return true;
    }

    public void endVisit(SQLUpdateStatement x) {
    }

    public boolean visit(SQLCreateViewStatement x) {
        return true;
    }

    public void endVisit(SQLCreateViewStatement x) {
    }
    
    public boolean visit(SQLCreateViewStatement.Column x) {
        return true;
    }
    
    public void endVisit(SQLCreateViewStatement.Column x) {
    }

    public boolean visit(NotNullConstraint x) {
        return true;
    }

    public void endVisit(NotNullConstraint x) {
    }

    @Override
    public void endVisit(SQLMethodInvokeExpr x) {

    }

    @Override
    public boolean visit(SQLMethodInvokeExpr x) {
        return true;
    }

    @Override
    public void endVisit(SQLUnionQuery x) {

    }

    @Override
    public boolean visit(SQLUnionQuery x) {
        return true;
    }

    @Override
    public boolean visit(SQLUnaryExpr x) {
        return true;
    }

    @Override
    public void endVisit(SQLUnaryExpr x) {

    }

    @Override
    public boolean visit(SQLHexExpr x) {
        return false;
    }

    @Override
    public void endVisit(SQLHexExpr x) {

    }

    @Override
    public void endVisit(SQLSetStatement x) {

    }

    @Override
    public boolean visit(SQLSetStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLAssignItem x) {

    }

    @Override
    public boolean visit(SQLAssignItem x) {
        return true;
    }

    @Override
    public void endVisit(SQLCallStatement x) {

    }

    @Override
    public boolean visit(SQLCallStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLJoinTableSource x) {

    }

    @Override
    public boolean visit(SQLJoinTableSource x) {
        return true;
    }

    @Override
    public boolean visit(ValuesClause x) {
        return true;
    }

    @Override
    public void endVisit(ValuesClause x) {

    }

    @Override
    public void endVisit(SQLSomeExpr x) {

    }

    @Override
    public boolean visit(SQLSomeExpr x) {
        return true;
    }

    @Override
    public void endVisit(SQLAnyExpr x) {

    }

    @Override
    public boolean visit(SQLAnyExpr x) {
        return true;
    }

    @Override
    public void endVisit(SQLAllExpr x) {

    }

    @Override
    public boolean visit(SQLAllExpr x) {
        return true;
    }

    @Override
    public void endVisit(SQLInSubQueryExpr x) {

    }

    @Override
    public boolean visit(SQLInSubQueryExpr x) {
        return true;
    }

    @Override
    public void endVisit(SQLListExpr x) {

    }

    @Override
    public boolean visit(SQLListExpr x) {
        return true;
    }

    @Override
    public void endVisit(SQLSubqueryTableSource x) {

    }

    @Override
    public boolean visit(SQLSubqueryTableSource x) {
        return true;
    }

    @Override
    public void endVisit(SQLTruncateStatement x) {

    }

    @Override
    public boolean visit(SQLTruncateStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLDefaultExpr x) {

    }

    @Override
    public boolean visit(SQLDefaultExpr x) {
        return true;
    }

    @Override
    public void endVisit(SQLCommentStatement x) {

    }

    @Override
    public boolean visit(SQLCommentStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLUseStatement x) {

    }

    @Override
    public boolean visit(SQLUseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableAddColumn x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableAddColumn x) {

    }

    @Override
    public boolean visit(SQLAlterTableDropColumnItem x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableDropColumnItem x) {

    }

    @Override
    public boolean visit(SQLDropIndexStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLDropIndexStatement x) {

    }

    @Override
    public boolean visit(SQLDropViewStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLDropViewStatement x) {

    }

    @Override
    public boolean visit(SQLSavePointStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLSavePointStatement x) {

    }

    @Override
    public boolean visit(SQLRollbackStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLRollbackStatement x) {

    }

    @Override
    public boolean visit(SQLReleaseSavePointStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLReleaseSavePointStatement x) {
    }

    @Override
    public boolean visit(SQLCommentHint x) {
        return true;
    }

    @Override
    public void endVisit(SQLCommentHint x) {

    }

    @Override
    public void endVisit(SQLCreateDatabaseStatement x) {

    }

    @Override
    public boolean visit(SQLCreateDatabaseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropIndex x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableDropIndex x) {

    }

    @Override
    public void endVisit(SQLOver x) {
    }

    @Override
    public boolean visit(SQLOver x) {
        return true;
    }

    @Override
    public void endVisit(SQLColumnPrimaryKey x) {

    }

    @Override
    public boolean visit(SQLColumnPrimaryKey x) {
        return true;
    }

    @Override
    public void endVisit(SQLColumnUniqueKey x) {

    }

    @Override
    public boolean visit(SQLColumnUniqueKey x) {
        return true;
    }

    @Override
    public void endVisit(SQLWithSubqueryClause x) {
    }

    @Override
    public boolean visit(SQLWithSubqueryClause x) {
        return true;
    }

    @Override
    public void endVisit(SQLWithSubqueryClause.Entry x) {
    }

    @Override
    public boolean visit(SQLWithSubqueryClause.Entry x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharacterDataType x) {
        return true;
    }

    @Override
    public void endVisit(SQLCharacterDataType x) {

    }

    @Override
    public void endVisit(SQLAlterTableAlterColumn x) {

    }

    @Override
    public boolean visit(SQLAlterTableAlterColumn x) {
        return true;
    }

    @Override
    public boolean visit(SQLCheck x) {
        return true;
    }

    @Override
    public void endVisit(SQLCheck x) {

    }

    @Override
    public boolean visit(SQLAlterTableDropForeignKey x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableDropForeignKey x) {

    }

    @Override
    public boolean visit(SQLAlterTableDropPrimaryKey x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableDropPrimaryKey x) {

    }

    @Override
    public boolean visit(SQLAlterTableDisableKeys x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableDisableKeys x) {

    }

    @Override
    public boolean visit(SQLAlterTableEnableKeys x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableEnableKeys x) {

    }

    @Override
    public boolean visit(SQLAlterTableStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableStatement x) {

    }

    @Override
    public boolean visit(SQLAlterTableDisableConstraint x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableDisableConstraint x) {

    }

    @Override
    public boolean visit(SQLAlterTableEnableConstraint x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableEnableConstraint x) {

    }

    @Override
    public boolean visit(SQLColumnCheck x) {
        return true;
    }

    @Override
    public void endVisit(SQLColumnCheck x) {

    }

    @Override
    public boolean visit(SQLExprHint x) {
        return true;
    }

    @Override
    public void endVisit(SQLExprHint x) {

    }

    @Override
    public boolean visit(SQLAlterTableDropConstraint x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableDropConstraint x) {

    }

    @Override
    public boolean visit(SQLUnique x) {
        for (SQLExpr column : x.getColumns()) {
            column.accept(this);
        }
        return false;
    }

    @Override
    public void endVisit(SQLUnique x) {

    }

    @Override
    public boolean visit(SQLCreateIndexStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLCreateIndexStatement x) {

    }

    @Override
    public boolean visit(SQLPrimaryKeyImpl x) {
        return true;
    }

    @Override
    public void endVisit(SQLPrimaryKeyImpl x) {

    }

    @Override
    public boolean visit(SQLAlterTableRenameColumn x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableRenameColumn x) {

    }

    @Override
    public boolean visit(SQLColumnReference x) {
        return true;
    }

    @Override
    public void endVisit(SQLColumnReference x) {

    }

    @Override
    public boolean visit(SQLForeignKeyImpl x) {
        return true;
    }

    @Override
    public void endVisit(SQLForeignKeyImpl x) {

    }

    @Override
    public boolean visit(SQLDropSequenceStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLDropSequenceStatement x) {

    }

    @Override
    public boolean visit(SQLDropTriggerStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLDropTriggerStatement x) {

    }

    @Override
    public void endVisit(SQLDropUserStatement x) {

    }

    @Override
    public boolean visit(SQLDropUserStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLExplainStatement x) {

    }

    @Override
    public boolean visit(SQLExplainStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLGrantStatement x) {

    }

    @Override
    public boolean visit(SQLGrantStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLDropDatabaseStatement x) {

    }

    @Override
    public boolean visit(SQLDropDatabaseStatement x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableAddIndex x) {

    }

    @Override
    public boolean visit(SQLAlterTableAddIndex x) {
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableAddConstraint x) {

    }

    @Override
    public boolean visit(SQLAlterTableAddConstraint x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLCreateTriggerStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLCreateTriggerStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLDropFunctionStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLDropFunctionStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLDropTableSpaceStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLDropTableSpaceStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLDropProcedureStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLDropProcedureStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLBooleanExpr x) {
        
    }
    
    @Override
    public boolean visit(SQLBooleanExpr x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLUnionQueryTableSource x) {
        
    }

    @Override
    public boolean visit(SQLUnionQueryTableSource x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLTimestampExpr x) {
        
    }
    
    @Override
    public boolean visit(SQLTimestampExpr x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLRevokeStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLRevokeStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLBinaryExpr x) {
        
    }
    
    @Override
    public boolean visit(SQLBinaryExpr x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableRename x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableRename x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterViewRenameStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterViewRenameStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLShowTablesStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLShowTablesStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableAddPartition x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableAddPartition x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableDropPartition x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableDropPartition x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableRenamePartition x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableRenamePartition x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableSetComment x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableSetComment x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableSetLifecycle x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableSetLifecycle x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableEnableLifecycle x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableEnableLifecycle x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableDisableLifecycle x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableDisableLifecycle x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLAlterTableTouch x) {
        
    }
    
    @Override
    public boolean visit(SQLAlterTableTouch x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLArrayExpr x) {
        
    }
    
    @Override
    public boolean visit(SQLArrayExpr x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLOpenStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLOpenStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLFetchStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLFetchStatement x) {
        return true;
    }
    
    @Override
    public void endVisit(SQLCloseStatement x) {
        
    }
    
    @Override
    public boolean visit(SQLCloseStatement x) {
        return true;
    }
    
}
