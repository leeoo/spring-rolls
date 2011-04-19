import java.sql.SQLException;

import org.h2.tools.Server;


public class DBStart {
	public static void main(String[] args) throws SQLException {
		Server server = Server.createTcpServer(new String[] { "-tcpPort", "9101" });
        server.start();
	}
}
