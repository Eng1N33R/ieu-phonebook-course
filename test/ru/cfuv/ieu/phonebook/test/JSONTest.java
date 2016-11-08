package ru.cfuv.ieu.phonebook.test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;

public class JSONTest {
    @Test
    public void JSONParseTest() {
        try {
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(new FileReader("testres/test.json")).getAsJsonObject();
            assertEquals("Johdfgsdfgn", obj.get("firstName").getAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
