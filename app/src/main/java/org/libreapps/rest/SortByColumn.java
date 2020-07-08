package org.libreapps.rest;

import org.json.JSONArray;
import org.libreapps.rest.ViewSearch.BillJSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortByColumn {

    public static List<BillJSON> sortTable(List<BillJSON> list, final String m_sortType){
        String[][] m_sortTable;
        List<BillJSON> m_finalTable;
        switch(m_sortType){
            case "Id" :
                m_sortTable = myStringSort(BillList_ToStringArray(list), 0);
                m_finalTable = StringArray_ToBillList(m_sortTable);
                break;

            case "Name":
            case "Nickname":
                m_sortTable = myStringSort(BillList_ToStringArray(list), 1);
                m_finalTable = StringArray_ToBillList(m_sortTable);
                break;

            case "Date":
                m_sortTable = myDateSort(BillList_ToStringArray(list), 2);
                m_finalTable = StringArray_ToBillList(m_sortTable);
                break;

            case "Type":
                m_sortTable = myStringSort(BillList_ToStringArray(list), 3);
                m_finalTable = StringArray_ToBillList(m_sortTable);
                break;

            case "Price":
                m_sortTable = myFloatSort(BillList_ToStringArray(list), 4);
                m_finalTable = StringArray_ToBillList(m_sortTable);
                break;

            default :
                m_finalTable = list;
                break;
        }
        return m_finalTable;
    }

    private static String[][] BillList_ToStringArray(List<BillJSON> m_list){
        String[][] m_array = new String[m_list.size()][5];

        for(int increment = 0; increment < m_list.size(); increment++){
            BillJSON m_element = m_list.get(increment);
            m_array[increment][0] = m_element.getId();
            m_array[increment][1] = m_element.getName();
            m_array[increment][2] = m_element.getDate();
            m_array[increment][3] = m_element.getType();
            m_array[increment][4] = m_element.getPrice();
        }
        return m_array;
    }

    private static List<BillJSON> StringArray_ToBillList(String[][] m_array){
        List<BillJSON> m_list = new ArrayList<BillJSON>();

        for(int increment = 0; increment < m_array.length; increment++){
            String[] m_element = m_array[increment];
            m_list.add(new BillJSON(
                    m_element[0],
                    m_element[1],
                    m_element[2],
                    m_element[3],
                    m_element[4]
                    ));
        }

        return m_list;
    }

    private static String[][] myStringSort(String[][] data, final int m_column) {
        String[][] transposed = data;
        Arrays.sort(transposed, new Comparator<String[]>() {
            @Override
            public int compare(String[] a1, String[] a2) {
                return a1[m_column].compareToIgnoreCase(a2[m_column]);
            }
        });
        return transposed;
    }

    private static String[][] myDateSort(String[][] data, final int m_column) {
        String[][] transposed = data;
        for(int increment = 0; increment < transposed.length; increment++){
            String[] m_date = transposed[increment][m_column].split("/");
            transposed[increment][m_column] = m_date[2] + "/" + m_date[1] + "/" + m_date[0];
        }
        Arrays.sort(transposed, new Comparator<String[]>() {
            @Override
            public int compare(String[] a1, String[] a2) {
                return a1[m_column].compareTo(a2[m_column]);
            }
        });
        for(int increment = 0; increment < transposed.length; increment++){
            String[] m_date = transposed[increment][m_column].split("/");
            transposed[increment][m_column] = m_date[2] + "/" + m_date[1] + "/" + m_date[0];
        }
        return transposed;
    }

    private static String[][] myFloatSort(String[][] data, final int m_column) {
        String[][] transposed = data;
        for(int increment = 0; increment < transposed.length; increment++){
            String[] m_price = transposed[increment][m_column].split("\\.");
            for(int zeroIncrement = m_price[0].length(); zeroIncrement<5; zeroIncrement++){
                m_price[0] = "0" + m_price[0];
            }
            if(m_price.length == 1){
                transposed[increment][m_column] = m_price[0];
            }else{
                transposed[increment][m_column] = m_price[0]+ "." +m_price[1];
            }
        }
        Arrays.sort(transposed, new Comparator<String[]>() {
            @Override
            public int compare(String[] a1, String[] a2) {
                return a1[m_column].compareTo(a2[m_column]);
            }
        });
        for(int increment = 0; increment < transposed.length; increment++){
            String[] m_price = transposed[increment][m_column].split("\\.");
            String[] m_digits = m_price[0].split("");
            int m_zeroIncrement = 0;
            while(m_zeroIncrement<m_digits.length && m_digits[m_zeroIncrement].equals("0")){
                m_zeroIncrement += 1;
            }
            String m_value = "";
            for(int add_element = m_zeroIncrement; add_element < m_digits.length; add_element++){
                m_value = m_value+m_digits[add_element];
            }
            m_price[0] = m_value;
            if(m_price.length == 1){
                transposed[increment][m_column] = m_price[0];
            }else{
                transposed[increment][m_column] = m_price[0]+ "." +m_price[1];
            }
        }
        return transposed;
    }


}
