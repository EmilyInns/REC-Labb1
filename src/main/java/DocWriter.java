import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static javax.json.JsonValue.ValueType.OBJECT;

public class DocWriter {
    public static void writeJsonFromURL(String url) {

        File jsonFile = new File("file.json");
//        parseAndPrint(jsonFile);

        System.out.println("In DocWriter");
        System.out.println("innan substring" + url);
//        if (url.contains("?")) {
        url = url.substring(url.indexOf("?"));
        System.out.println("före split" + url);
        String[] strings = url.split("[&]");
        System.out.println("efter split" + url);
        for (String s : strings) {
            System.out.println("21" + s);
        }

//        File file = new File("file.json");
        Map<String, Boolean> config = new HashMap<String, Boolean>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory jwf = Json.createWriterFactory(config);
        Writer writer = null;
//        try {
//            writer = new PrintWriter(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        JsonWriter jWriter = jwf.createWriter(writer);
        JsonObjectBuilder job = Json.createObjectBuilder();
        System.out.println("Adding to json object in docWriter");
        JsonObject jo = job.build();
        for (String s : strings) {
            System.out.println(s.substring(0, s.indexOf("=")));
            System.out.println(s.substring(s.indexOf("=") + 1));
            job.add(s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=") + 1));
            System.out.println(job.toString() + "         13413");
        }
        jWriter.writeObject(job.build());
//        jo = job.build();
        jWriter.close();
        System.out.println("done building jsonFile");
        System.out.println("json:" + jo.toString());

    }

    static void parseAndPrint(String jsonString) throws FileNotFoundException {
        try {
            JsonReader reader = Json.createReader(new FileReader(jsonString));
            JsonStructure jsonStruct = reader.read();
            reader.close();
            System.out.println(jsonString);

            if (jsonStruct.getValueType().equals(OBJECT)) {
                JsonObject jo = (JsonObject) jsonStruct;
                System.out.println(jo.getString("firstName"));
                System.out.println(jo.getString("lastName"));
                System.out.println(jo.getInt("age"));
                System.out.println(jo.getString("streetAdress", "Adress"));
                System.out.println(jo.getString("city", "City"));
                System.out.println(jo.getString("state", "State"));
                System.out.println(jo.getString("postalCode", "Postal code"));
                JsonArray arr = jo.getJsonArray("phoneNumbers");
                for (JsonValue jv : arr) {
                    if (((JsonObject) jv).keySet().contains("Mobile")) {
                        String mobile = ((JsonObject) jv).getString("Mobile");
                        if (mobile != null) {
                            System.out.println(mobile);
                        }
                    }
                    if (((JsonObject) jv).keySet().contains("Home")) {
                        String home = ((JsonObject) jv).getString("Home");
                        if (home != null) {
                            System.out.println(home);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw e;
        }
    }
    //   System.out.println(jo.toString() + "                 2");
//        }

//            Json jsonFile = ;
//            Map<String, Boolean> config = new HashMap<String, Boolean>()
//            config.put(JsonGenerator.PRETTY_PRINTING, true);//
//            JsonWriterFactory jwf = Json.createWriterFactory(config); //
//            Writer writer = new PrintWriter(jsonFile);
//            JsonWriter jWriter = jwf.createWriter(writer);
//            JsonObject jo = Json.createObjectBuilder()


//    public void createAndWrite(URL url) throws IOException {
//        Map<String, Boolean> config = new HashMap<String, Boolean>();//
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
}