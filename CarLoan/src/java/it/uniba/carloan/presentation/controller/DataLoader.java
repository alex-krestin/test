package it.uniba.carloan.presentation.controller;


import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.exception.DataLoadingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.presentation.AlertHandler.showException;

public abstract class DataLoader<T extends TransferObject> {
    private static final Logger log = Logger.getLogger(DataLoader.class.getName());

    private final HBox spinnerBox;
    private final ObservableList<T> loadedData = FXCollections.observableArrayList();

    protected abstract Response dataLoadRequest();
    protected abstract HBox spinnerBox();

    protected DataLoader() {
        this.spinnerBox = spinnerBox();
        startLoadingThread();
    }

    public ObservableList<T> getLoadedData() {
        return this.loadedData;
    }

    private void startLoadingThread() {

        Task dataLoader;
        dataLoader = getSimpleTask();

        // uncomment this if you want to show spinner on main view loading
        /*
        if (spinnerBox == null) {
            dataLoader = getSimpleTask();
        }
        else {
            dataLoader = getSimpleTask();
        }*/

        Thread loadingThread = new Thread(dataLoader, "data-loader");
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

    private Task getSimpleTask() {
        return new Task<Boolean>() {
            {
                setOnFailed(workerStateEvent -> {
                    showException("Errore di caricamento dati", new DataLoadingException());
                    log.log(Level.WARNING, "Failed load data", getException());
                });
            }

            @Override
            protected Boolean call() throws Exception {
                return loadData();
            }
        };
    }

    private Task getTaskWithLoader() {
        // start displaying the loading indicator at the Application Thread
        spinnerBox.setVisible(true);

        return new Task<Boolean>() {
            {
                setOnSucceeded(workerStateEvent -> {
                    spinnerBox.setVisible(false); // stop displaying the loading indicator
                });
                setOnFailed(workerStateEvent -> {
                    spinnerBox.setVisible(false); // stop displaying the loading indicator
                    showException("Errore di caricamento dati", new DataLoadingException());
                    //getException().printStackTrace();
                });
            }

            @Override
            protected Boolean call() throws Exception {
                return loadData();
            }
        };
    }

    private boolean loadData() {
        // request all users from the database
        Response response = dataLoadRequest();

        if (response != null) {
            T item;

            // extract list of objects <T> from TransferObject
            List<?> list = response.getList();

            // add all objects to ObservableList
            for(Object object : list) {
                item = (T) object;
                loadedData.add(item);
            }
        }

        return true;
    }
}
