package com.wisdom.core.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
/**
 * <b>业务说明</b>:单元测试用测试继承类，专为普通数据操作业务以及权限相关模块做测试<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-17<br>
 * <b>建立时间</b>: 下午08:47:40<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.testSpringTestCase.java<br>
 */
@ContextConfiguration(locations = { "/applicationContext.xml"})
public abstract class SpringTestCase extends AbstractJUnit38SpringContextTests {

}
