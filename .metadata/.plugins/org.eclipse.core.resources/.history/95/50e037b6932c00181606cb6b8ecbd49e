package typeCounter;
import java.util.ArrayList;

/**
 * Logger used for debugging purposes
 * @author Elvin
 *
 */
public class Logger {
	private boolean enabled;
	private ArrayList<String> logBook = new ArrayList<String>();
	
	/**
	 * Logger constructor defaulted to disabled
	 */
	public Logger(){
		this.enabled = false;
		toLogBook("Logger: initialized");
	}
	
	/**
	 * Logger constructor with option
	 * to enable logger
	 * @param caller -- function which initiated the logger
	 * @param initiator -- function which initiated the logger
	 * @param willBeEnabled
	 */
	public Logger(boolean willBeEnabled){
		this.enabled = willBeEnabled;
		toLogBook("Logger: initialized from ");
	}
	
	/**
	 * Logger constructor with option
	 * to alert that logger is enabled
	 * @param willEnable
	 * @param alert
	 */
	public Logger(boolean willEnable, boolean alert){
		this.enabled = willEnable;
		if(alert) this.log("Logger enabled");
		toLogBook("Logger: initialized");
	}

	/**
	 * Logs string if logger is enabled using
	 * System.out.println
	 * @param str
	 * @return this for chaining functions
	 */
	public Logger log(String str){
		if(enabled) System.out.println(str);
		toLogBook(str);
		return this;
	}
	
	/**
	 * Logs string if logger is enabled
	 * using System.out.println
	 * 
	 * If flag is true, logs string regardless
	 * of whether the logger is enabled
	 * If flag is false, an alternative
	 * print method is used: System.out.print
	 * 
	 * @param str
	 * @param flag
	 * @return this for chaining methods
	 */
	public Logger log(String str, boolean flag){
		if(flag) System.out.println(str);
		else if(enabled) System.out.print(str);
		toLogBook(str);
		return this;
	}	
	
	/**
	 * Enables logger
	 * @return this for chaining methods
	 */
	public Logger enable(){
		enabled = true;
		toLogBook("Logger: enable");
		return this;
	}
	
	/**
	 * Enables logger with option to alert
	 * @param alert
	 * @return this for chaining methods
	 */
	public Logger enable(boolean alert){
		enabled = true;
		if(alert) System.out.println("Logger enabled");
		toLogBook("Logger: enable");
		return this;
	}

	/**
	 * Disables logger
	 * @return this for chaining methods
	 */
	public Logger disable(){
		enabled = false;
		toLogBook("Logger: disable");
		return this;
	}
	
	/**
	 * Disables logger with option to alert
	 * @param alert
	 * @return this for chaining methods
	 */
	public Logger disable(boolean alert){
		enabled = false;
		if(alert) System.out.println("Logger disabled");
		toLogBook("Logger: disable");
		return this;
	}
	
	/**
	 * Toggles from disable to enable or vice versa
	 * @param alert
	 * @return this for chaining methods
	 */
	public Logger toggleEnable(){
		enabled = !enabled;
		toLogBook("Logger: toggled to "+ this.enabled);
		return this;
	}
	
	/**
	 * Toggles from disable to enable or vice versa
	 * with option to alert
	 * @param alert
	 * @return this for chaining methods
	 */
	public Logger toggleEnable(boolean alert){
		enabled = !enabled;
		if(alert){
			System.out.print("Logger toggled to ");
			if(enabled) System.out.println("enabled");
			else System.out.println("disabled");
		}
		toLogBook("Logger: toggled to "+ this.enabled);
		return this;
	}
	
	public Logger printAllLogs(){
		for(String log: logBook){
			System.out.println(log);
		}
		return this;
	}
	
	/**
	 * Checks and returns if logger is enabled
	 * @return whether function is enabled
	 */
	public boolean isEnabled(){
		return this.enabled;
	}
	
	/**
	 * Returns new instance of the log book
	 * @return
	 */
	public ArrayList<String> getLogBook(){
		ArrayList<String> result = new ArrayList<String>();
		for(String log: logBook){
			result.add(log);
		}
		return result;
	}
	
	/**
	 * Adds string to log book
	 * @param str
	 */
	private void toLogBook(String str){
		logBook.add(str);
	}
}
