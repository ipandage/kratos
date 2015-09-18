package com.gxl.kratos.utils.xml;

import java.io.File;
import com.gxl.kratos.utils.exception.UtilsException;

/**
 * 生成数据源配置文件
 * 
 * @author gaoxianglong
 */
public interface CreateDSXml {
	/**
	 * 生成kratos的数据源信息配置文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @return boolean 生成结果
	 */
	public boolean createDatasourceXml(File savePath);
}