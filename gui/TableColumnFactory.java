package gui;

import javafx.scene.control.TableColumn;

import java.util.function.Function;

public class TableColumnFactory<T> {

    public TableColumn<T, String> createColumn(String title, double widthPercentage, Function<T, String> valueProvider) {
        TableColumn<T, String> column = new TableColumn<>(title);
        column.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(valueProvider.apply(data.getValue()))
        );
        column.setUserData(widthPercentage); // Lưu tỷ lệ để tính kích thước sau
        return column;
    }
}
