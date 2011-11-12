package org.paramecium.transaction;

/**
 * 功能描述(Description):<br><b>
 * 事务拦截器中拦截到的异常
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-11-12下午03:52:58</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.transaction.TransactionException.java</b>
 */
public class TransactionException extends RuntimeException{

	private static final long serialVersionUID = -7612102158976946533L;

	public TransactionException(String message) {
   	 	super(message);
	}
	
}
