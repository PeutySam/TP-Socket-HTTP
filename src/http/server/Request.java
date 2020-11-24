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

    public Request(List<String> header) {
        if (header.size() > 0) {
            String split[] = header.get(0).split(" ");
            method = split[0];
            switch (method){
                case "GET":
                case "HEAD":
                case "DELETE":
                case "OPTIONS":
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

    public void setBody(byte b[]){
        body = b;
    }

    public String getAttrib(String type){
        return attributes.get(type);
    }

    public  String getPutAttrib(String type) {
        return putAttribs.getOrDefault(type, "");


    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }
}
