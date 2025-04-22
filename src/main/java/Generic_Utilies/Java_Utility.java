package Generic_Utilies;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Java_Utility {

	
	public int getRandomNumber(){
		
		Random r = new Random();
		int Rno = r.nextInt(5000);
		return Rno;
		
	}
	public String getCurrentDate() {
		
		Date dobj = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String currentdata = sim.format(dobj);
		return currentdata;
	}
	public String getDateAftergivendays(int days) {
		Date dobj = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		sim.format(dobj);
		Calendar cal = sim.getCalendar();
	    cal.add(Calendar.DAY_OF_MONTH, days);
	    String dataaftergivendays = sim.format(cal.getTime());
	    return dataaftergivendays;
	}
}
