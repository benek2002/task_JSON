package org.example;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing file path argument");
        }
        String jsonFilePath = args[0];
        boolean isValid = verifyInput(readJsonFile(jsonFilePath));
        System.out.println(isValid);
    }

    public static String readJsonFile(String jsonFilePath) throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        } catch (InvalidPathException e) {
            throw new IOException("Invalid file path", e);
        } catch (IOException e) {
            throw new IOException("Error reading JSON file", e);
        }

    }

    public static boolean verifyInput(String jsonContent) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonContent);
            JSONObject policyDocument = (JSONObject) jsonObject.get("PolicyDocument");
            JSONArray statements = (JSONArray) policyDocument.get("Statement");
            JSONObject statement = (JSONObject) statements.get(0);
            if (statement.containsKey("Resource")) {
                String resource = (String) statement.get("Resource");
                return !resource.equals("*");
            } else {
                return true;
            }
        }catch(ParseException e){
            throw new RuntimeException("Error parsing JSON File");
        }
    }
}
