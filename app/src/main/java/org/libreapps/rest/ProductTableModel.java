package org.libreapps.rest;
import org.libreapps.rest.obj.Product;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProductTableModel {
    private String[] columnNames = {"Date","Nom","Prix"};
    private ArrayList<Product> listProducts = new ArrayList<Product>();
    private ConnectionRest connectionRest = null;

    //CONSTRUCTOR
    public ProductTableModel() {
    }
    //RETURN TABLE HEADERS
    public String[] getProductHeaders() {
        return columnNames;
    }
    //RETURN TABLE ROWS
    public  String[][] getProducts() {
        listProducts = refreshProducts();
        Product obj;
        if(listProducts != null) {
            String[][] products = new String[listProducts.size()][3];
            for (int i = 0; i < listProducts.size(); i++) {
                obj = listProducts.get(i);
                products[i][0] = obj.getType();
                products[i][1] = obj.getName();
                products[i][2] = "" + obj.getPrice();
            }
            return products;
        }
        return new String[0 ][3];
    }
    public ArrayList<Product> refreshProducts(){
        try{
            connectionRest = new ConnectionRest();
            connectionRest.execute("GET");
            String listJsonObjs = (String) connectionRest.get();
            if(listJsonObjs != null) {
                return connectionRest.parse(listJsonObjs);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Product get(int row){
        return listProducts.get(row);
    }
}
