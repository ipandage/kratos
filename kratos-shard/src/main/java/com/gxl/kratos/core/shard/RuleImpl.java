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
package com.gxl.kratos.core.shard;

import java.util.List;
import com.gxl.kratos.util.ResolveRule;

/**
 * 路由规则接口实现
 * 
 * @author gaoxianglong
 */
public abstract class RuleImpl implements Rule {
	@Override
	public int getIndex(long route, String ruleArray) {
		return -1;
	}

	/**
	 * 根据库内分片模式下的分库规则计算出数据源索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param routeValue
	 *            路由条件
	 * 
	 * @param dbRuleArray
	 *            分库规则
	 * 
	 * @return int 数据源索引
	 */
	public static int getDbIndex(long routeValue, String dbRuleArray) {
		List<Integer> list = ResolveRule.resolveDbRule(dbRuleArray);
		final int TAB_SIZE = list.get(0);
		final int DB_SIZE = list.get(1);
		return (int) (routeValue % TAB_SIZE / DB_SIZE);
	}

	/**
	 * 根据库内分片模式下的分片规则计算出片索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param routeValue
	 *            路由条件
	 * 
	 * @param tbRuleArray
	 *            分表规则
	 * 
	 * @return int 片索引
	 */
	public static int getTabIndex(long routeValue, String tbRuleArray) {
		List<Integer> list = ResolveRule.resolveTabRule(tbRuleArray);
		final int TAB_SIZE = list.get(0);
		final int DB_SIZE = list.get(1);
		return (int) (routeValue % TAB_SIZE % DB_SIZE);
	}

	/**
	 * 根据一库一片模式下的分库规则计算出数据源索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param routeValue
	 *            路由条件
	 * 
	 * @param dbRuleArray
	 *            分库规则
	 * 
	 * @return int 数据源索引
	 */
	public static int getDbIndexbyOne(long routeValue, String dbRuleArray) {
		List<Integer> list = ResolveRule.resolveDbRulebyOne(dbRuleArray);
		final int DB_SIZE = list.get(0);
		return (int) (routeValue % DB_SIZE);
	}
}