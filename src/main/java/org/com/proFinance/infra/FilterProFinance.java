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

	UserAgentInfo agent;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try{
	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;
	        HttpSession ses = req.getSession(false);
	        String reqURI = req.getRequestURI();
	        boolean isMobile = isMobile(req);
	        
	        if (isDoFilter(reqURI, ses, req, isMobile)) {
	            filterChain.doFilter(request, response);
	        }else if(isMobile){
	        	res.sendRedirect(req.getContextPath() + "/loginMobile.jsf"); 
	        }else{
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
	
	public boolean isDoFilter(String reqURI, HttpSession ses, HttpServletRequest req, boolean isMobile){
		if((reqURI.startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER) || reqURI.startsWith(req.getContextPath() + "/img")
	        		|| (reqURI.startsWith(req.getContextPath() + "/login.jsf") && isMobile == false) || 
	        			(reqURI.startsWith(req.getContextPath() + "/loginMobile.jsf") && isMobile)) ||
	        			(ses != null && ses.getAttribute("usuarioLogado") != null)){
			return true;
		}
		return false;
	}
	
	public boolean isMobile(HttpServletRequest req){
		String userAgent = req.getHeader("user-agent");
		String accept = req.getHeader("Accept");
		
		if (userAgent != null && accept != null) {
		    agent = new UserAgentInfo(userAgent, accept);
		    if (agent.isMobileDevice()) {
		        return true;
		    }
		}
		return false;
	}

}
