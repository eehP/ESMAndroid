package org.libreapps.rest;

import org.libreapps.rest.obj.Bill;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BillTableModel {

    private ArrayList<Bill> listBills = new ArrayList<>();
    private ConnectionRest connectionRest = null;
    private String token = null;

    public BillTableModel() {
    }
    public  String[][] getBills() {
        listBills = refreshBills();
        Bill obj;
        if(listBills != null) {
            String[][] bills = new String[listBills.size()][4];
            for (int i = 0; i < listBills.size(); i++) {
                obj = listBills.get(i);
                bills[i][0] = "" + obj.getId();
                bills[i][1] = obj.getType();
                bills[i][2] = obj.getName();
                bills[i][3] = "" + obj.getPrice();
            }
            return bills;
        }
        return new String[0][4];
    }
    public ArrayList<Bill> refreshBills(){
        try{
            connectionRest = new ConnectionRest();
            connectionRest.setToken(token);
            connectionRest.execute("GET");
            String listJsonObjs = (String) connectionRest.get();
            if(listJsonObjs != null) {
                return connectionRest.parse(listJsonObjs);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setToken(String monToken) {token = monToken; }
}
