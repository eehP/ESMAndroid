package org.libreapps.rest.obj;
import org.json.JSONObject;

public class Bill {
    private final int id;
    private final String name;
    private final String date;
    private final String type;
    private final double price;

    public Bill(JSONObject jObject) {
        this.id = jObject.optInt("id");
        this.name = jObject.optString("name");
        this.date = jObject.optString("date");
        this.type = jObject.optString("type");
        this.price = jObject.optDouble("price");
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getType() { return type; }
    public double getPrice() { return price; }
}