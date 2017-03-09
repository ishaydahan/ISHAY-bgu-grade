package com.sample;

import java.io.PrintWriter;
import java.net.URL;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

@SuppressWarnings("serial")
public class ServletEx extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		try {
			PrintWriter out = response.getWriter();

			JSONObject jsonObject;
			StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null){
					jb.append(line);
				}
				System.out.println("Request JSON string :"+jb.toString());
		  } catch (Exception e) { /*report an error*/ }

		  try {
		    jsonObject =  new JSONObject(jb.toString());
		  } catch (JSONException e) {
		    // crash and burn
		    throw new IOException("Error parsing JSON request string");
		  }
       //write the response here by getting JSON from jasonBuff.toString()


			String user = jsonObject.getString("user");
			String pass = jsonObject.getString("pass");
			String id = jsonObject.getString("id");

			String p_year = jsonObject.getString("p_year");
			String p_semester = jsonObject.getString("p_semester");
			String out_institution = jsonObject.getString("out_institution");
			String list_department = jsonObject.getString("list_department");
			String list_degree_level = jsonObject.getString("list_degree_level");
			String list_course = jsonObject.getString("list_course");

			File f = new File(p_year +"_"+ p_semester +"_"+ out_institution +"_"+ list_department +"_"+ list_degree_level +"_"+ list_course +".pdf");

			if (f.exists()) {
				String filename = p_year +"_"+ p_semester +"_"+ out_institution +"_"+ list_department +"_"+ list_degree_level +"_"+ list_course +".pdf";
				response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
				FileInputStream fl = new FileInputStream(filename);
				int i;
				while ((i = fl.read()) != -1) {
						out.write(i);
				}
				fl.close();

				out.close();
			}else{
				scrap a = new scrap();
				if (a.data(user, pass, id, p_year, p_semester, out_institution, list_department, list_degree_level, list_course)){
					String filename = p_year +"_"+ p_semester +"_"+ out_institution +"_"+ list_department +"_"+ list_degree_level +"_"+ list_course +".pdf";
					response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
					FileInputStream fl = new FileInputStream(filename);
					int i;
					while ((i = fl.read()) != -1) {
							out.write(i);
					}
					fl.close();

					out.close();
				}else{
					response.setContentType("text");
			    PrintWriter out1 = response.getWriter();
			    out1.print("0");
				}
			}



		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
