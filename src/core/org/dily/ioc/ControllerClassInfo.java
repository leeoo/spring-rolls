package org.dily.ioc;

/**
 * 功能描述(Description):<br>
 * <b> Controller被注入到容器中的类信息 </b><br>
 * 作 者(Author): <i><b>曹阳(Cao.Yang)</b></i> <br>
 * 建立日期(Create Date): <b>2011-4-13下午08:46:23</b> <br>
 * 项目名称(Project Name): <b>dily</b> <br>
 * 包及类名(Package Class): <b>org.dily.ioc.ControllerClassInfo.java</b>
 */
public class ControllerClassInfo{

	private String namespace;
	
	private boolean single = true;
	
	private Class<?> clazz = null;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

}
