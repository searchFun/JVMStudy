package concurrent.homework.log;
/**
 * 日志类型枚举
 * @author wanglw2
 *
 */
public enum LogEnum {
	//debug
	DEBUG(0,"[DEBUG]"),
	//info
	INFO(1,"[INFO]"),
	//warn
	WARN(2,"[WARN]"),
	//error
	ERROR(3,"[ERROR]");
	
	private int logLevel;
	private String preLogText;
	
	public static int getLevelByName(String name){
		LogEnum[] logEnums = values();
		for (LogEnum logEnum : logEnums) {
			if (logEnum.getPreLogText().equalsIgnoreCase("["+name+"]")) {
				return logEnum.getLogLevel();
			}
		}
		return 0;
	}


	LogEnum(int level,String text){
		logLevel = level;
		preLogText = text;
	}

	public int getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public String getPreLogText() {
		return preLogText;
	}

	public void setPreLogText(String preLogText) {
		this.preLogText = preLogText;
	}
}
