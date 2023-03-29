package richard.threadpool;

public class RWorkerManager {
	public long currentTimeMillis;
	public short BUFFER;
	private RWorker[] listWorker;
	private Object syncLock;
	public RWorkerManager(int _buffer) {
		syncLock=new Object();
		BUFFER=(short) _buffer;
		listWorker=new RWorker[_buffer];
		for(short i=0;i<BUFFER;i++) {
			listWorker[i]=new RWorker();
			listWorker[i].start();
		}
	}
	
	private RWorker worker;
	public void processKeepAlive() {
		for(short i=0;i<BUFFER;i++) {
			worker = listWorker[i];
			if(currentTimeMillis>worker.timeout) {
				worker.isRunning=false;
				worker=new RWorker();
				worker.start();
				listWorker[i]=worker;
				countErrorAndDownload++;
			}
		}
	}
	
	public final void execute(Runnable runnable) {
		for(byte count=Byte.MIN_VALUE;count<100;count++)/**/
			synchronized (syncLock) {
				for(short i=0;i<BUFFER;i++)
					if(listWorker[i].add(runnable))
						return;
				try {Thread.sleep(50);} catch (InterruptedException e) {}
			}
	}
	
	public final void release() {
		for(short i=0;i<BUFFER;i++)
			listWorker[i].isRunning=false;
	}
	public static long countErrorAndDownload;
	public short countWorking() {
		short _count=0;
		for(short i=0;i<BUFFER;i++) 
			if(listWorker[i].runnable!=null)
				_count++;
		return _count;
	}
}
