

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	        String k = request.getParameter("keyword");
	        System.out.println("keyword: " + k);
	        ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		    keywords.add(new Keyword(k, 50));
			keywords.add(new Keyword("實習", 3));
			keywords.add(new Keyword("政大", 3));
			keywords.add(new Keyword("大安區", 40));
			keywords.add(new Keyword("徵才", 5));
			keywords.add(new Keyword("人力銀行", 20));
			GoogleQuery googleQuery = new GoogleQuery(k);
			ArrayList<String> searchR = new ArrayList<String>();
			googleQuery.query();
			searchR = googleQuery.getSearchR();
		
			
			ArrayList<WebNode> nodelist=new ArrayList<WebNode>();
			for (int a = 0; a < searchR.size(); a++) {
				String url = searchR.get(a);
				
				//find children
				int x = url.indexOf("//") + 2;
				int y = url.indexOf("/", x);
				String shorturl = url.substring(0, y);
				Document doc = null;
				try {
					doc = Jsoup.connect(url).get();
				} catch (IOException e1) {
				}

				if (doc != null) {
					Element body = doc.body();
					Elements es = body.select("a");
					ArrayList<String> children = new ArrayList<String>();

					for (Iterator it = es.iterator(); it.hasNext();) {
						Element e = (Element) it.next();
						String u = e.attr("href");
						if (u.startsWith("http")) {
							children.add(u);
						} else if (u.startsWith("/")) {
							u = shorturl + u;
							children.add(u);
						}
					}

					WebPage rootPage = new WebPage(url);
					WebTree tree = new WebTree(rootPage);

					//add children
					int i = 0;
					while (i < children.size()) {
						tree.root.addChild(new WebNode(new WebPage(children.get(i))));
						i++;
					}
					tree.root.calNodeScore(keywords);
					nodelist.add(tree.root);
				} else {
					WebPage rootPage = new WebPage(url);
					WebTree tree = new WebTree(rootPage);
					tree.root.nullnodescore(keywords);
					nodelist.add(tree.root);
				}
				
			}
			
			int low = 0;
			int high = nodelist.size() - 1;
			Quicksort.quickSort(nodelist, low, high);
			
			PrintWriter writer = response.getWriter();
	        // build HTML code
	        String htmlRespone = "<html> "
	        		+ "<head>\r\n" + 
	        		"\r\n" + 
	        		"<style>\r\n" + 
	        		".Website {\r\n" + 
	        		"  background-color:yellow;\r\n" + 
	        		"  color:white;\r\n" + 
	        		"  margin: 10px;\r\n" + 
	        		"  padding: 10px;\r\n" + 
	        		"\r\n" + 
	        		"} \r\n" + 
	        		"</style>\r\n" + 
	        		"</head>"
	        		+ "<body>\r\n" + 
	        		"\r\n" + 
	        		"<font face=\"Arial\" > action=\"form_action.asp\" method=\"get\"\r\n" + 
	        		"  \r\n" + 
	        		"  Search: <input type=\"text\"  />\r\n" + 
	        		"  <input type=\"submit\" value=\"Submit\" />\r\n" + 
	        		" \r\n" + 
	        		"</font>";
			for(int i=nodelist.size()-1;i>=0;i--) {
				htmlRespone += "<div class=\"Website\">\r\n" + 
						"  <font face=\"Arial\" color=\"#E9AC38\" size=\"5\"><a href=\""
						+ nodelist.get(i).webPage.url
						+ "\">"
						+ i
						+ "</a></font>\r\n" + 
						"  <p><font face=\"Arial\" color=\"#4EB198\" size=\"2\">https://www.facebook.com/</font></p>\r\n" + 
						" </div>";   
				
			}
			
			
	        htmlRespone += "</body>\r\n" + 
	        		"</html>";      
	         
	        writer.println(htmlRespone);
		
	}

}
