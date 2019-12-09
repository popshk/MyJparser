package com.popshk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class CreateJson {
    // disableHtmlEscaping() - fix problem with \u0026 etc.
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    /*
    *Windows - check user directory
    *Linux - check home directory
     */
    File file =new File("result.json");

    public void writeToXml(Products products) throws IOException {
        String json = GSON.toJson(products);
        //System.out.println(json);
        BufferedWriter writer =new BufferedWriter(new FileWriter(file,true));
        writer.write(json+"\n");
        writer.flush();
        writer.close();
    }
}
