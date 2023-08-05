package tlsys.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonFileManager {
    
    private String ressourcePath;

    public JsonFileManager(String ressourcePath) {
        this.ressourcePath = ressourcePath;
    }


}
