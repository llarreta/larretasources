package ar.com.larreta.commons.statistics;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ar.com.larreta.commons.domain.audit.ExecutionStatistics;
import ar.com.larreta.commons.utils.SessionUtils;

public class StatisticsExecutorManager implements Serializable {

	private Map<Long, ExecutionStatistics> statistics = new ConcurrentHashMap<Long, ExecutionStatistics>();
	
	private static StatisticsExecutorManager INSTANCE;
	
	private StatisticsExecutorManager(){
		
	}
	
	public static StatisticsExecutorManager getInstance(){
		if (INSTANCE==null){
			INSTANCE = new StatisticsExecutorManager();
		}
		return INSTANCE;
	}
	
	public Map<Long, ExecutionStatistics> getStatistics() {
		return statistics;
	}

	public Long start(String mark){
		ExecutionStatistics statistic = new ExecutionStatistics();
		statistic.start();
		statistic.setMark(mark);
		statistics.put(statistic.getId(), statistic);
		return statistic.getId();
	}
	
	public void stop(Long id){
		ExecutionStatistics statistic = statistics.get(id);
		if (statistic!=null){
			statistic.setUser(SessionUtils.getActualUser());
			statistic.stop();
		}
	}
}
