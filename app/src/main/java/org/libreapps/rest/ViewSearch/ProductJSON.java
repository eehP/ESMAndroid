package org.libreapps.rest.ViewSearch;

import com.google.gson.annotations.SerializedName;

public class ProductJSON {

    private int id;

    private String name;

    private String type;

    private String price;
    @SerializedName("body")
    private String text;

    public String getText() { return text; }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getType() { return type; }

    public String getPrice() { return price; }
}


