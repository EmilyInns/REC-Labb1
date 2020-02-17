import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DocReader {

    public Map<String, Object> jsonReader() {
        File file = new File(".\\config.json");
        JsonReader reader = null;
        try {
            reader = Json.createReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonStructure jsonStruct = reader.read();
        reader.close();
        JsonObject jso = (JsonObject) jsonStruct;
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, JsonValue> e : jso.entrySet()) {
            map.put(e.getKey(), (e.getValue().toString().replace("\"", "")));
        }
        return map;
    }
}