package dad;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Controller implements Initializable {

	private Stage stage;
	private Model model;

	@FXML
	private HBox root;

	@FXML
	private TextArea areaText;

	@FXML
	private TextField asuntoText;

	@FXML
	private Button cerrarBtn;

	@FXML
	private CheckBox check;

	@FXML
	private PasswordField contraseniaText;

	@FXML
	private TextField emailText;

	@FXML
	private Button enviarBtn;

	@FXML
	private TextField puertoText;

	@FXML
	private TextField reemitenteText;

	@FXML
	private TextField servidorText;

	@FXML
	private Button vaciarBtn;

	public HBox getRoot() {
		return root;
	}

	public Controller(Stage stage) throws IOException {
		this.stage = stage;
		model = new Model();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {
		servidorText.textProperty().bindBidirectional(model.servidorProperty());
		reemitenteText.textProperty().bindBidirectional(model.remitenteProperty());
		emailText.textProperty().bindBidirectional(model.destinatarioProperty());
		asuntoText.textProperty().bindBidirectional(model.asuntoProperty());
		contraseniaText.textProperty().bindBidirectional(model.contraseniaProperty());
		areaText.textProperty().bindBidirectional(model.mensajeProperty());
		check.selectedProperty().bindBidirectional(model.sslProperty());
		Bindings.bindBidirectional(puertoText.textProperty(), model.puertoProperty(), new NumberStringConverter());
		cerrarBtn.setOnAction(e -> cerrar(e));
		vaciarBtn.setOnAction(e -> limpiar(e));
		enviarBtn.setOnAction(e -> enviar(e));
	}

	private void enviar(ActionEvent e) {
		Alert alerta = new Alert(null);
		try {
			Email email = new SimpleEmail();
			email.setHostName(model.getServidor());
			email.setSmtpPort(model.getPuerto());
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getContrasenia()));
			email.setSSLOnConnect(model.isSsl());
			email.setFrom(model.getRemitente());
			email.setSubject(model.getAsunto());
			email.setMsg(model.getMensaje());		
			email.addTo(model.getDestinatario());
			email.send();
			alerta.setTitle("Mensaje enviado");
			alerta.setAlertType(AlertType.INFORMATION);
			alerta.setHeaderText("Mensaje enviado con exito a '"+ model.getDestinatario()+ "'.");
			alerta.setContentText("");
			
		} catch (Exception error) {
			alerta.setTitle("Error");
			alerta.setAlertType(AlertType.ERROR);
			alerta.setHeaderText("No se pudo enviar el email");
			alerta.setContentText("Invalid message suplied");
		}
		alerta.showAndWait();
	}

	private void limpiar(ActionEvent e) {
		model.setAsunto(null);
		model.setContrasenia(null);
		model.setDestinatario(null);
		model.setMensaje(null);
		model.setPuerto(0);
		model.setRemitente(null);
		model.setServidor(null);
		model.setSsl(false);
	}

	private void cerrar(ActionEvent e) {
		stage.close();
	}
	

}
