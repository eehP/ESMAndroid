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

    public String getId() { return id; }

    public String getName() { return name; }

    public String getDate() { return date; }

    public String getType() { return type; }

    public String getPrice() { return price; }

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


