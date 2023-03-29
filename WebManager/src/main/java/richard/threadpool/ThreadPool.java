package richard.threadpool;

public class ThreadPool {
	public RWorkerManager workerManager;
	private RScheduleManager scheduleManager;
	private ThreadPool() {
		workerManager=new RWorkerManager(12);
		scheduleManager=new RScheduleManager(256);
	}
	
	private void start() {
		short count=Short.MIN_VALUE;
		long currentTimeMillis;
		while(true) {
			currentTimeMillis=System.currentTimeMillis();
			workerManager.currentTimeMillis=currentTimeMillis;
			scheduleManager.currentTimeMillis=currentTimeMillis;
			
			RWorker.currentTimeMillis=currentTimeMillis;
			
			scheduleManager.process();
			if(count++==Short.MAX_VALUE) {/*10 phút*/
				workerManager.processKeepAlive();
				System.gc();
			}
			try {Thread.sleep(10);} catch (InterruptedException e) {}
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public final void runThreadWaitMili(Runnable runnable,long waitTimemili) {scheduleManager.schedule(runnable, waitTimemili);}
	public void setScheduleBuffer(int _buffer) {
		final RScheduleManager tmp=scheduleManager;
		scheduleManager=new RScheduleManager(_buffer);
		
		workerManager.execute(new Runnable() {@Override public void run() {try {Thread.sleep(1000);} catch (InterruptedException e) {}
			for(short i=0;i<tmp.BUFFER;i++)
				if(tmp.listScheduled[i].timeStart!=0)
					scheduleManager.copyFromOldSchedule(tmp.listScheduled[i]);
		}});
	}
	
	public final void runThread(Runnable runnable) {workerManager.execute(runnable);}
	public void setNumberThread(int _buffer) {
		final RWorkerManager tmp=workerManager;
		workerManager=new RWorkerManager(_buffer);
		new Thread(new Runnable() {public void run() {try {Thread.sleep(10000);} catch (InterruptedException e) {}tmp.release();}}).start();
	}
	
	public void loopThread(Runnable runnable,int secondDelay,int secondSleepLoop) {
		final long timeSleep = secondSleepLoop*1000;
		new Thread() {
			@Override public void run() {
				try {Thread.sleep(secondDelay*1000);} catch (InterruptedException e) {}
				while(true) {
					workerManager.execute(runnable);
					try {Thread.sleep(timeSleep);} catch (InterruptedException e) {}
				}
			}
		}.start();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public final RJsonObject toJson() {
//		RJsonObject result=new RJsonObject();
//		result.put("processing", workerManager.countWorking()+"/"+workerManager.BUFFER);
//		result.put("schedule", scheduleManager.countWorking()+"/"+scheduleManager.BUFFER);
//		return result;
//	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static ThreadPool instance=null;
	public static ThreadPool gI() {
		if(instance==null) {
			instance=new ThreadPool();
			new Thread(new Runnable() {public void run() {instance.start();}}).start();
			
			RScheduleManager.threadPool=instance;
		}
		return instance;
	}
}
