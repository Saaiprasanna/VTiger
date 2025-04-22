package Listeners_Utilies;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class IRetry_Analyzer_Utility implements IRetryAnalyzer{
  
	int count = 0;
	
	int max_retry= 5;

	public boolean retry(ITestResult result) {
		
		if(count < max_retry) {
			count++;
			return true;
		}
		return false;
	}
	
	
	
	

}
