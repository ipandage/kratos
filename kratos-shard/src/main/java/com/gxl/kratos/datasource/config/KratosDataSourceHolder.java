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
package com.gxl.kratos.datasource.config;

import org.springframework.stereotype.Component;

/**
 * master数据源路由选择器
 * 
 * @author gaoxianglong
 */
@Component("dataSourceHolder")
public class KratosDataSourceHolder implements DataSourceHolder {
	@SuppressWarnings("unused")
	private static final ThreadLocal<Integer> holder;

	static {
		holder = new ThreadLocal<Integer>();
	}

	@Override
	public void setRoutingIndex(int index) {
		holder.set(index);
	}

	@Override
	public int getRoutingIndex() {
		return holder.get();
	}
}