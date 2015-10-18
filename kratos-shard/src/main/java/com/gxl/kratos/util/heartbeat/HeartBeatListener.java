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
package com.gxl.kratos.util.heartbeat;

/**
 * 数据库心跳定时器,如果主库宕机就调用shell脚本启用从库作为主库,并关闭读写分离功能,待主库恢复后,再还之前的状态
 * 
 * @author gaoxianglong
 */
public interface HeartBeatListener {
}