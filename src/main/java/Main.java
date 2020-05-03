import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Controller;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/view.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        //FFTSolver1D FFTSolver1D = new FFTSolver1D();
        //controller.setFFTSolver1D(FFTSolver1D);

        primaryStage.setTitle("Optic 2");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
