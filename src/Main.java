import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Lg.fxml"));
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("img/book-icon.png")));
        stage.show();
        stage.setTitle("Login");
        stage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
