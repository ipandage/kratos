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
 * See the License for the specific language governing permissions AND
 * limitations under the License.
 */
package com.gxl.kratos.jdbc.shard;

import org.junit.Test;
import junit.framework.Assert;

/**
 * 分库分表解析测试类
 * 
 * @author gaoxianglong
 */
public class ShardResolverTest {
	private ShardResolverImpl shardResolver;

	/**
	 * 测试解析库内分片模式下配置文件中的分库规则
	 * 
	 * @author gaoxianglong
	 */
	@SuppressWarnings("static-access")
	public @Test void testDbResolver() {
		Assert.assertEquals(10000 % 1024 / 32, shardResolver.dbResolver("#10000#%1024/32"));
	}

	/**
	 * 测试解析库内分片模式下配置文件中的分表规则
	 * 
	 * @author gaoxianglong
	 */
	@SuppressWarnings("static-access")
	public @Test void testTbResolver() {
		Assert.assertEquals(10000 % 1024 % 32, shardResolver.tbResolver("#10000#%1024%32"));
	}

	/**
	 * 测试解析一库一片模式下配置文件中的分库规则
	 * 
	 * @author gaoxianglong
	 */
	@SuppressWarnings("static-access")
	public @Test void testDbResolver_oneTb() {
		Assert.assertEquals(10000 % 32, shardResolver.dbResolver_oneTb("#10000#%32"));
	}
}