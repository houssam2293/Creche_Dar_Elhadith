package home.java;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class EleveCellFactory implements Callback<ListView<Eleve>, ListCell<Eleve>> {
    @Override
    public ListCell<Eleve> call(ListView<Eleve> param) {
        return new EleveCell();
    }
}
