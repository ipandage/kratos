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

import org.springframework.stereotype.Component;

import com.gxl.kratos.jdbc.exception.ShardException;

/**
 * 解析分表规则后计算分表索引
 * 
 * @author gaoxianglong
 */
@Component
public class TbRule extends ShardResolverImpl {
	@Override
	public int getIndex(long key, String ruleArray) {
		/*
		 * 覆盖分库分表规则的具体值,比如"#userinfo_id# % tbSize % dbSize",覆盖后为
		 * "#1000# % tbSize % dbSize"
		 */
		String cover = ruleArray.replace(ruleArray.split("\\#")[1], String.valueOf(key));
		/* 调用解析分库规则 */
		return tbResolver(cover);
	}
}