package com.wooribank.blockchain.verifiablecredential.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;


public class MessageView extends AbstractView{
	
	String message;
	String script;
	String location;
	
	public MessageView(){
		setContents();
	}
	public MessageView(String message, String script, String location){
		setContents();
		this.message = message;
		this.script = script;
		this.location = location;
	}
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType(getContentType());
		PrintWriter out = null;
		StringBuilder sb = new StringBuilder();
		try {
			
			sb.append("<html>");
			sb.append("<script type='text/javascript'>");
			if(!"".equals(this.message)){
				sb.append("alert(\"").append(this.message).append("\");");
			}
			if(!"".equals(location)){
				sb.append("location.href = \"").append(this.location).append("\";");
			}
			if(!"".equals(this.script)){
				sb.append(this.script).append(";");
			}		
			
			sb.append("</script>");
			sb.append("</html>");
			
			out = response.getWriter();
			out.println(sb.toString());
			out.flush();
		} catch (Exception e) {
			
		} finally {
			if (out != null) {
				out.close();
			}
		}	
	}
	
	private void setContents(){
		setContentType("text/html; charset=utf-8");
	}

}
