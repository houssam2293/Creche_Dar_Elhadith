package home.dbDir;

import home.java.EntreStock;

import java.util.LinkedList;
import java.util.List;

public class StockDB {
    private static StockDB INSTANCE;
    private List<EntreStock> listeStock;
    private StockDB(){
        listeStock = new LinkedList<>();
    }
    public static StockDB getStockDB(){
        if (INSTANCE == null)INSTANCE = new StockDB();
        return INSTANCE;
    }

    public List<EntreStock> getListeStock() {
        return listeStock;
    }
}
