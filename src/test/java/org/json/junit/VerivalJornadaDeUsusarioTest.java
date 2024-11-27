package org.json.junit;

import static org.junit.Assert.*;
import org.json.*;
import org.junit.Test;

public final class VerivalJornadaDeUsusarioTest {
    @Test
    public void parseHTTPToJSON() {
        String httpBody = "{\"transactionId\":\"12345\",\"user\":{\"id\":\"67890\",\"name\":\"John Doe\"},\"amount\":150.00,\"currency\":\"USD\"}";

        JSONObject expected = new JSONObject()
                .put("transactionId", "12345")
                .put("user", new JSONObject()
                        .put("id", "67890")
                        .put("name", "John Doe"))
                .put("amount", 150.00)
                .put("currency", "USD");

        JSONObject jsonObject = new JSONObject(httpBody);

        assertTrue(expected.similar(jsonObject));
    }

    @Test
    public void modificaçãoDeValoresNoJSON() {
        JSONObject jsonObject = new JSONObject()
                .put("transactionId", "12345")
                .put("user", new JSONObject()
                        .put("id", "67890")
                        .put("name", "John Doe"))
                .put("amount", 150.00)
                .put("currency", "USD");

        JSONObject expected = new JSONObject()
                .put("transactionId", "12345")
                .put("user", new JSONObject()
                        .put("id", "67890")
                        .put("name", "Jane Doe"))
                .put("amount", 200.00)
                .put("currency", "BRL");

        jsonObject.put("amount", 200.00);
        jsonObject.put("currency", "BRL");
        jsonObject.getJSONObject("user").put("name", "Jane Doe");

        assertTrue(expected.similar(jsonObject));
    }

    @Test
    public void conversãoCompletaDeJSONParaXML() {
        JSONObject jsonObject = new JSONObject()
                .put("transactionId", 12345)
                .put("user", new JSONObject()
                        .put("id", 67890)
                        .put("name", "Jane Doe"))
                .put("amount", 200.00)
                .put("currency", "BRL");

        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><transaction><transactionId>12345</transactionId><user><id>67890</id><name>Jane Doe</name></user><amount>200.0</amount><currency>BRL</currency></transaction>";

        JSONObject expected = XML.toJSONObject(expectedXML);
        assertTrue(expected.getJSONObject("transaction").similar(jsonObject));
    }

    @Test
    public void manipulacaoDeJsonInvalido() {
        String jsonString = "{\"appName\":\"MyApp\",\"version\":\"1.0.0\",\"settings\":{\"theme\":\"light\",\"notifications\":true}";

        assertThrows(JSONException.class, () -> {
            new JSONObject(jsonString);
        });
    }
}
