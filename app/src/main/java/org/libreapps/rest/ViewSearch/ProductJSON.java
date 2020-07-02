package org.libreapps.rest.ViewSearch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductJSON implements Serializable {

    String id;

    String name;

    String type;

    String price;

    public ProductJSON(String id, String name, String type, String price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getType() { return type; }

    public String getPrice() { return price; }

    @Override
    public String toString() {
        return "ProductJSON{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}


