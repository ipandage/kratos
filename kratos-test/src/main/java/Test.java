
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author JohnGao
 *
 */
public class Test {
	static final String DOWNTIME = "java.net.ConnectException: Connection refused: connect";
	static final String TIMEOUT = "java.net.SocketException: Connection attempt exceeded defined timeout";
	static int num = 0;

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DriverManager.setLoginTimeout(1);
			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://ip:3306", "root", "88888888");
			if (null != conn)
				conn.close();
		} catch (Exception e) {
			Writer write = new StringWriter();
			e.printStackTrace(new PrintWriter(write));
			String error_info = write.toString();
			if (3 == num) {
				System.out.println(TIMEOUT);
			} else if (error_info.indexOf(TIMEOUT) != -1) {
				num++;
				try {
					Thread.sleep(2000);
					run();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} else if (error_info.indexOf(DOWNTIME) != -1) {
				System.out.println(DOWNTIME);
			}
		}
	}
}