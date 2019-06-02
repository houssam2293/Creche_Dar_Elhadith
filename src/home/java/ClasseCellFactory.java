package home.java;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ClasseCellFactory implements Callback<ListView<ClasseModel>, ListCell<ClasseModel>> {
    @Override
    public ListCell<ClasseModel> call(ListView<ClasseModel> param) {
        return new ClasseCell();
    }
}
