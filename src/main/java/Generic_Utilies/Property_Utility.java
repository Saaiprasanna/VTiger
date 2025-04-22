package Generic_Utilies;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Property_Utility {
	public String FetchdatafromPropfile(String key) throws IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/Commondata.properties");
		Properties p = new Properties();
		p.load(fis);
		String data = p.getProperty(key);
		return data;
	}

	public void WriteDataBacktoProperty(String key, String value) throws IOException {

		FileInputStream fis = new FileInputStream("./src/test/resources/Commondata.properties");
        Properties p = new Properties();
        p.load(fis);
        p.put(key, value);
        FileOutputStream fos = new FileOutputStream("./src/test/resources/Commondata.properties");
        p.store(fos, "Updated Successfully");
		System.out.println("Data inserted into propertyfile");
	}
}
