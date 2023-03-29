package richard.threadpool;

public class RWorker extends Thread{
	public static long currentTimeMillis;
	
	public long timeout;
	public boolean isRunning;
	public Runnable runnable;
	public RWorker() {
		runnable=null;
		isRunning=true;
		timeout=currentTimeMillis+6000;
	}
	
	public boolean add(Runnable _runnable) {
		if(isRunning==false || currentTimeMillis>timeout)
			return false;
		if(runnable==null) {
			runnable=_runnable;
			return true;
		}else
			return false;
	}

	@Override public void run() {
		Runnable task;
		while(isRunning) {
			timeout=currentTimeMillis+6000;//Mỗi process tối đa 6 giây, lâu quá sẽ tạo Thread mới
			task=runnable;
			if(task==null)
				try {Thread.sleep(10);} catch (InterruptedException e) {}
			else {
				runnable=null;
				task.run();
			}
		}
	}
}
