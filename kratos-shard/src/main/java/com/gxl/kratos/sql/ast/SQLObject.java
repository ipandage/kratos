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
package com.gxl.kratos.sql.ast;

import java.util.List;
import java.util.Map;

import com.gxl.kratos.sql.visitor.SQLASTVisitor;

public interface SQLObject {

    void accept(SQLASTVisitor visitor);

    SQLObject getParent();

    void setParent(SQLObject parent);

    Map<String, Object> getAttributes();

    Object getAttribute(String name);

    void putAttribute(String name, Object value);

    Map<String, Object> getAttributesDirect();
    
    void addBeforeComment(String comment);
    void addBeforeComment(List<String> comments);
    
    List<String> getBeforeCommentsDirect();
    
    void addAfterComment(String comment);
    
    void addAfterComment(List<String> comments);
    
    List<String> getAfterCommentsDirect();
    
    boolean hasBeforeComment();
    
    boolean hasAfterComment();

    void output(StringBuffer buf);
}
