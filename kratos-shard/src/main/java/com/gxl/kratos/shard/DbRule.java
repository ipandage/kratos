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
package com.gxl.kratos.shard;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.gxl.kratos.core.KratosJdbcTemplate;

/**
 * 解析分库规则后计算分库索引
 * 
 * @author gaoxianglong
 */
@Component
public class DbRule extends ShardResolverImpl {
	@Resource
	private KratosJdbcTemplate kJdbcTemplate;

	@Override
	public int getIndex(long key, String ruleArray) {
		int dbIndex = -1;
		/* 覆盖分库分表规则的具体值,比如"#userinfo_id|email_hash# % dbSize",覆盖后为"#1000# % dbSize" */
		String cover = ruleArray.replace(ruleArray.split("\\#")[1], String.valueOf(key));
		if (kJdbcTemplate.getShardMode()) {
			/* 调用一库一片模式下配置文件中的分库规则 */
			dbIndex = dbResolver_oneTb(cover);
		} else {
			/* 调用库内分片模式下配置文件中的分库规则 */
			dbIndex = dbResolver(cover);
		}
		return dbIndex;
	}
}