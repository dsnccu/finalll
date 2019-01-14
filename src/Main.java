

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) throws IOException {

		System.out.println("Please Enter Your Keyword: ");
		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			ArrayList<Keyword> keywords = new ArrayList<Keyword>();
			String keyword = sc.next();
			keywords.add(new Keyword(keyword, 50));
			keywords.add(new Keyword("撌乩��", 3));
			keywords.add(new Keyword("撖衣��", 3));
			keywords.add(new Keyword("�憭�", 40));
			keywords.add(new Keyword("敺菜��", 5));
			keywords.add(new Keyword("鈭箏��銵�", 20));

			GoogleQuery googleQuery = new GoogleQuery(keyword);

			// ������
			ArrayList<String> searchR = new ArrayList<String>();
			googleQuery.query();
			searchR = googleQuery.getSearchR();

			
			ArrayList<WebNode> nodelist=new ArrayList<WebNode>();
			for (int a = 0; a < searchR.size(); a++) {
				System.out.println("");
				System.out.println(a + ": " + searchR.get(a));

				// �撠酋
				String url = searchR.get(a);
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
			for(int i=nodelist.size()-1;i>=0;i--) {
				System.out.println(nodelist.get(i).nodeScore);
			}
		}
		sc.close();

	}

}
