import com.wisdom.core.web.TomcatServer;

/**
 * 功能描述(Description):<br><b>
 * 嵌入式web服务器启动
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-1-26下午09:34:02</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>.WebStart.java</b>
 */

public class WebStart {

	public static void main(String[] args) throws Exception {
		 new TomcatServer(null,"/WebRoot",80,"UTF-8").start();
	}

}
