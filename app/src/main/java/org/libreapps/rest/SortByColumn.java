package org.libreapps.rest;

import java.util.Arrays;
import java.util.Comparator;

public class SortByColumn {

    public static String[][] myStringSort(String[][] data, final int m_column) {
        String[][] transposed = data;
        Arrays.sort(transposed, new Comparator<String[]>() {
            @Override
            public int compare(String[] a1, String[] a2) {
                return a1[m_column].compareTo(a2[m_column]);
            }
        });
        return transposed;
    }

    public static String[][] myDateSort(String[][] data, final int m_column) {
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

    public static String[][] myFloatSort(String[][] data, final int m_column) {
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
            System.out.println(m_price[0]);
            if(m_price.length == 1){
                transposed[increment][m_column] = m_price[0];
            }else{
                transposed[increment][m_column] = m_price[0]+ "." +m_price[1];
            }
        }
        return transposed;
    }


}
