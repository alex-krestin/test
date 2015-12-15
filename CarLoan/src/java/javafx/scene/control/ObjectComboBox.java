package javafx.scene.control;


import entity.Embedded;
import entity.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import presentation.FrontController;
import presentation.helper.EntityCode;

import java.util.List;

import static presentation.helper.OperationCode.GET_ALL;

public class ObjectComboBox<T extends Embedded> extends ComboBox<T> {

    public void loadValues(EntityCode entityCode) {
        Response response = FrontController.handleRequest(GET_ALL, entityCode, null);
        List<?> responseList = response.getList();

        ObservableList<T> list = FXCollections.observableArrayList();

        for(Object object : responseList) {
            @SuppressWarnings("unchecked")
            T t = (T) object;
            list.add(t);
        }

        setValues(list);
    }

    public void setValues(ObservableList<T> observableList) {
        ObjectListCell<T> objectListCell = new ObjectListCell<>();
        setConverter(objectListCell.getStringConverter());

        setItems(observableList);
        //getSelectionModel().selectFirst();
    }
}
