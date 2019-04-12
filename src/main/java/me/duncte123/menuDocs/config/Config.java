package me.duncte123.menuDocs.config;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class Config extends JSONObject {

    private static Config instance;

    public Config(File file) throws IOException {
        super(new ConfigLoader().load(file));

        instance = this;
    }

    public static Config getInstance() {
        return instance;
    }
}
