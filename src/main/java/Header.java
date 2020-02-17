import javax.json.JsonObject;
import java.io.PrintWriter;
import java.util.Date;

public class Header {
    Header(PrintWriter out, String serverName, String headerName, String content, int fileLength) {
        out.print("HTTP/1.1 "+ headerName + "\r\n");
        out.print("Server: " + serverName + "\r\n");
        out.print("Date: " + new Date() + "\r\n");
        out.print("Content-type: " + content + "\r\n");
        out.print("Content-length: " + fileLength + "\r\n");
        out.print("\r\n");
        out.flush();
    }

    Header(PrintWriter out, String serverName, String headerName, String content, int fileLength, char[] body) {
        out.print("HTTP/1.1 "+ headerName + "\r\n");
        out.print("Server: " + serverName + "\r\n");
        out.print("Date: " + new Date() + "\r\n");
        out.print("Content-type: " + content + "\r\n");
        out.print("Content-length: " + fileLength + "\r\n");
        out.print("\r\n");
        out.print(body);
        out.print("\r");
        out.flush();
    }
    Header(PrintWriter out, String serverName, String headerName, String content, int fileLength, JsonObject body) {
        out.print("HTTP/1.1 "+ headerName + "\r\n");
        out.print("Server: " + serverName + "\r\n");
        out.print("Date: " + new Date() + "\r\n");
        out.print("Content-type: " + content + "\r\n");
        out.print("Content-length: " + fileLength + "\r\n");
        out.print("\r\n");
        out.print(body);
        out.print("\r");
        out.flush();
    }


}
