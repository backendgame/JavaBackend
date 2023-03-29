package richard;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeManager {
	public static final long A_WEEK_MILLISECOND=604800000;
	public static final long A_DAY_MILLISECOND=86400000;
	public static final long A_MONTH_MILLISECOND=2592000000l;
	public static final long A_HOUR_MILLISECOND=3600000;
	public static final long A_MINUTES_MILLISECOND=60000;
	private static TimeManager instance = null;
	public static final TimeManager gI() {
		if (instance == null)
			instance = new TimeManager();
		return instance;
	}

	public Calendar calendar;
	public Object syncLock;

	private TimeManager() {
		syncLock = new Object();
		calendar = Calendar.getInstance();
	}

	public final TimeModel getTimeModel(long time) {
		TimeModel timeModel = new TimeModel();
		synchronized (syncLock) {
			calendar.setTimeInMillis(time);
			timeModel.date = (short) calendar.get(Calendar.DATE);
			timeModel.month = (short) (calendar.get(Calendar.MONTH) + 1);
			timeModel.year = (short) calendar.get(Calendar.YEAR);
			timeModel.hour = (short) calendar.get(Calendar.HOUR_OF_DAY);
			timeModel.minute = (short) calendar.get(Calendar.MINUTE);
			timeModel.second = (short) calendar.get(Calendar.SECOND);
		}
		return timeModel;
	}

	public final String getTimeBreakLine(long time) {
		int dd, mm, yyyy, hour, minus, second;
		synchronized (syncLock) {
			calendar.setTimeInMillis(time);
			dd = calendar.get(Calendar.DATE);
			mm = calendar.get(Calendar.MONTH) + 1;
			yyyy = calendar.get(Calendar.YEAR);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minus = calendar.get(Calendar.MINUTE);
			second = calendar.get(Calendar.SECOND);
		}
		return yyyy + "/" + mm + "/" + dd + "<br>" + hour + ":" + minus + ":" + second;
	}
	public final String getStringTime(long time) {
		int dd, mm, yyyy, hour, minus, second;
		synchronized (syncLock) {
			calendar.setTimeInMillis(time);
			dd = calendar.get(Calendar.DATE);
			mm = calendar.get(Calendar.MONTH) + 1;
			yyyy = calendar.get(Calendar.YEAR);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minus = calendar.get(Calendar.MINUTE);
			second = calendar.get(Calendar.SECOND);
		}
		return yyyy + "/" + mm + "/" + dd + " (" + hour + ":" + minus + ":" + second + ")";
	}
	public final String getStringTime() {
		int dd, mm, yyyy, hour, minus, second;
		synchronized (syncLock) {
			calendar.setTimeInMillis(System.currentTimeMillis());
			dd = calendar.get(Calendar.DATE);
			mm = calendar.get(Calendar.MONTH) + 1;
			yyyy = calendar.get(Calendar.YEAR);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minus = calendar.get(Calendar.MINUTE);
			second = calendar.get(Calendar.SECOND);
		}
		return yyyy + "/" + mm + "/" + dd + " (" + hour + ":" + minus + ":" + second + ")";
	}
	
	public final String getTimeNameFile(long time) {
		int dd, mm, yyyy;//, hour, minus;
		synchronized (syncLock) {
			calendar.setTimeInMillis(time);
			yyyy = calendar.get(Calendar.YEAR);
			mm = calendar.get(Calendar.MONTH) + 1;
			dd = calendar.get(Calendar.DATE);
//			hour = calendar.get(Calendar.HOUR_OF_DAY);
//			minus = calendar.get(Calendar.MINUTE);
		}
		String result=""+yyyy; 
		if(mm<10)
			result=result+"0"+mm;
		else
			result=result+mm;
		
		if(dd<10)
			result=result+"0"+dd;
		else
			result=result+dd;
//		result=result+"__";
//		if(hour<10)
//			result=result+"0"+hour;
//		else
//			result=result+hour;
//		result=result+"_";
//		if(minus<10)
//			result=result+"0"+minus;
//		else
//			result=result+minus;
		return result;
	}
	
	public final String getNameMinus(long time) {
		int dd, mm, yyyy,hour, minus;
		synchronized (syncLock) {
			calendar.setTimeInMillis(time);
			yyyy = calendar.get(Calendar.YEAR);
			mm = calendar.get(Calendar.MONTH) + 1;
			dd = calendar.get(Calendar.DATE);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minus = calendar.get(Calendar.MINUTE);
		}
		String result=""+yyyy; 
		if(mm<10)
			result=result+"0"+mm;
		else
			result=result+mm;
		
		if(dd<10)
			result=result+"0"+dd;
		else
			result=result+dd;
		result=result+"_";
		if(hour<10)
			result=result+"0"+hour;
		else
			result=result+hour;
		result=result+"h";
		if(minus<10)
			result=result+"0"+minus;
		else
			result=result+minus;
		return result;
	}
	
	public final String getDistanceTime(long time) {
		time = time/1000;
		
		String s = (time % 60)+"s";
		time=time/60;
		if(time==0)
			return s;
		
		s=(time % 60)+"m "+s;
		time=time/60;
		if(time==0)
			return s;
		s=(time % 24)+"h "+s;
		time=time/24;
		if(time==0)
			return s;
		s=time+"day "+s;
		return s;
	}
	
	public String getTimeZone() {
		 TimeZone timeZone = calendar.getTimeZone();
		 if(timeZone.getRawOffset()<0)
			 return timeZone.getID()+" GMT(-"+(timeZone.getRawOffset()/3600)+") "+timeZone.getDisplayName();
		 else
			 return timeZone.getID()+" GMT(+"+(timeZone.getRawOffset()/3600)+") "+timeZone.getDisplayName();
	}
	
	public final String getHourMinus() {synchronized (syncLock) {calendar.setTimeInMillis(System.currentTimeMillis());return "["+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+"]-->"+calendar.get(Calendar.SECOND)+" s";}}
	public final long getTimeMiliEndDay() {
//		return TimeManager.A_DAY_MILLISECOND-(System.currentTimeMillis()%TimeManager.A_DAY_MILLISECOND)-7*TimeManager.A_HOUR_MILLISECOND;
		return 86400000-(System.currentTimeMillis()%86400000)-7*3600000;
	}
	
	public final String getSizeBytes(long value) {
		String s="";
		if(value>1073741824) {
			s=s+(value/1073741824)+" G__";
			value=value%1073741824;
		}
		if(value>1048576) {
			s=s+(value/1048576)+" M__";
			value=value%1048576;
		}
		if(value>1024) {
			s=s+(value/1024)+" K";
			value=value%1024;
		}
		return s;
	}
}
