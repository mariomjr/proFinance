package org.com.proFinance.infra;

import javax.ejb.Startup;
import javax.faces.bean.ApplicationScoped;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Startup
@ApplicationScoped
public class ListenerProFinance  implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Listener Pro Finance");
		inicializarCertificadoAutentificacao();
	}
	
	
	private void inicializarCertificadoAutentificacao() {
		System.setProperty("javax.net.ssl.trustStoreType","JKS");
		System.setProperty("javax.net.ssl.trustStore", "C:/Program Files/Java/jre7/lib/security/cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword","changeit");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

}
