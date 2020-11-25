package http.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private String method;
    private String resource;
    private String httpVersion;
    private byte[] body = null;
    private Map<String,String> attributes;
    private Map<String,String> putAttribs;

    /**
     * Creates a new request which then reads the headers sent and parses it into different objects that can then be read
     * @param header a list of strings that are composed of the information received by the port.
     */
    public Request(List<String> header) {
        if (header.size() > 0) {
            String split[] = header.get(0).split(" ");
            method = split[0];
            switch (method){
                case "GET":
                case "HEAD":
                case "DELETE":
                case "OPTIONS":
                case "POST":
                case "PUT":
                    putAttribs = new HashMap<>();
                    if(split[1].contains("?")){
                        String[] putSplit = split[1].split("\\?");
                        resource = putSplit[0].substring(1);
                        String[] attribs = putSplit[1].split("&");
                        for(String attr:attribs){
                            String[] attrSplit = attr.split("=");
                            putAttribs.put(attrSplit[0],attrSplit[1]);
                        }
                    }else{
                        resource = split[1].substring(1);
                    }
                    httpVersion = split[2];
                break;

            }
        }

        attributes = new HashMap<>();
        for(String str:header){
            if(!str.equals(header.get(0)) && !str.equals("")){
                String split[] = str.split(": ");
                attributes.put(split[0],split[1]);
            }
        }
    }

    /**
     * Sets the body as a certain array of byte
     * @param b the new body
     */
    public void setBody(byte b[]){
        body = b;
    }

    /**
     * Returns the attributes that have been parsed
     * @param type the name of the attribute as a string
     * @return the attribute that has been gotten
     */
    public String getAttrib(String type){
        return attributes.get(type);
    }

    /**
     * Returns attributes that were added as part of the url
     * @param type the name of the attribute as a string
     * @return the attribute that has been gotten
     */
    public  String getPutAttrib(String type) {
        return putAttribs.getOrDefault(type, "");


    }
    /**
     * Returns the body as an array of bytes, returns and empty array if no body
     * @return The body to return
     */
    public byte[] getBody(){
        if(body == null){
            body = new byte[0];
        }
        return body;
    }

    /**
     * Returns the method
     * @return the method from the request
     */
    public String getMethod() {
        return method;
    }

    /**
     *  Returns the Resource
     * @return the resource from the request
     */
    public String getResource() {
        return resource;
    }
}
