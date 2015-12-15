package javafx.scene.control;


import entity.Embedded;
import javafx.util.Callback;
import javafx.util.StringConverter;

class ObjectListCell<T extends Embedded> {

    private final StringConverter<T> stringConverter = new StringConverter<T>() {
        @Override
        public String toString(final T t) {
            return t.toStringField();
        }

        @Override
        public T fromString(String string) {
            throw new UnsupportedOperationException();
        }
    };

    private final Callback<ListView<T>, ListCell<T>> callback = new Callback<ListView<T>, ListCell<T>>() {
        @Override
        public ListCell<T> call(ListView<T> p) {
            return new ListCell<T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setText(item.toStringField());
                    }
                }
            };
        }
    };

    public StringConverter<T> getStringConverter() {
        return stringConverter;
    }

    public Callback<ListView<T>, ListCell<T>> getCallback() {
        return callback;
    }




}
