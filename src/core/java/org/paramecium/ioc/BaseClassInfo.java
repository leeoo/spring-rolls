package org.paramecium.ioc;

/**
 * 功能描述(Description):<br>
 * <b> IOC注入类基本信息 </b><br>
 * 作 者(Author): <i><b>曹阳(Cao.Yang)</b></i> <br>
 * 建立日期(Create Date): <b>2011-4-13下午08:54:20</b> <br>
 * 项目名称(Project Name): <b>paramecium</b> <br>
 * 包及类名(Package Class): <b>org.paramecium.ioc.BaseClassInfo.java</b>
 */
public abstract class BaseClassInfo {

	private Class<?> clazz = null;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

}
