import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class DocWriter {
    public static void writeJsonFromURL(String url) {

        url = url.substring(2, url.length());
        String[] strings = url.split("[&]");
        for (String s : strings) {
            System.out.println(s);
        }
        
        File file = new File("file.json");
//        file.getParentFile().mkdirs();
        
        Map< String, Boolean > config = new HashMap< String, Boolean >();
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory jwf = Json.createWriterFactory(config);

        Writer writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonWriter jWriter = jwf.createWriter(writer);
        JsonObjectBuilder job = Json.createObjectBuilder();
        for (String s : strings) {
            job.add( s.substring(0,s.indexOf("=")), s.substring(s.indexOf("=")+1) );
            System.out.println(job.toString());
        }
        jWriter.writeObject(job.build());
        jWriter.close();

//            Json jsonFile = ;
//            Map<String, Boolean> config = new HashMap<String, Boolean>()
//            config.put(JsonGenerator.PRETTY_PRINTING, true);//
//            JsonWriterFactory jwf = Json.createWriterFactory(config); //
//            Writer writer = new PrintWriter(jsonFile);
//            JsonWriter jWriter = jwf.createWriter(writer);
//            JsonObject jo = Json.createObjectBuilder()
    }

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