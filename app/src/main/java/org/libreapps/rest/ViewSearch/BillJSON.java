package org.libreapps.rest.ViewSearch;

import java.io.Serializable;

public class BillJSON implements Serializable {

    String id;
    String name;
    String date;
    String type;
    String price;

    public BillJSON(String id, String name, String date, String type, String price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
        this.price = price;
    }

    public String getId() { return this.id; }

    public String getName() { return this.name; }

    public String getDate() { return this.date; }

    public String getType() { return this.type; }

    public String getPrice() { return this.price; }

    @Override
    public String toString() {
        return "BillJSON{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}


