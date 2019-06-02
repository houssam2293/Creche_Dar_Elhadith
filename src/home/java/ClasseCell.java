package home.java;

import javafx.scene.control.ListCell;

public class ClasseCell extends ListCell<ClasseModel> {
    @Override
    protected void updateItem(ClasseModel item, boolean empty) {
        super.updateItem(item, empty);
        String name = null;

        // Format name
        if (item == null || empty)
        {
        }
        else
        {
            name = item.getClassNam();
        }

        this.setText(name);
        setGraphic(null);
    }
}
