package blog.ml3426.easyCal.gui;

import com.google.common.primitives.Ints;

import blog.ml3426.easyCal.analysis.Add;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EasyCalApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final TextArea input = new TextArea();
        final Text output = new Text();
        final ComboBox<String> algorithm = new ComboBox<>();
        primaryStage.setTitle("EasyCal");
        final Button calButton = new Button();
        calButton.setText("Calculate");
        calButton.setOnAction(event -> 
                output.setText(analyze(input.getText(), algorithm.getValue())));

        final VBox vBox = new VBox();
        vBox.setPadding(new Insets(3));
        vBox.setSpacing(3);
        final Text title = new Text("Choose an algorithm: ");
        algorithm.getItems().add("Add");

        final ObservableList<Node> childNode = vBox.getChildren();
        childNode.add(title);
        childNode.add(algorithm);
        childNode.add(calButton);

        final BorderPane pane = new BorderPane();
        pane.setRight(vBox);
        pane.setCenter(input);
        pane.setBottom(output);
        primaryStage.setScene(new Scene(pane, 300, 250));
        primaryStage.show();
    }

    private String analyze(final String input, final String algorithm) {
        final Add add = new Add();
        final String[] splitNum = input.split("\n");
        if (splitNum.length != 2) {
            return "ERROR! PARAM NUM INVALID!";
        }
        final Integer firstNum = Ints.tryParse(splitNum[0]);
        final Integer secondNum = Ints.tryParse(splitNum[1]);
        if (firstNum == null || secondNum == null) {
            return "ERROR! PARAM INVALID!";
        }
        return Integer.toString(add.add(firstNum, secondNum));
    }
}
