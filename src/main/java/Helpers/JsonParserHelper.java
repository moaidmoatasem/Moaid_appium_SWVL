package Helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;

public class JsonParserHelper {
    Gson gson;
    JsonObject jsonFile;

    public JsonParserHelper()
    {
        gson = new Gson();
    }
    /**
     *
     * @param path: json file path
     * @return the json file in the format of json Object
     */
    public JsonObject getJsonObject(String path )
    {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            jsonFile = gson.fromJson(bufferedReader, JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonFile;
    }
}
