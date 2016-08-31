package co.com.directv.sdii.job;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.jobs.JobTimeManager;

/**
 * Servlet implementation class ServletAllocatorWM
 */
public class ServletAllocatorWM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAllocatorWM() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	JobTimeManager jobTimeManager = null;
	
	@Override
	public void init()  {
		
		jobTimeManager = new JobTimeManager();
		
		try {
			jobTimeManager.runTimeManager();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {		
	}

}
