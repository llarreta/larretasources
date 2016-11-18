package ar.com.larreta.commons.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.audit.VisitorStatistics;
import ar.com.larreta.commons.logger.AppLogger;
import ar.com.larreta.commons.threads.SaveThread;
import ar.com.larreta.commons.utils.SessionUtils;
import eu.bitwalker.useragentutils.UserAgent;

public class URLVisitorFilter extends AppObjectImpl implements Filter {

	private static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";
	public static final String BACK = "back";
	public static final String EQUAL = "=";
	public static final String PREFIX = "?";
	
	private static final AppLogger LOGGER = new AppLogger(URLVisitorFilter.class);
	
	
	public void init(FilterConfig filterConfig) throws ServletException {
		URLsManager.getInstance();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			Boolean active = new Boolean(AppManager.getInstance().getAppConfigData().getProperty("visitor.statistics.avaiable"));
			if (SessionUtils.getActualUser()!=null && active){
				HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				
				String indexParam = httpServletRequest.getParameter(URLsManager.ACTUAL_INDEX);
				
				if (indexParam!=null){
					Integer indexValue = new Integer(indexParam);
					URLsManager.getInstance().setActualIndex(indexValue);
				} else {
					URLsManager.getInstance().putActualURL(httpServletRequest.getRequestURL().toString());
				}
				
				saveStatistics(httpServletRequest);
			}
			chain.doFilter(request, response);
		} catch (IOException e){
			LOGGER.error("Ocurrio un error inesperado", e);
			throw e;
		} catch (ServletException e){
			LOGGER.error("Ocurrio un error inesperado", e);
			throw e;
		}
	}

	/**
	 * Almacena las estadisticas de visita 
	 * @param httpServletRequest
	 */
	public void saveStatistics(HttpServletRequest httpServletRequest) {
		UserAgent agent = UserAgent.parseUserAgentString(httpServletRequest.getHeader("User-Agent"));
		
		
		VisitorStatistics statistics = new VisitorStatistics();
		statistics.setIp(getIp(httpServletRequest));
		statistics.setUrl(httpServletRequest.getRequestURL().toString());
		statistics.setDate(new Date());
		statistics.setUser(SessionUtils.getActualUser());
		statistics.setBrowser(agent.getBrowser().getName());
		statistics.setDevice(agent.getOperatingSystem().getDeviceType().getName());
		statistics.setOperatingSystem(agent.getOperatingSystem().getName());
		
		SaveThread.addEntity(statistics);
	}


	public void destroy() {
		getLog().debug("destroy:doNothing");
	}

	/**
	 * Obtiene la ip del que invoca
	 * @param request
	 * @return
	 */
	public String getIp(HttpServletRequest request){
		String ip = request.getHeader(X_FORWARDED_FOR);
		if (ip==null){
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	
}
