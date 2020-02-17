import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DocWriter {
    public static JsonObject writeJsonFromParam(String param) {

        File jsonFile = new File("file.json");
        String[] strings = new String[param.length()];
        strings = param.split("[&]");

        Map<String, Boolean> config = new HashMap<String, Boolean>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for (String s : strings) {
            String s1 = s.substring(0, s.indexOf("="));
            String s2 = s.substring(s.indexOf("=") + 1);
            builder.add(s1, s2);
        }
        JsonObject jo = builder.build();
        try {
            FileWriter fileWriter = new FileWriter("web/file.json");
            fileWriter.write(jo.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jo;
    }
}