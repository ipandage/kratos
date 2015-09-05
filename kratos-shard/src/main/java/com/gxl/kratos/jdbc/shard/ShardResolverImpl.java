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
package com.gxl.kratos.jdbc.shard;

import com.gxl.kratos.jdbc.exception.ShardException;

/**
 * 分库分表解析接口实现
 * 
 * @author gaoxianglong
 */
public abstract class ShardResolverImpl implements ShardResolver {
	@Override
	public int getIndex(long key, String ruleArray) {
		return 0;
	}

	/**
	 * 解析库内分片模式下配置文件中的分库规则
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbRuleArray
	 *            分库规则
	 * 
	 * @return int 分库索引
	 */
	protected static int dbResolver(String dbRuleArray) {
		long key = Long.parseLong(dbRuleArray.split("\\#")[1]);
		int tbSize = Integer.parseInt(dbRuleArray.substring(dbRuleArray.indexOf("%") + 1, dbRuleArray.indexOf("/")));
		int dbSize = Integer.parseInt(dbRuleArray.substring(dbRuleArray.indexOf("/") + 1));
		/* kratos的分库规则 */
		return (int) (key % tbSize / dbSize);
	}

	/**
	 * 解析库内分片模式下配置文件中的分表规则
	 * 
	 * @author gaoxianglong
	 * 
	 * @param tbRuleArray
	 *            分表规则
	 * 
	 * @return int 分表索引
	 */
	protected static int tbResolver(String tbRuleArray) {
		String values[] = tbRuleArray.split("[\\%]");
		long key = Long.parseLong(tbRuleArray.split("\\#")[1]);
		int tbSize = Integer.parseInt(values[1]);
		int dbSize = Integer.parseInt(values[2]);
		/* kratos的分表规则 */
		return (int) (key % tbSize % dbSize);
	}

	/**
	 * 解析一库一片模式下配置文件中的分库规则
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbRuleArray
	 *            分库规则
	 * 
	 * @return int 分库索引
	 */
	protected static int dbResolver_oneTb(String dbRuleArray) {
		long key = Long.parseLong(dbRuleArray.split("\\#")[1]);
		int dbSize = Integer.parseInt(dbRuleArray.substring(dbRuleArray.indexOf("%") + 1));
		/* kratos的分库规则 */
		return (int) (key % dbSize);
	}
}