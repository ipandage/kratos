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
package com.gxl.kratos.test.shard;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.gxl.kratos.core.KratosJdbcTemplate;
import com.gxl.kratos.utils.sql.PropertyPlaceholderConfigurer;

/**
 * email反向索引表Dao接口
 * 
 * @author gaoxianglong
 */
@Repository("emailDao")
public class EmailDaoImpl implements EmailDao {
	@Resource
	private KratosJdbcTemplate kJdbcTemplate;

	@Resource
	private PropertyPlaceholderConfigurer property;

	@Resource
	private EmailMapper emailMapper;

	@Override
	public void insertEmail(Email email) throws Exception {
		final String SQL = property.getSql("insertEmail", email.getEmail_hash());
		kJdbcTemplate.update(SQL, new Object[] { email.getEmail(), email.getUserinfo_Id() });
	}

	@Override
	public List<Email> queryEmailbyId(Email email) throws Exception {
		final String SQL = property.getSql("queryEmailbyId", email.getEmail_hash());
		return kJdbcTemplate.query(SQL, new Object[] { email.getEmail() }, emailMapper);
	}
}