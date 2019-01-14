
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WordCounter {
	public String urlStr;
	public String content;
	
	public WordCounter(String urlStr) {
		
		if(hasChinese(urlStr)==0) {
			this.urlStr=urlStr;
		}else {
		String docname = urlStr.substring(urlStr.lastIndexOf("/") + 1);

		try {
			urlStr = urlStr.substring(0,urlStr .lastIndexOf("/"))+"/"+URLEncoder.encode(docname,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.urlStr=urlStr;
		}

	}

	
    

	public int hasChinese(String url) {
	int ans=0;
 
	for(int i=0; i<url.length();i++){
	{  
	    String test = url.substring(i, i+1);  
	    if(test.matches("[\\u4E00-\\u9FA5]+"))  
	    {  
	        ans++;
	    }  	
	}
	}
	return ans;
	}

	
	
	private String fetchContent() throws Exception {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in=conn.getInputStream(); 
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String retVal=""; 
		String line=null; 
		
		while((line=br.readLine())!=null) {
			retVal = retVal+line+"/n";
		}
		
		return retVal;
	}
	
	
	
	
	public double countKeyword(String keyword) {
		if (content == null) {
			try {
				content = fetchContent();
			} catch (Exception e) {
			}
		}
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int count = 0;
		int i = content.indexOf(keyword);
		while (i != -1) {
			count++;
			content = content.substring(i + keyword.length(), content.length());
			i = content.indexOf(keyword);
		}
		return count;
	}
	
}

