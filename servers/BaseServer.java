import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class BaseServer {
	// TODO cleanup

	private final int port;
	// private final String baseLink;
	private final ConcurrentIndex index;
	private final ConcurrentWebCrawler crawler;

	public BaseServer(int port, ConcurrentIndex index, ConcurrentWebCrawler crawler) {
		this.port = port;
		// this.baseLink = baseLink;
		this.index = index;
		this.crawler = crawler;
	}

	public void startServer() {
		Server server = new Server(port);

		ServletHandler handler = new ServletHandler();

		handler.addServletWithMapping(new ServletHolder(new HomeServlet(crawler)), "/");
		handler.addServletWithMapping(new ServletHolder(new LoginUserServlet()), "/login");
		handler.addServletWithMapping(new ServletHolder(new ResultsServlet(index)), "/search");
		handler.addServletWithMapping(new ServletHolder(new LoginRegisterServlet()), "/register");
		handler.addServletWithMapping(new ServletHolder(new VisitRecordServlet()), "/leaving");

		server.setHandler(handler);
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}