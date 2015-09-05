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
package com.gxl.kratos.jdbc.mysql.sql.parser;

/**
 * mysql中常用的Sql关键字
 * 
 * @author gaoxianglong
 */
public class Token {
	public static final String SELECT = "select";
	public static final String FROM = "from";
	public static final String WHERE = "where";
	public static final String AND = "and";
	public static final String OR = "or";
	public static final String INSERT = "insert";
	public static final String INTO = "into";
	public static final String VALUES = "values";
	public static final String UPDATE = "update";
	public static final String SET = "set";
	public static final String DELETE = "delete";
	public static final String EQ = "=";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String SEMI = ";";
	public static final String GT = ">";
	public static final String LT = "<";
	public static final String BANG = "!";
	public static final String QUES = "?";
	public static final String BAR = "|";
	public static final String LPAREN = "(";
	public static final String RPAREN = ")";
}