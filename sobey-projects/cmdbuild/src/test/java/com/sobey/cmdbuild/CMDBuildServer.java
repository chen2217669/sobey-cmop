package com.sobey.cmdbuild;

import org.eclipse.jetty.server.Server;

import com.sobey.test.jetty.JettyFactory;
import com.sobey.test.spring.Profiles;

/**
 * 使用Jetty运行调试Web应用, 在Console快速重载应用.
 * 
 * @author Administrator
 * 
 */
public class CMDBuildServer {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/cmdbuild";
	public static final String[] TLD_JAR_NAMES = new String[] { "cmdbuild", "iaas", "webservice" };

	public static void main(String[] args) throws Exception {

		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

		// 启动Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

		try {
			System.out.println("[HINT] Don't forget to set -XX:MaxPermSize=128m");

			server.start();
			System.out.println("Server running at http://localhost:" + PORT + CONTEXT);
			System.out.println("[HINT] Hit Enter to reload the application quickly");

			// 等待用户输入回车重载应用.
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
