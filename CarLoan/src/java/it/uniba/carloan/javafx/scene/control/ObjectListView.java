package it.uniba.carloan.javafx.scene.control;

import it.uniba.carloan.entity.Embedded;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.helper.EntityCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.List;

import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;


public class ObjectListView<T extends Embedded> extends ListView<T> {

    private ObservableList<T> list = FXCollections.observableArrayList();

    public void loadValues(EntityCode entityCode) {
        Response response = FrontController.handleRequest(GET_ALL, entityCode, null);
        List<?> responseList = response.getList();

        for(Object object : responseList) {
            @SuppressWarnings("unchecked")
            T t = (T) object;
            list.add(t);
        }

        setValues(list);
    }

    public void setValues(ObservableList<T> observableList) {
        ObjectListCell<T> objectListCell = new ObjectListCell<>();
        setCellFactory(objectListCell.getCallback());

        list = observableList;
        setItems(observableList);
    }

    public void updateList() {
        setValues(list);
    }





}
