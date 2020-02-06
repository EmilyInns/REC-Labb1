public class Main {
    public static void main(String[] args) {
        System.out.println("Hello REC!");
        DocReader r = new DocReader();
        System.out.println(r.jsonReader().get("WEB_ROOT"));
        System.out.println(r.jsonReader().get("DEFAULT_FILE"));
        System.out.println(r.jsonReader().get("FILE_NOT_FOUND"));
        System.out.println(r.jsonReader().get("METHOD_NOT_SUPPORTED"));
        System.out.println(r.jsonReader().get("PORT"));
    }
}
