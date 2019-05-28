package home.java;

import javafx.scene.control.ListCell;

public class EleveCell extends ListCell<Eleve> {
    @Override
    protected void updateItem(Eleve item, boolean empty) {
        super.updateItem(item, empty);

        String name = null;

        // Format name
        if (item == null || empty)
        {
        }
        else
        {
            name = item.getNom() + " " +
                    item.getPrenom();
        }

        this.setText(name);
        setGraphic(null);
    }
}
