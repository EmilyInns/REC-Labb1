import java.io.PrintWriter;
import java.util.Date;

public class Header {
    Header(PrintWriter out, String headerName, String content, int fileLength) {
        out.print("HTTP/1.1 "+ headerName + "\r\n");
        out.print("Server: Java HTTP Server from REC : 1.0" + "\r\n");
        out.print("Date: " + new Date() + "\r\n");
        out.print("Content-type: " + content + "\r\n");
        out.print("Content-length: " + fileLength + "\r\n");
        out.print("\r\n"); // blank line between headers and content, very important !
        out.flush(); // flush character output stream buffer
    }


}
