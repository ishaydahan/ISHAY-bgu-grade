package com.sample;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindowEvent;
import com.gargoylesoftware.htmlunit.WebWindowListener;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class scrap {

	public boolean data(
			String user,
			String pass,
			String id,
			String p_year,
			String p_semester,
			String out_institution,
			String list_department,
			String list_degree_level,
			String list_course
			) {

	    // turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);


	    WebClient webClient = new WebClient(BrowserVersion.CHROME);
	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setRedirectEnabled(true);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    webClient.getCookieManager().setCookiesEnabled(true);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);

        webClient.addWebWindowListener(new WebWindowListener() {

            public void webWindowOpened(WebWindowEvent event) {
            	event.getNewPage().getWebResponse();
            }

            public void webWindowContentChanged(WebWindowEvent event) {}

            public void webWindowClosed(WebWindowEvent event) {}
        });


	    try {
	        HtmlPage page = (HtmlPage) webClient
	                .getPage("https://bgu4u.bgu.ac.il/pls/apex/f?p=104:LOGIN_DESKTOP:8277622888376");

	        HtmlForm form = page.getFormByName("wwv_flow");

	        form.getInputByName("p_t01").setValueAttribute(user);
	        HtmlInput passWordInput = form.getInputByName("p_t02");
	        passWordInput.removeAttribute("disabled");
	        passWordInput.setValueAttribute(pass);
	        form.getInputByName("p_t03").setValueAttribute(id);

	        page = form.getElementsByTagName("button").get(0).click(); // works fine

	        page = page.getElementById("P1_KIOSK").click();

	        page = page.getAnchorByHref("javascript:apex.submit('T_מידעעלקורסים');").click();

	        HtmlElement a = page.getElementByName("p_instance");
	        int start = a.toString().indexOf("value") + 7;
	        int end = a.toString().indexOf("pInstance") - 6;
	        String ans = a.toString().substring(start, end);

	        page = page.getAnchorByHref("f?p=109:3:" + ans + "::NO:::").click();

	        page = page.getElementById("P3_URL").click();


	        String html = page.asXml();
	        start = html.indexOf("p_key")+6;
	        end = html.indexOf("p_year")-1;
	        ans = html.toString().substring(start, end);

	        WebResponse response = webClient.getPage("https://reports4u.bgu.ac.il/GeneratePDF.php?server=rep_aristo4stu4_FRHome1/report=SCRR016w/p_key="+ans+"/p_year="+p_year+"/p_semester="+p_semester+"/out_institution="+out_institution+"/grade=5/list_department=*"+list_department+"@/list_degree_level=*"+list_degree_level+"@/list_course=*"+list_course+"@/LIST_GROUP=*@/P_FOR_STUDENT=1").getWebResponse();            // Change or add conditions for content-types that you would
            if (response.getContentType().equals("application/pdf")) {
                try {
                    IOUtils.copy(response.getContentAsStream(), new FileOutputStream(p_year +"_"+ p_semester +"_"+ out_institution +"_"+ list_department +"_"+ list_degree_level +"_"+ list_course +".pdf"));
                	return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
							return false;
            }



	    } catch (Exception e) {
	        return false;
	    } finally {
	        webClient.close();
	    }
			return true;
	}

}
