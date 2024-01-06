package com.example.cryptoapp;

import com.example.cryptoapp.models.BaseModel;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.services.IEntityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public abstract class TableViewBaseController<T extends BaseModel>
        extends BaseController
        implements IChildWindowChangesListener {

    @FXML
    protected TableView<T> tableView;

    public abstract List<T> getTableViewItems();

    public void updateTableViewContent() {
        List<T> entities;

        var findAllResult = getTableViewItems();
        entities = findAllResult != null ? findAllResult : new ArrayList<>();


        System.out.println("TableView-inherited controller updated its table with " + entities.stream().count() + " items.");

        ObservableList<T> entityList = FXCollections.observableArrayList(entities);
        tableView.setItems(entityList);
    }

    public void onChildWindowChanges() {
        updateTableViewContent();
    }
}
