package richard.threadpool;

public class RScheduleManager {
	public static ThreadPool threadPool;
	public long currentTimeMillis;
	public short BUFFER;
	protected RSchedule[] listScheduled;
	private Object synclock;
	public RScheduleManager(int _buffer) {
		synclock=new Object();
		BUFFER=(short) _buffer;
		listScheduled=new RSchedule[BUFFER];
		for(short i=0;i<BUFFER;i++)
			listScheduled[i]=new RSchedule();
	}
	
	private RSchedule tmpAdd;
	public final void schedule(Runnable runnable,long waitTimemili) {
		synchronized (synclock) {
			for(byte count=Byte.MIN_VALUE;count<Byte.MAX_VALUE;count++) {
				for(short i=0;i<BUFFER;i++) {
					tmpAdd=listScheduled[i];
					if(tmpAdd.timeStart==0) {
						tmpAdd.timeStart=currentTimeMillis+waitTimemili;
						tmpAdd.runnable=runnable;
						return;
					}
				}
				try {Thread.sleep(10);} catch (InterruptedException e) {}
			}
		}
	}
	
	
	private RSchedule task;
	public void process() {
		for(short i=0;i<BUFFER;i++) {
			task=listScheduled[i];
			if(task.timeStart!=0 && currentTimeMillis>task.timeStart) {
				threadPool.runThread(task.runnable);
				task.runnable=null;
				task.timeStart=0;
			}
		}
	}
	
	
	protected void copyFromOldSchedule(RSchedule _schedule) {
		synchronized (synclock) {
			for(byte count=Byte.MIN_VALUE;count<Byte.MAX_VALUE;count++) {
				for(short i=0;i<BUFFER;i++) {
					tmpAdd=listScheduled[i];
					if(tmpAdd.timeStart==0) {
						tmpAdd.timeStart=_schedule.timeStart;
						tmpAdd.runnable=_schedule.runnable;
						return;
					}
				}
				try {Thread.sleep(10);} catch (InterruptedException e) {}
			}
		}
	}
	
	public short countWorking() {
		short _count=0;
		for(short i=0;i<BUFFER;i++) 
			if(listScheduled[i].runnable!=null)
				_count++;
		return _count;
	}
}
