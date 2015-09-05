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

/**
 * 分库分表解析接口
 * 
 * @author gaoxianglong
 */
public interface ShardResolver {
	/**
	 * 获取分库索引/分表索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param key
	 *            分库分表条件
	 * 
	 * @param ruleArray
	 *            分库分表规则
	 * 
	 * @throws ShardException
	 * 
	 * @return int 分库索引/分表索引
	 */
	public int getIndex(long key, String ruleArray);
}