import javax.json.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class FRead {
    FRead() {
        jsonReader();
    }

    public Map<String,Object> jsonReader() {
        File file = new File("C:\\Users\\Christoffer Clausen\\IdeaProjects\\WebServicesLabb1\\REC-Labb1\\new 1");
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
            map.put(e.getKey(),(e.getValue()));
        }
        return map;
    }

}

//    static void createAndWrite(File jsonFile) throws IOException {
//        Map< String, Boolean > config = new HashMap< String, Boolean >();//
//        config.put(JsonGenerator.PRETTY_PRINTING, true);//
//        JsonWriterFactory jwf = Json.createWriterFactory(config); //
//        Writer writer = new PrintWriter(jsonFile);
//        JsonWriter jWriter = jwf.createWriter(writer);
//        JsonObject jo = Json.createObjectBuilder()
//                .add("firstName", "Hanky")
//                .add("lastName", "Sandycleavage")
//                .add("age", 65)
//                .add("streetAdress", "Skidrow 88")
//                .add("State", "VGR")
//                .add("postalCode", "66613")
//                .add("phoneNumbers", Json.createArrayBuilder()
//                        .add(Json.createObjectBuilder()
//                                .add("Mobile", "0700123321"))
//                        .add(Json.createObjectBuilder()
//                                .add("Home", "031-90 51 06")))
//                .build();
//        jWriter.writeObject(jo);
//        jWriter.close();
//    }