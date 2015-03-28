开发背景：

> 经过多年的Spring Struts Hibernate（以下简称SSH）等框架的整合使用，版本差异及兼容性一直都是整合的障碍。基于SSH开发的项目jar依赖包过多，启动速度慢，运行时资源消耗较多。
> 2009年末，spring3已初见端倪，提供很多轻量级的解决方案，其jdbc template略微封装后，即可方便使用，加入自定义@注解既可像hiberante和ibatis一样。而mvc则是一次重大改革，是传统struts，webwork，jsf等mvc框架的终结者。真正意义的零配置，稍作配置既可以轻松实现REST风格。

开发时间：

> 2009年10月01日

版本阶段：

> 开发阶段 2009-10-01 至 2010-02-01

> 试用阶段 2010-03-01 至 2010-04-01

> 调试阶段 2010-04-01 至 2010-05-01

特点：
> 采用spring 3.0+ 为基础，扩展封装相关内容，目前spring版本为3.1.0 M1，security版本为3.1.0 RC1最新版本。
> 封装后的jdbctemplate支持MYSQL,ORACLE,DB2,SQLSERVER2000/2K+,SQLITE,H2,DERBY,POSTGRESQL等主流数据库。

> 目前计划加入表拆分功能，提供对大数据量进行水平分割。