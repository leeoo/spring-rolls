package org.paramecium.ioc;

/**
 * 功能描述(Description):<br>
 * <b> Service被注入到容器中的类信息 </b><br>
 * 作 者(Author): <i><b>曹阳(Cao.Yang)</b></i> <br>
 * 建立日期(Create Date): <b>2011-4-13下午08:44:49</b> <br>
 * 项目名称(Project Name): <b>paramecium</b> <br>
 * 包及类名(Package Class): <b>org.paramecium.ioc.ServiceClassInfo.java</b>
 */
public class ServiceClassInfo extends BaseClassInfo {

	private String uniqueName;
	private boolean isTransactional;

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public boolean isTransactional() {
		return isTransactional;
	}

	public void setTransactional(boolean isTransactional) {
		this.isTransactional = isTransactional;
	}

}
