package dad;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private Controller controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new Controller(primaryStage);

		Scene escena = new Scene(controller.getRoot(), 800, 500);

		primaryStage.setTitle("Enviar Email");
		primaryStage.getIcons().add(new Image(getClass().getResource("/icono.png").toExternalForm()));
		primaryStage.setScene(escena);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
