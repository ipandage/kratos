![](http://dl.iteye.com/upload/picture/pic/133973/d30fc066-9cb2-369e-bcae-5a729733c683.jpg)
## kratos简介 [![Build Status](https://api.travis-ci.org/biezhi/blade.svg?branch=master)]()  [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

轻量级Mysql分库分表(Sharding)中间件，丰富的Sharding算法支持(2类4种分片算法)，能够方便DBA实现库的水平扩容和降低数据迁移成本。Kratos站在巨人的肩膀上(SpringJdbc、Druid)，采用与应用集成架构，放弃通用性，只为换取更好的执行性能与降低分布式环境下外围系统的宕机风险。<br>

- [使用手册](https://github.com/gaoxianglong/kratos/wiki)<br>
- [常见问题](https://github.com/gaoxianglong/kratos/wiki/常见问题)<br>

----------

## kratos的优点
- 动态数据源的无缝切换；<br>
- master/slave一主一从读写分离；<br>
- Sql独立配置，与逻辑代码解耦；<br>
- 单线程读重试(取决于的数据库连接池是否支持)；<br>
- 友好支持Mysql数据库；<br>
- 非Proxy架构，与应用集成，应用直连数据库，降低外围系统依赖带来的宕机风险；<br>
- 使用简单，侵入性低，站在巨人的肩膀上，依赖于SpringJdbc、Druid；<br>
- 基于淘宝Druid的SqlParser完成Sql解析任务，解析性能高效、稳定；<br>
- 分库分表路由算法支持2类4种分片模式，库内分片/一库一片；<br>
- 提供自动生成全局唯一的sequenceId的API支持；<br>
- 提供自动生成配置文件的支持，降低配置出错率；<br>
- 提供内置验证页面，方便开发、测试以及运维对执行后的sql进行验证；<br>
- 目标和职责定位明确，仅专注于Sharding，不支持其它多余或鸡肋功能、无需兼容通用性，因此核心代码量少、易读易维护；<br>

----------

## kratos的分片模型
##### kratos支持2类4种分片算法：
- 库内分片类型：
  - 片名连续的库内分片算法；
  - 非片名连续的库内分片算法；
- 一库一片类型：
  - 片名连续的一库一片算法；
  - 非片名连续的一库一片算法；

----------

## kratos的使用注意事项
- 不支持强一致性的分布式事务，但可以在业务层依赖MQ、异步操作的方式实现事物，保证最终一致性即可；
- 不建议、不支持多表查询，所有多表查询sql，务必全部打散为单条sql逐条执行；
- 不建议使用一些数据库统计函数、Order by语句等；
- sql语句的第一个参数务必是路由条件；
- 路由条件必须是整数类型；
- 不支持sql语句中出现数据库别名；
- 在连续分片模式下，子表后缀为符号"_"+4位整型，比如“tb_0001”——"tb_1024"；

----------

## 学习 & 联系我们
- 项目主页：https://github.com/gaoxianglong/kratos
- Wiki：https://github.com/gaoxianglong/kratos/wiki
- Issues：https://github.com/gaoxianglong/kratos/issues
- QQ Group:150445731
- Blog：http://gao-xianglong.iteye.com
- [![Join the chat at https://gitter.im/gaoxianglong/kratos](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/gaoxianglong/kratos?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

----------
