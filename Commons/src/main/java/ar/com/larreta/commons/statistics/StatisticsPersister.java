package ar.com.larreta.commons.statistics;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.audit.ExecutionStatistics;
import ar.com.larreta.commons.threads.SaveThread;
import ar.com.larreta.commons.threads.Thread;
import ar.com.larreta.commons.utils.DateUtils;

@Component
public class StatisticsPersister extends Thread {

	private static final Long INTERVAL = DateUtils.ONE_SECOND;
	
	public StatisticsPersister(){
		super(INTERVAL, null);
		getLog().info("Se creo StatisticsPersister (" + this + ")");
	}
	
	private StatisticsPersister(Date executeTime, Long interval, Long executeCount){
		super(executeTime, interval, executeCount);
	}

	@Override
	protected void restart(Long executeCount, Long interval, Date executeTime) {
		new StatisticsPersister(getExecuteTime(), interval, executeCount);
	}
	
	@Override
	protected void execute() {
		synchronized (StatisticsPersister.class) {
			Map<Long, ExecutionStatistics> statistics = StatisticsExecutorManager.getInstance().getStatistics();
			Set<Long> keys = statistics.keySet();
			if (keys!=null){
				Iterator<Long> it = keys.iterator();
				while (it.hasNext()) {
					Long key = (Long) it.next();
					ExecutionStatistics statistic = statistics.get(key);
					if (!statistic.isActive()){
						SaveThread.addEntity(statistic);
						statistics.remove(statistic.getId());
					}
				}
			}
		}
	}
		

		
}
