package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.dbDir.StockDB;
import home.java.EntreStock;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static home.controllers.EditStockFormController.stockSelected;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class StockController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXComboBox<String> combo;
    @FXML
    private JFXButton Refresher;

    @FXML
    private JFXButton Adder;

    @FXML
    private JFXButton Editer;

    @FXML
    private JFXButton Remover;

    @FXML
    private JFXButton Printer;

    @FXML
    private JFXTreeTableView<TableStock> tableView;

    @FXML // Cols of table
    private JFXTreeTableColumn<TableStock, String> idColumn, nomColumn, typeColmn, dateFabColumn,
            dateExpColumn, quantiteColumn, prixColumn, fournisseurColumn, prixTotaleColumn;

    static JFXDialog addUserDialog, editUserDialog;


    @FXML
    void Consommation(ActionEvent event) {
        errorLabel.setText("");
        AnchorPane addUserPane = null;
        try {
            addUserPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/SortieStock.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addUserPane);
        addUserDialog.show();
    }

    @FXML
    void addstock(ActionEvent event) {
        errorLabel.setText("");
        AnchorPane addUserPane = null;
        try {
            addUserPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/EntreStockForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addUserPane);
        addUserDialog.show();

    }

    @FXML
    void print(ActionEvent event) {


    }

    @FXML
    void editstock(ActionEvent event) {
        errorLabel.setText("");
        int index = tableView.getSelectionModel().getSelectedIndex(); // selected index
        String id = idColumn.getCellData(index);
        if (id == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }

        stockSelected = new EntreStock();
        stockSelected.setId(parseInt(id));
        stockSelected.setNom(nomColumn.getCellData(index));
        System.out.println("type : " + typeColmn.getCellData(index));
        switch (typeColmn.getCellData(index)) {
            case "طعام":
                stockSelected.setTypeProduit(1);
            case "كتب و كراريس":
                stockSelected.setTypeProduit(2);
            case "أخرى":
                stockSelected.setTypeProduit(3);
        }
        stockSelected.setDateFab(java.sql.Date.valueOf(dateFabColumn.getCellData(index)));
        stockSelected.setDateExp(java.sql.Date.valueOf(dateExpColumn.getCellData(index)));
        stockSelected.setQuantite(parseInt(quantiteColumn.getCellData(index)));
        stockSelected.setPrix(Double.parseDouble(prixColumn.getCellData(index)));
        stockSelected.setFournisseur(fournisseurColumn.getCellData(index));
        //stockSelected.setPrixTotale(Double.parseDouble(prixTotaleColumn.getCellData(index)));
        AnchorPane editUserPane = null;
        try {
            editUserPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/EditstockForm.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        editUserDialog = getSpecialDialog(editUserPane);
        editUserDialog.show();


    }

    @FXML
    void removestock(ActionEvent event) {
        errorLabel.setText("");
        int id;
        if (idColumn.getCellData(tableView.getSelectionModel().getSelectedIndex()) == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد مسحه                                 ")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .darkStyle()
                    .showWarning();
            return;
        }
        id = valueOf(idColumn.getCellData(tableView.getSelectionModel().getSelectedIndex()));
        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("تأكيد العملية");
        Text contentText = new Text("هل أنت متأكد من مسح البيانات؟");
        headerText.setStyle("-fx-font-size: 19px");
        contentText.setStyle("-fx-font-size: 18px");

        content.setHeading(headerText);
        content.setBody(contentText);

        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);

        JFXButton btnOk = new JFXButton("نعم");
        btnOk.setOnAction(e -> {
            int status = new StockDB().deleteStock(id);
            System.out.println("status : " + status);
            if (status == -1) {
                errorLabel.setText("Connection Failed !");
            } else {
                Notifications notification = Notifications.create()
                        .title("تمت العملية بنجاح                               ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT);
                notification.darkStyle();
                notification.show();
                updateTable();
            }
            dialog.close();
        });

        JFXButton btnNo = new JFXButton("لا");
        btnOk.setPrefSize(120, 40);
        btnNo.setPrefSize(120, 40);
        btnOk.setStyle("-fx-font-size: 18px");
        btnNo.setStyle("-fx-font-size: 18px");

        content.setActions(btnOk, btnNo);

        dialog.getStylesheets().add("/home/resources/css/main.css");
        btnNo.setOnAction(e -> dialog.close());
        dialog.show();

    }

    @FXML
    void updateTable() {
        errorLabel.setText("");
        ObservableList<TableStock> stocks = FXCollections.observableArrayList();
        combo.setValue(null);

        List<EntreStock> stockDB = new StockDB().getStock();
        if (stockDB == null) {
            errorLabel.setText("Connection Failed !");
        } else {
            for (EntreStock stock : stockDB) {
                stocks.add(new TableStock(stock.getId(), stock.getNom().toUpperCase(), stock.getProdectName(), stock.getDateFab(), stock.getDateExp(), stock.getQuantite(), stock.getPrix(), stock.getFournisseur(), stock.getPrixTotale()));
            }

        }
        final TreeItem<TableStock> item = new RecursiveTreeItem<>(stocks, RecursiveTreeObject::getChildren);
        try {
            tableView.setRoot(item);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        combo.getItems().addAll("رقم السلعة", "نوع السلعة", "إسم السلعة", "تاريخ  دخول", "تاريخ  خروج", "كمية", "سعر الوحدة", "الممول");
        initializeTable();

        Refresher.setTooltip(new Tooltip("تحديث"));

        Adder.setTooltip(new Tooltip("إضافة"));
        Editer.setTooltip(new Tooltip("تعديل"));
        Remover.setTooltip(new Tooltip("إزالة"));
        Printer.setTooltip(new Tooltip("طبع"));

    }

    private void initializeTable() {
        idColumn = new JFXTreeTableColumn<>("رقم السلعة");
        idColumn.setPrefWidth(149);
        idColumn.setCellValueFactory(param -> param.getValue().getValue().id);

        nomColumn = new JFXTreeTableColumn<>("إسم السلعة");
        nomColumn.setPrefWidth(149);
        nomColumn.setCellValueFactory(param -> param.getValue().getValue().nom);

        typeColmn = new JFXTreeTableColumn<>("نوع السلعة");
        typeColmn.setPrefWidth(149);
        typeColmn.setCellValueFactory(param -> param.getValue().getValue().type);

        dateFabColumn = new JFXTreeTableColumn<>("تاريخ الادخال");
        dateFabColumn.setPrefWidth(149);
        dateFabColumn.setCellValueFactory(param -> param.getValue().getValue().dateFab);

        dateExpColumn = new JFXTreeTableColumn<>("تاريخ الاخراج");
        dateExpColumn.setPrefWidth(149);
        dateExpColumn.setCellValueFactory(param -> param.getValue().getValue().dateExp);

        quantiteColumn = new JFXTreeTableColumn<>("كمية");
        quantiteColumn.setPrefWidth(149);
        quantiteColumn.setCellValueFactory(param -> param.getValue().getValue().quantite);

        prixColumn = new JFXTreeTableColumn<>("سعر الوحدة");
        prixColumn.setPrefWidth(149);
        prixColumn.setCellValueFactory(param -> param.getValue().getValue().prix);


        fournisseurColumn = new JFXTreeTableColumn<>("الممول");
        fournisseurColumn.setPrefWidth(149);
        fournisseurColumn.setCellValueFactory(param -> param.getValue().getValue().fournisseur);

        prixTotaleColumn = new JFXTreeTableColumn<>("السعر الاجمالي");
        prixTotaleColumn.setPrefWidth(149);
        prixTotaleColumn.setCellValueFactory(param -> param.getValue().getValue().prixTotale);
        updateTable();

        searchField.textProperty().addListener(e -> filterSearchTable());
        combo.setOnAction(e -> filterSearchTable());

        //noinspection deprecation
        tableView.getColumns().addAll(idColumn, nomColumn, typeColmn, dateFabColumn, dateExpColumn, quantiteColumn, prixColumn, fournisseurColumn, prixTotaleColumn);
        tableView.setShowRoot(false);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> updateTable());
        return dialog;
    }

    private void filterSearchTable() {
        tableView.setPredicate(stocks -> {
            switch (combo.getSelectionModel().getSelectedIndex()) {
                case 0:
                    return stocks.getValue().id.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 1:
                    return stocks.getValue().nom.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 2:
                    return stocks.getValue().dateFab.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 3:
                    return stocks.getValue().dateExp.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 4:
                    return stocks.getValue().quantite.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 5:
                    return stocks.getValue().prix.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 6:
                    return stocks.getValue().fournisseur.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 7:
                    return stocks.getValue().type.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                default:
                    return stocks.getValue().id.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            stocks.getValue().nom.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            stocks.getValue().dateFab.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            stocks.getValue().dateExp.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            stocks.getValue().quantite.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            stocks.getValue().prix.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            stocks.getValue().fournisseur.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            stocks.getValue().type.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
            }
        });
    }

    private class TableStock extends RecursiveTreeObject<TableStock> {
        StringProperty id;
        StringProperty nom;
        StringProperty type;
        StringProperty dateFab;
        StringProperty dateExp;
        StringProperty quantite;
        StringProperty prix;
        StringProperty fournisseur;
        StringProperty prixTotale;

        TableStock(int id, String nom, String type, Date dateFab, Date dateExp, int quantite, double prix, String fournisseur, double prixTotale) {
            this.id = new SimpleStringProperty(String.valueOf(id));
            this.nom = new SimpleStringProperty(String.valueOf(nom));
            this.type = new SimpleStringProperty(String.valueOf(type));
            this.dateFab = new SimpleStringProperty(String.valueOf(dateFab));
            this.dateExp = new SimpleStringProperty(String.valueOf(dateExp));
            this.quantite = new SimpleStringProperty(String.valueOf(quantite));
            this.prix = new SimpleStringProperty(String.valueOf(prix));
            this.fournisseur = new SimpleStringProperty(String.valueOf(fournisseur));
            this.prixTotale = new SimpleStringProperty(String.valueOf(prixTotale));

        }
    }

}


