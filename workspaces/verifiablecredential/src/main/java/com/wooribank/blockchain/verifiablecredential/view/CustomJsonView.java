package com.wooribank.blockchain.verifiablecredential.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.web.servlet.view.AbstractView;

public class CustomJsonView extends AbstractView {

	public CustomJsonView () {
		setContentType("text/html; charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//request.setCharacterEncoding("UTF-8");
		response.setContentType("text/x-json; charset=UTF-8");
		
		JSONObject jsonObject = (JSONObject) model.get("data");
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.write(jsonObject.toString());
			printWriter.flush();
		} catch (Exception e) {
			
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}
	}
}
