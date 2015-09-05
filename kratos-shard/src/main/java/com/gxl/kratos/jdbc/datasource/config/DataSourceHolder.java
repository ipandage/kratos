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
package com.gxl.kratos.jdbc.datasource.config;

/**
 * 数据源路由选择器接口
 * 
 * @author gaoxianglong
 */
public interface DataSourceHolder {
	/**
	 * 设置数据源路由索引
	 * 
	 * @author JohnGao
	 * 
	 * @param index
	 *            数据源路由索引
	 * 
	 * @return void
	 */
	public void setRoutingIndex(int index);

	/**
	 * 获取数据源路由索引
	 * 
	 * @author JohnGao
	 * 
	 * @return int 数据源路由索引
	 */
	public int getRoutingIndex();
}