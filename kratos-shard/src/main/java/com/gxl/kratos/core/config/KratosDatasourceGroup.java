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
package com.gxl.kratos.core.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.gxl.kratos.core.shard.KratosJdbcTemplate;

/**
 * kratos数据源路由实现，持有多数据源
 * 
 * 该数据源继承自Spring提供的AbstractRoutingDataSource，可以根据配置文件中的数据源key对多数据源进行动态切换，
 * 能够非常方便的实现数据源路由工作
 * 
 * @author gaoxianglong
 */
public class KratosDatasourceGroup extends AbstractRoutingDataSource implements DataSource {
	private Logger logger = LoggerFactory.getLogger(KratosDatasourceGroup.class);
	@Resource
	private DataSourceHolder dataSourceHolder;

	private KratosDatasourceGroup() {
		logger.info("动态数据源已经初始化完成...");
	}

	@Override
	protected Object determineCurrentLookupKey() {
		int index = -1;
		if (null != dataSourceHolder)
			index = dataSourceHolder.getIndex();
		return index;
	}
}