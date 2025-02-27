package RvNLearn.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public DataReader() {
		
		
	}
	
	public List getJsonToMap() throws IOException, JsonProcessingException {
		
		String jsonFileContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")
				+ "\\src\\test\\java\\RvNLearn\\data\\purchaseOrder.json"),
				StandardCharsets.UTF_8);
		
		// String to HashMap util Jackson DataBind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonFileContent,
				new TypeReference<List<HashMap<String, String>>>(){
			
		});
		return data;
	}

}
