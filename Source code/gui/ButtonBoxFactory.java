package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ButtonBoxFactory {

    public HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox(10); // Khoảng cách giữa các phần tử là 10
        buttonBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < buttons.length; i++) {
            buttonBox.getChildren().add(buttons[i]);

            if (i < buttons.length - 1) { // Không thêm spacer sau phần tử cuối
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                buttonBox.getChildren().add(spacer);
            }
        }

        return buttonBox;
    }
}
