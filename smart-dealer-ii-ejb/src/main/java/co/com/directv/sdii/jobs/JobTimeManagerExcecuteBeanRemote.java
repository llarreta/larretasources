package co.com.directv.sdii.jobs;
import co.com.directv.sdii.exceptions.BusinessException;

import commonj.timers.TimerManager;

public interface JobTimeManagerExcecuteBeanRemote {
	
	public void runTimeManager()  throws BusinessException;
	public void runTimeManagerNoCluster(TimerManager timerManager) throws BusinessException;
	
}
