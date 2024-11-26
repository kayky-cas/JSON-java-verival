package org.json.junit;

import static org.junit.Assert.*;
import java.io.StringWriter;
import java.util.*;
import org.json.*;
import org.junit.Test;


public final class VerivalJornadaDeUsusarioTest {
    @Test
    public void parseDeJsonSimples() {
        String jsonString = "{\"appName\":\"MyApp\",\"version\":\"1.0.0\",\"settings\":{\"theme\":\"light\",\"notifications\":true}}";
        JSONObject jsonObject = new JSONObject(jsonString);

        JSONObject expected = new JSONObject()
            .put("appName", "MyApp")
            .put("version", "1.0.0")
            .put("settings", new JSONObject()
                .put("theme", "light")
                .put("notifications", true)
            );

        assertTrue(expected.similar(jsonObject));
    }

    @Test 
    public void modificacaoDePropriedade() {
        JSONObject jsonObject =  new JSONObject()
            .put("appName", "MyApp")
            .put("version", "1.0.0")
            .put("settings", new JSONObject()
                .put("theme", "light")
                .put("notifications", true)
            );

        jsonObject
            .getJSONObject("settings")
            .put("theme", "dark")
            .put("notifications", false);

        JSONObject expected = new JSONObject()
            .put("appName", "MyApp")
            .put("version", "1.0.0")
            .put("settings", new JSONObject()
                .put("theme", "dark")
                .put("notifications", false)
            );

        assertTrue(expected.similar(jsonObject));
    }

    @Test
    public void carregamentoESalvamento() {
        String jsonString = "{\"appName\":\"MyApp\",\"version\":\"1.0.0\",\"settings\":{\"theme\":\"light\",\"notifications\":true}}";
        JSONObject jsonObject = new JSONObject(jsonString);

        jsonObject.getJSONObject("settings").put("theme", "dark").put("notifications", false);

        StringWriter writer = new StringWriter();

        String novaJsonString = jsonObject.write(writer).toString();
        String expected = "{\"settings\":{\"theme\":\"dark\",\"notifications\":false},\"appName\":\"MyApp\",\"version\":\"1.0.0\"}";

        assertEquals(expected, novaJsonString);
    }

    @Test
    public void manipulacaoDeJsonInvalido() {
        String jsonString = "{\"appName\":\"MyApp\",\"version\":\"1.0.0\",\"settings\":{\"theme\":\"light\",\"notifications\":true}";

        assertThrows(JSONException.class, () -> {
            new JSONObject(jsonString);
        });
    }
}

