import javax.json.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class JavaHTTPServer implements Runnable {

    static DocReader config = new DocReader();
    static final File WEB_ROOT = new File(config.jsonReader().get("WEB_ROOT").toString());
    static final String DEFAULT_FILE = (String) config.jsonReader().get("DEFAULT_FILE").toString();
    static final String FILE_NOT_FOUND = (String) config.jsonReader().get("FILE_NOT_FOUND").toString();
    static final String METHOD_NOT_SUPPORTED = (String) config.jsonReader().get("METHOD_NOT_SUPPORTED").toString();
    static final int PORT = Integer.parseInt(config.jsonReader().get("PORT").toString());

    static final String serverName = "ServerName";

    static final boolean verbose = true;

    private Socket connect;

    public JavaHTTPServer(Socket c) {
        connect = c;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");

            while (true) {
                JavaHTTPServer myServer = new JavaHTTPServer(serverConnect.accept());

                if (verbose) {
                    System.out.println("Connection opened. (" + new Date() + ")");
                }

                Thread thread = new Thread(myServer);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;
        String method = "";

        try {
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new PrintWriter(connect.getOutputStream());
            dataOut = new BufferedOutputStream(connect.getOutputStream());

            connect.setSoTimeout(10000); //

            String input = in.readLine();
            StringTokenizer parse = new StringTokenizer(input);
            method = parse.nextToken().toUpperCase();
            fileRequested = parse.nextToken().toLowerCase();

            if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
                if (verbose) {
                    System.out.println("501 Not Implemented : " + method + " method.");
                }

                File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
                int fileLength = (int) file.length();
                String contentMimeType = "text/html";
                byte[] fileData = readFileData(file, fileLength);

                Header page501 = new Header(out, serverName + "501", "501 Not implemented", contentMimeType, fileLength);
                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();
            } else {
                String param = null;
                if (fileRequested.endsWith("/")) {
                    fileRequested += DEFAULT_FILE.replace("\"", "");
                } else if (fileRequested.contains("?")) {
                    param = fileRequested.substring(fileRequested.indexOf("?") + 1);
                    fileRequested = fileRequested.substring(0, fileRequested.indexOf(param) - 1);
                }

                File file = new File(WEB_ROOT, fileRequested);
                int fileLength = (int) file.length();
                String content = getContentType(fileRequested);

                if (method.equals("GET") || method.equals("HEAD") || method.equals("POST")) { // GET method so we return content

                    if (method.equals("HEAD")) {
                        Header twoOhOhHead = new Header(out, serverName + "200", "200 OK", content, fileLength);
                    }

                    if (method.equals("GET") || method.equals("POST")) {
                        byte[] fileData = readFileData(file, fileLength);
                        if (method.equals("GET") && param != null) {

                            JsonObject jo = DocWriter.writeJsonFromParam(param);
                            if (fileRequested.endsWith("jsonpage.html")) {
                                content = getContentType(".json");
                            } else {
                                content = getContentType(fileRequested);
                            }
                            Header getWithParams = new Header(out, serverName + "200", "200 OK", content, fileLength, jo);
                            dataOut.write(fileData, 0, fileLength);
                            dataOut.flush();
                        } else if (method.equals("GET")) {
                            Header page200 = new Header(out, serverName + "200", "200 OK", content, fileLength);
                            dataOut.write(fileData, 0, fileLength);
                            dataOut.flush();
                        } else if (method.equals("POST")) {
                            StringBuilder sb = new StringBuilder();
                            int charNum = in.read();
                            char theChar = '.';

                            while (theChar != '}') {
                                theChar = (char) charNum;
                                sb.append(theChar);
                                if (theChar != '}') {
                                    charNum = in.read();
                                }
                            }
                            String s = sb.toString();
                            s = s.substring(s.indexOf("{"));
                            char fileBody[] = new char[s.length()];
                            for (int i = 0; i < s.length(); i++) {
                                fileBody[i] = s.charAt(i);
                            }
                            content = getContentType(fileRequested);
                            Header twoOhOh = new Header(out, serverName + "200", "200 OK", content, fileLength + fileBody.length, fileBody);
                            dataOut.write(fileData, 0, fileLength);
                            dataOut.flush();
                        }
                    } else if (method.equals("GET")) {
                        Header page200 = new Header(out, serverName + "200", "200 OK", content, fileLength);
                    }
                }

                if (verbose) {
                    System.out.println("File " + fileRequested + " of type " + content + " returned");
                }

            }

        } catch (FileNotFoundException fnfe) {
            try {
                fileNotFound(out, dataOut, fileRequested, method);
            } catch (IOException ioe) {
                System.err.println("Error with file not found exception : " + ioe.getMessage());
            }

        } catch (IOException ioe) {
            System.err.println("Server error : " + ioe);
        } finally {
            try {
                in.close();
                out.close();
                dataOut.close();
                connect.close();
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }

            if (verbose) {
                System.out.println("Connection closed.\n");
            }
        }
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".htm") || fileRequested.endsWith(".html"))
            return "text/html";
        else if (fileRequested.endsWith(".js")) {
            return "text/javascript";
        } else if (fileRequested.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileRequested.endsWith(".css")) {
            return "text/css";
        } else if (fileRequested.endsWith(".json")) {
            return "application/json";
        } else if (fileRequested.endsWith(".png")) {
            return "image/png";
        } else if (fileRequested.endsWith(".jpg")) {
            return "image/jpeg";
        } else
            return "text/plain";
    }

    private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested, String method) throws
            IOException {
        File file = new File(WEB_ROOT.toString(), FILE_NOT_FOUND.replace("\"", ""));

        int fileLength = (int) file.length();
        String content = "text/html";
        byte[] fileData = readFileData(file, fileLength);

        Header page404 = new Header(out, serverName + "404", "404 File Not Found", content, fileLength);

        if (method.equals("GET")) {
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();
        }

        if (verbose) {
            System.out.println("File " + fileRequested + " not found");
        }
    }
}