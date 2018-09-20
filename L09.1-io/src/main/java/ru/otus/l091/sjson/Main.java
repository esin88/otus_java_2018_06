package ru.otus.l091.sjson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.JsonObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tully.
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        //JSONObject jsonObject = read();
        JSONObject jsonObject = create();
        navigateTree(jsonObject, "root");
    }

    private static JSONObject read() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse((new FileReader("jsondata.txt")));
    }

    @SuppressWarnings("unchecked")
    private static JSONObject create() {
        JSONObject root = new JSONObject();
        root.put("Key23", "Value42");
        JSONObject address = new JSONObject();
        address.put("Street", "Test");
        root.put("address", address);
        return root;
    }

    @SuppressWarnings("unchecked")
    private static void navigateTree(Object aware, String key) {
        // System.out.println(key + ": " + aware);

        String awareClassName = aware.getClass().getSimpleName();
        switch (awareClassName) {
            case "JSONArray":
                JSONArray array = (JSONArray) aware;
                array.forEach(element -> navigateTree(element, "array_element"));
                break;
            case "JSONObject":
                JSONObject jsonObject = (JSONObject) aware;
                jsonObject.entrySet().forEach(element -> navigateTree(element, "set_element"));
                break;
            case "Node":
                Map.Entry entry = (Map.Entry) aware;
                navigateTree(entry.getValue(), entry.getKey().toString());
                break;
            default:
                System.out.println(key + ": " + aware);

        }
    }
}
