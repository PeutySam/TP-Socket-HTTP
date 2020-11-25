///A Simple Web Server (WebServer.java)

package http.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Example program from Chapter 1 Programming Spiders, Bots and Aggregators in
 * Java Copyright 2001 by Jeff Heaton
 * <p>
 * WebServer is a very simple web-server. Any request is responded with a very
 * simple web-page.
 *
 * @author Jeff Heaton
 * @version 1.0
 */
public class WebServer{

    /**
     * WebServer constructor.
     */
    protected void start(int port) {
        ServerSocket s;

        System.out.println("Webserver starting up on port " + port);
        System.out.println("(press ctrl-c to exit)");
        try {
            // create the main server socket
            s = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        System.out.println("Waiting for connection");
        for (; ; ) {
            try {
                // wait for a connection
                Socket remote = s.accept();
                // remote is now the connected socket
                System.out.println("Connection, sending data.");
                InputStream in = remote.getInputStream();
                PrintWriter out = new PrintWriter(remote.getOutputStream());

                // read the data sent. We basically ignore it,
                // stop reading once a blank line is hit. This
                // blank line signals the end of the client HTTP
                // headers.
                boolean getter = false;

                String str = ".";
                List<String> header = new LinkedList<>();
                while (str != null && !str.equals("")){
                    str = "";
                    char c;
                    while ((c=(char)in.read()) != '\n'){
                        if(c != '\r'){
                            str += c;
                        }
                    }
                    header.add(str);
                    System.out.println(str);
                }


                Request request = new Request(header);


                System.out.println(request.getResource());

                String contentLength = request.getAttrib("Content-Length");
                if(contentLength!= null){
                    int nByte = Integer.parseInt(contentLength);
                    request.setBody(in.readNBytes(nByte));
                }

                String errCode = "200 OK";

                switch (request.getMethod()){
                    case "HEAD":
                    case "GET":
                        if (request.getResource().equals("DynamicScript")){



                            String outputString = "";
                            switch (request.getPutAttrib("calculation")){
                                case "add":
                                    try{
                                        outputString += Integer.parseInt(request.getPutAttrib("op1")) + Integer.parseInt(request.getPutAttrib("op2"));
                                        out.println("HTTP/1.0 200 OK");
                                    }catch(Exception e){
                                        out.println("HTTP/1.0 500 INTERNAL_SERVER_ERROR");
                                        outputString += "INTERNAL_SERVER_ERROR 500";
                                    }

                                break;
                                case "time":
                                    outputString = LocalTime.now().toString();
                                    out.println("HTTP/1.0 200 OK");
                                    break;
                                default:
                                    outputString = "No Info";
                                    out.println("HTTP/1.0 200 OK");
                            }

                            out.println("");
                            out.println(outputString);
                            out.flush();
                        }else{
                            byte o[] = getFileContents(request.getResource());
                            // Send the response
                            // Send the headers
                            if(o != null){
                                out.println("HTTP/1.0 200 OK");
                                out.println("Content-Type: " + getContentType(request.getResource()));
                                out.println("Server: Bot");
                                // this blank line signals the end of the headers
                                out.println("");
                                out.flush();
                                // Send the HTML page
                                if(request.getMethod().equals("GET"))
                                    remote.getOutputStream().write(o,0,o.length);
                            }else{
                                out.println("HTTP/1.0 404 NOT_FOUND");
                                out.println("");
                                out.println("");
                                out.flush();
                            }
                        }
                    break;
                    case "PUT":
                        System.out.println("Put File written " + request.getResource());
                        FileWriter fWriter = new FileWriter(request.getResource());
                        String gotString = new String(request.getBody(), StandardCharsets.UTF_8);
                        fWriter.write(gotString);
                        fWriter.close();
                        System.out.println(gotString);

                        out.println("HTTP/1.0 " + errCode);
                        out.println("");
                        out.println("");
                        out.flush();
                        break;
                    case "POST":
                        System.out.println("POST");
                        gotString = new String(request.getBody(), StandardCharsets.UTF_8);
                        System.out.println(gotString);




                        out.println("HTTP/1.0 " + errCode);
                        out.println("");
                        out.println("");
                        out.flush();
                    break;

                    case "DELETE":
                        System.out.println("Supposed to delete " + request.getResource());

                        out.println("HTTP/1.0 202 DELETE_SUCCESS");
                        out.println("");
                        out.println("");
                        out.flush();
                    break;
                }



                remote.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }

    private String getContentType (String path){
        URL p;
        try {
            p= getClass().getClassLoader().getResource(path);
        }catch(Exception e){
            return  "";
        }
        if(p == null){
            return "";
        }

        Path oPath = null;
        try {
            oPath = Paths.get(p.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            return Files.probeContentType(oPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private byte[] getFileContents(String path){
        URL p;
        try {
             p= getClass().getClassLoader().getResource(path);
        }catch(Exception e){
            return  null;
        }
        if(p == null){
            return null;
        }

        Path oPath = null;
        try {
            oPath = Paths.get(p.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        byte out[] = null;
        try{
            out = Files.readAllBytes(oPath);
        }catch (Exception e){
            System.out.println("error" + e);
        }


        return out;
    }


    /**
     * Start the application.
     *
     * @param args Command line parameters are not used.
     */
    public static void main(String args[]) {
        WebServer ws = new WebServer();
        ws.start(Integer.parseInt(args[0]));
    }
}
