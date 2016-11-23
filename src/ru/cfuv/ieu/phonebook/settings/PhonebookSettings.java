package ru.cfuv.ieu.phonebook.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PhonebookSettings {
    private Map<String, JsonElement> settings = new HashMap<>();

    public PhonebookSettings() {
        loadOrCreateDefault();
    }

    private void loadOrCreateDefault() {
        if ((new File("settings.json")).exists()) {
            try (BufferedReader br = new BufferedReader(
                    new FileReader("settings.json"))) {
                JsonObject root = (new JsonParser()).parse(br)
                        .getAsJsonObject();

                settings.put("formatNumbers", root.getAsJsonPrimitive(
                        "formatNumbers"));
                settings.put("dbPath", root.getAsJsonPrimitive("dbPath"));
                settings.put("fontScale", root.getAsJsonPrimitive("fontScale"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            settings.put("formatNumbers", new JsonPrimitive(true));
            settings.put("dbPath", new JsonPrimitive(""));
            settings.put("fontScale", new JsonPrimitive(200));
            write();
        }
    }

    public void write() {
        JsonObject root = new JsonObject();
        for (String key : settings.keySet()) {
            root.add(key, settings.get(key));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                "settings.json"))) {
            bw.write(root.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getFormatting() {
        return settings.get("formatNumbers").getAsBoolean();
    }

    public void setFormatting(boolean b) {
        settings.replace("formatNumbers", new JsonPrimitive(b));
    }

    public String getDBPath() {
        String p = settings.get("dbPath").getAsString();
        return p.equals("") ? "phonebook.db" : p;
    }

    public void setDBPath(String path) {
        settings.replace("dbPath", new JsonPrimitive(path));
    }

    public int getFontScale() {
        return settings.get("fontScale").getAsInt();
    }

    public void setFontScale(int scale) {
        settings.replace("fontScale", new JsonPrimitive(scale));
    }
}
