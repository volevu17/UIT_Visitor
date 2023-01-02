package com.example.uit_visitor.Models;

import com.google.gson.JsonObject;

public class Map {
    public JsonObject options;
    public String version;

    public JsonObject getOptions() {
        return options;
    }

    public void setOptions(JsonObject options) {
        this.options = options;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
