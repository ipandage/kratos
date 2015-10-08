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
package com.gxl.kratos.util.sequence;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.gxl.kratos.exception.SequenceIdException;
/**
 * 生成SequenceId接口
 * 
 * @author JohnGao
 */
public class CreateSequenceIdService {
	private static CreateSequenceIdService createSequenceId;
	private CreateSequenceIdDao createUserNameDao;
	private StringBuffer str;
	private ConcurrentHashMap<Integer, Long> useDataMap;
	private ConcurrentHashMap<Integer, Integer> surplusDataMap;
	private long memData;
	private static final Logger log = LoggerFactory.getLogger(CreateSequenceIdService.class);

	protected static CreateSequenceIdService createSequenceIdService() {
		return createSequenceId;
	}

	static {
		createSequenceId = new CreateSequenceIdService();
	}

	private CreateSequenceIdService() {
		str = new StringBuffer();
		useDataMap = new ConcurrentHashMap<Integer, Long>();
		surplusDataMap = new ConcurrentHashMap<Integer, Integer>();
		createUserNameDao = new CreateSequenceIdDaoImpl();
	}

	/**
	 * 生成用户唯一的sequenceId
	 * 
	 * @author gaoxianglong
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房
	 * 
	 * @param userType
	 *            类别
	 * 
	 * @param memData
	 *            内存占位数量
	 * 
	 * @return long 根据指定规则生成的sequenceId
	 */
	protected long getSequenceId(int idcNum, int type, long memData) {
		long sequenceId = -1;
		/* 避免在并发环境下出现线程安全，则添加同步对象锁 */
		synchronized (this) {
			this.memData = memData;
			if (null != createUserNameDao) {
				try {
					/* 根据指定的类别从内存中获取剩余的占位数量 */
					Integer surplusData = surplusDataMap.get(type);
					/* 检查剩余的占位数量是否存在， 如果不存在，则创建并申请数量占位 */
					if (null != surplusData) {
						/* 检测剩余的占位数量是否已达边界 */
						if (0 < surplusData) {
							/* 更新内存中剩余的占位数量 */
							surplusDataMap.put(type, --surplusData);
							/* 根据指定的类别从内存中获取已申请的占位数量, 并计算唯一序列 */
							Long useData = useDataMap.get(type);
							if (null == useData) {
								/* 如果内存中不存在指定类别的已申请占位数量，则从数据库中获取 */
								useData = createUserNameDao.queryUseDatabyType(type);
								/* 更新内存中的已申请占位数量 */
								useDataMap.put(type, useData);
								/* 进行事物传播特性控制的事物管理 */
								transactionManager();
							} else {
								useData = useDataMap.get(type);
							}
							/* 根据指定规则创建唯一的SequenceId */
							sequenceId = createSequenceId(useData - surplusData, idcNum, type);
						} else {
							/* 生成当前占位数据 */
							Long useData = createUseData();
							/* 初始化剩余的占位数量 */
							surplusData = (int) memData;
							/* 并重新申请占位数据 */
							createUserNameDao.changeUseData(type, useData);
							/* 更新内存中的剩余占位数量 */
							surplusDataMap.put(type, --surplusData);
							/* 更新内存中的申请占位数量 */
							useDataMap.put(type, useData);
							/* 进行事物传播特性控制的事物管理 */
							transactionManager();
							/* 根据指定规则创建唯一的SequenceId */
							sequenceId = createSequenceId(useData - surplusData, idcNum, type);
						}
					} else {
						/* 生成当前占位数据 */
						Long useData = createUseData();
						/* 初始化剩余的占位数量 */
						surplusData = (int) memData;
						/* 检查当前用户类型是否存在已申请内存占位,如果不存在则申请占位数据 */
						if (null != createUserNameDao.queryUseDatabyType(type)) {
							createUserNameDao.changeUseData(type, useData);
						} else {
							createUserNameDao.insertSequenceId(type, useData);
						}
						/* 更新内存中的剩余占位数量 */
						surplusDataMap.put(type, --surplusData);
						/* 更新内存中的申请占位数量 */
						useDataMap.put(type, useData);
						/* 进行事物传播特性控制的事物管理 */
						transactionManager();
						/* 根据指定规则创建唯一的SequenceId */
						sequenceId = createSequenceId(useData - surplusData, idcNum, type);
					}
				} catch (SQLException e) {
					try {
						CreateSequenceIdDaoImpl.conn.rollback();
					} catch (SQLException e1) {
						log.error("生成SequenceId回滚数据失败", e1);
					}
					e.printStackTrace();
					throw new SequenceIdException("生成sequenceId失败...");
				} finally {
					try {
						CreateSequenceIdDaoImpl.conn.close();
					} catch (SQLException e) {
						log.error("生成SequenceId关闭连接失败", e);
					}
				}
			}
		}
		return sequenceId;
	}

	/**
	 * 进行事物传播特性控制的事物管理
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception SQLException
	 * 
	 * @return void
	 */
	protected void transactionManager() {
		try {
			CreateSequenceIdDaoImpl.conn.commit();
		} catch (SQLException e) {
			log.error("生成SequenceId事物管理失败", e);
		}
	}

	/**
	 * 根据指定规则创建唯一的userName
	 * 
	 * @author gaoxianglong
	 * 
	 * @param id
	 *            唯一序列
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房,3位长度
	 * 
	 * @param userType
	 *            类别,2位长度
	 * 
	 * @return long 返回生成的17-19位sequenceId
	 */
	protected long createSequenceId(Long id, int idcNum, int type) {
		long sequenceId = -1;
		/* IDC机房编码不能够超过3位,type不能够超过2位 */
		if (idcNum < 1000 && type < 100) {
			/* 清空历史数据 */
			if (null != str)
				str.delete(0, str.length());
			final int length = 14 - String.valueOf(id).length();
			for (int i = 0; i < length; i++)
				str.append(0);
			str.append(id);
			str.insert(0, idcNum);
			str.insert(getIndex(idcNum), type < 10 ? "0" + type : type);
			sequenceId = Long.parseLong(str.toString());
		} else {
			throw new SequenceIdException("IDC机房编码不能够超过3位长度,type不能够超过2位长度...");
		}
		return sequenceId;
	}

	/**
	 * 生成当前占位数据
	 * 
	 * @author gaoxianglong
	 * 
	 * @throws SQLException
	 * 
	 * @return long 返回生成的当前占位数据
	 */
	protected long createUseData() throws SQLException {
		/* 获取当前最大的占位数据 */
		Long useData = createUserNameDao.queryMaxUseData();
		return null != useData ? useData += memData : memData;
	}

	/**
	 * 计算预留字段的占位索引
	 * 
	 * @author EX-GAOXIANGLONG001
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房
	 * 
	 * @return int 返回占位索引
	 */
	protected int getIndex(int idcNum) {
		int index = 1;
		if (idcNum >= 100)
			index = 3;
		else if (idcNum >= 10)
			index = 2;
		return index;
	}
}