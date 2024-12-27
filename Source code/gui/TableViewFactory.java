package gui;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class TableViewFactory<T> {

    public TableView<T> createTableView(List<TableColumn<T, String>> columns, ObservableList<T> items) {
        TableView<T> tableView = new TableView<>();
        tableView.getColumns().addAll(columns);

        // Gán tỷ lệ chiều rộng cho các cột
        columns.forEach(column -> {
            Double widthPercentage = (Double) column.getUserData();
            if (widthPercentage != null) {
                column.prefWidthProperty().bind(tableView.widthProperty().multiply(widthPercentage));
            }
        });

        tableView.setItems(items);
        return tableView;
    }
}
