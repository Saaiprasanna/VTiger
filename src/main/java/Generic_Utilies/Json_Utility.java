package Generic_Utilies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Json_Utility {

	public String FetchDataFromJsonFile(String key) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
		JSONParser parser=new JSONParser();
		Object obj=parser.parse(new FileReader("./src/test/resources/Vtiger.json"));
		JSONObject js=(JSONObject)obj;
		String data=js.get(key).toString();
		return data;
		
		
	}
}
