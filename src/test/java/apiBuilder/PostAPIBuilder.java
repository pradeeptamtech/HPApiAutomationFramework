package apiBuilder;

import java.util.HashMap;
import java.util.Map;

public class PostAPIBuilder {

	public Map<String,String> postRequestBody(String id,String title,String author)
	{
		Map<String,String> body = new HashMap<String,String>();
	    body.put("id", id);
	    body.put("title", title);
	    body.put("author", author);   
  
	    return body;	    
	}
	
	
public Map<String,String> reqbin_PostApieReuestBody(String id,String customer,String quantity,String price)
	{
		Map<String,String> body = new HashMap<String,String>();
	    body.put("Id", id);
	    body.put("Customer", customer);
	    body.put("Quantity", quantity); 
	    body.put("Price", price);
  
	    return body;	    
	}
	
}
