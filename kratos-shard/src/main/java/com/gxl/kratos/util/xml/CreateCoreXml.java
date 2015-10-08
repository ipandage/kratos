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
package com.gxl.kratos.util.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * 生成kratos核心配置文件
 * 
 * @author gaoxianglong
 */
public class CreateCoreXml {
	private String isShard;
	private String wr_weight;
	private String dbRuleArray;
	private String tbRuleArray;
	private String dbSize;
	private String consistent;
	private String shardMode;
	private String sqlPath;
	private boolean isShow;
	private Marshaller marshaller;

	public CreateCoreXml() {
		try {
			JAXBContext jAXBContext = JAXBContext.newInstance(Beans.class);
			marshaller = jAXBContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成kratos的核心信息配置文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception Exception
	 * 
	 * @return boolean 生成结果
	 */
	public boolean createCoreXml(File savePath) {
		boolean result = false;
		try {
			/* 创建配置文件的根目录<beans/>标签 */
			Beans beans = new Beans();
			/* 创建<bean/>子标签 */
			Bean bean1 = new Bean();
			bean1.setId("kJdbcTemplate");
			bean1.setClass_("com.gxl.kratos.jdbc.core.KratosJdbcTemplate");
			/* 创建<property/>子标签 */
			Property dataSource = new Property();
			dataSource.setName("dataSource");
			dataSource.setRef("kDataSourceGroup");
			Property wr_weight = new Property();
			wr_weight.setName("wr_weight");
			wr_weight.setValue(this.getWr_weight());
			Property shardMode = new Property();
			shardMode.setName("shardMode");
			shardMode.setValue(this.isShardMode());
			Property consistent = new Property();
			consistent.setName("consistent");
			consistent.setValue(this.getConsistent());
			Property dbRuleArray = new Property();
			dbRuleArray.setName("dbRuleArray");
			dbRuleArray.setValue(this.getDbRuleArray());
			Property tbRuleArray = new Property();
			tbRuleArray.setName("tbRuleArray");
			tbRuleArray.setValue(this.getTbRuleArray());
			List<Property> propertys = new ArrayList<Property>();
			propertys.add(dataSource);
			propertys.add(wr_weight);
			propertys.add(shardMode);
			propertys.add(consistent);
			propertys.add(dbRuleArray);
			propertys.add(tbRuleArray);
			bean1.setProperty(propertys);
			/* 创建<constructor_arg/>子标签 */
			ConstructorArg constructor_arg = new ConstructorArg();
			constructor_arg.setName("isShard");
			constructor_arg.setValue(this.isShard());
			bean1.setConstructor_arg(constructor_arg);
			/* 创建<bean/>子标签 */
			Bean bean2 = new Bean();
			bean2.setId("kDataSourceGroup");
			bean2.setClass_("com.gxl.kratos.jdbc.datasource.config.KratosDatasourceGroup");
			/* 创建<property/>子标签 */
			Property targetDataSources = new Property();
			targetDataSources.setName("targetDataSources");
			/* 创建<map/>子标签 */
			Map map = new Map();
			map.setKey_type("java.lang.Integer");
			List<Entry> entrys = new ArrayList<Entry>();
			for (int i = 0; i < Integer.parseInt(dbSize); i++) {
				/* 创建<entry/>子标签 */
				Entry entry = new Entry();
				entry.setKey(String.valueOf(i));
				entry.setValue_ref("dataSource" + (i + 1));
				entrys.add(entry);
			}
			map.setEntry(entrys);
			targetDataSources.setMap(map);
			propertys = new ArrayList<Property>();
			propertys.add(targetDataSources);
			bean2.setProperty(propertys);
			/* 创建<bean/>子标签 */
			Bean bean3 = new Bean();
			bean3.setId("property");
			bean3.setClass_("com.gxl.kratos.utils.sql.PropertyPlaceholderConfigurer");
			/* 创建<constructor_arg/>子标签 */
			ConstructorArg constructor_arg1 = new ConstructorArg();
			constructor_arg1.setName("path");
			constructor_arg1.setValue(this.getSqlPath());
			bean3.setConstructor_arg(constructor_arg1);
			List<Bean> beanList = new ArrayList<Bean>();
			beanList.add(bean1);
			beanList.add(bean2);
			beanList.add(bean3);
			beans.setBean(beanList);
			if (this.getIsShow())
				marshaller.marshal(beans, System.out);
			marshaller.marshal(beans, savePath);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}

	public String isShard() {
		return isShard;
	}

	public void setShard(String isShard) {
		this.isShard = isShard;
	}

	public String getWr_weight() {
		return wr_weight;
	}

	public void setWr_weight(String wr_weight) {
		this.wr_weight = wr_weight;
	}

	public String isShardMode() {
		return shardMode;
	}

	public void setShardMode(String shardMode) {
		this.shardMode = shardMode;
	}

	public String getDbRuleArray() {
		return dbRuleArray;
	}

	public void setDbRuleArray(String dbRuleArray) {
		this.dbRuleArray = dbRuleArray;
	}

	public String getTbRuleArray() {
		return tbRuleArray;
	}

	public void setTbRuleArray(String tbRuleArray) {
		this.tbRuleArray = tbRuleArray;
	}

	public String getDbSize() {
		return dbSize;
	}

	public void setDbSize(String dbSize) {
		this.dbSize = dbSize;
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public String getConsistent() {
		return consistent;
	}

	public void setConsistent(String consistent) {
		this.consistent = consistent;
	}

	public String getSqlPath() {
		return sqlPath;
	}

	public void setSqlPath(String sqlPath) {
		this.sqlPath = sqlPath;
	}
}