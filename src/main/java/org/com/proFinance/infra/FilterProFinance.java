package org.com.proFinance.infra;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class FilterProFinance implements Filter, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4866683945454659329L;

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try{
	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;
	        HttpSession ses = req.getSession(false);
	        String reqURI = req.getRequestURI();
	        if (reqURI.startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)
	        		|| reqURI.indexOf("/login.jsf") >= 0 ||(ses != null && ses.getAttribute("usuarioLogado") != null)) {
	            filterChain.doFilter(request, response);
//	            return;
	        }
//	        if ( reqURI.indexOf("/login.jsf") >= 0 ||reqURI.contains("/img/") ||(ses != null && ses.getAttribute("usuarioLogado") != null)
//	                                   || reqURI.indexOf("/public/") >= 0 || reqURI.contains("javax.faces.resource") ){
//	        	filterChain.doFilter(request, response);
//	        }
	        else{
	        	res.sendRedirect(req.getContextPath() + "/login.jsf"); 
	        }
		}catch(Throwable t) {
	     System.out.println( t.getMessage());
		}
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
