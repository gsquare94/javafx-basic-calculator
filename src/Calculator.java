
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class Calculator extends Application{
	private static final String[][] layout = {{"7","8","9","c"},{"4","5","6","Neg"},{"1","2","3","sin"},{"0","-","+","="}};
	private static boolean opSelected;
	private boolean outDisplayed;
	private String operator;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu helpMenu = new Menu("Help");
		menuBar.getMenus().add(helpMenu);
		MenuItem about = new MenuItem("About");
		helpMenu.getItems().add(about);
		about.setOnAction(e -> {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("A simple calculator");
			alert.setContentText("Created by "+"[YOUR NAME HERE]");
			alert.showAndWait();
		});
		TextField operand = new TextField();
		operand.setEditable(false);

		TextField output = new TextField();
		output.setEditable(false);

		VBox displayBox = new VBox();
		displayBox.getChildren().addAll(menuBar,operand,output);

		pane.setTop(displayBox);

		GridPane buttonPane = new GridPane();
		buttonPane.setHgap(5);
		buttonPane.setVgap(5);
		pane.setCenter(buttonPane);

		Button[][] buttons = new Button[4][4];
		String regex = "[0-9]+";
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				String button = layout[i][j];
				buttons[i][j] = new Button(button);
				if(button.matches(regex)) {
					buttons[i][j].setOnAction(e -> {
						if (outDisplayed) {
							output.setText(button);
							outDisplayed = false;
						} else {
							output.appendText(button);
						}

						opSelected = false;
					});
				}
				buttonPane.add(buttons[i][j], j, i);
			}
		}
		buttons[0][3].setOnAction(e -> {
			output.clear();
			operand.clear();
			opSelected = false;
		});
		buttons[3][1].setOnAction(e -> {
			if (operand.getText().isEmpty()) {
				operand.setText(output.getText().isEmpty() ? "0" : output.getText());
				operand.appendText("-" );
				operator = "-";
				outDisplayed = true;
				opSelected = true;
			} else if (opSelected) {
				operator = "-";
				int end = operand.getText().length();
				operand.replaceText(end - 1, end,  "-");
			} else {
				double first = Double.parseDouble(operand.getText().replaceAll("[^\\.0-9]", ""));
				double sec = Double.parseDouble(output.getText());
				operand.setText(format(first - sec) + " -");
				output.clear();
				operator = "-";
				outDisplayed = true;
				opSelected = true;
			}
		});
		buttons[3][2].setOnAction(e -> {
			if (operand.getText().isEmpty()) {
				operand.setText(output.getText().isEmpty() ? "0" : output.getText());
				operand.appendText(" +" );
				operator = "+";
				outDisplayed = true;
				opSelected = true;
			} else if (opSelected) {
				operator = "+";
				int end = operand.getText().length();
				operand.replaceText(end - 1, end,  "+");
			} else {
				double first = Double.parseDouble(operand.getText().replaceAll("[^\\.0-9]", ""));
				double sec = Double.parseDouble(output.getText());
				operand.setText(format(first + sec) + " +");
				output.clear();
				operator = "+";
				outDisplayed = true;
				opSelected = true;
			}
		});
		buttons[1][3].setOnAction(e -> {
			if(!output.getText().isEmpty()) {
				double num = -Double.parseDouble(output.getText());
				output.setText(format(num));
			}
		});
		buttons[2][3].setOnAction(e -> {
			if(!output.getText().isEmpty()) {
				double num = Double.parseDouble(output.getText());
				operand.setText("sin("+format(num)+")");
				num = Math.sin(num);
				output.setText(format(num));
				outDisplayed = true;
			}
		});
		buttons[3][3].setOnAction(e -> {
			if (!operand.getText().isEmpty()) {
				double first = Double.parseDouble(operand.getText().replaceAll("[^\\.0-9]", ""));
				double sec = Double.parseDouble(output.getText());
				if(operand.getText().startsWith("-"))first = -first;
				if("+".equals(operator))
					output.setText(format(first+sec));
				else if("-".equals(operator))
					output.setText(format(first-sec));
				outDisplayed = true;
				opSelected = false;
				operand.clear();
			}
		});
		buttons[3][3].setDefaultButton(true);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.setTitle("Calculator");
		stage.show();		
	}
	public static String format(double num)
	{
	    if(num == (long) num)
	        return String.format("%d",(long)num);
	    else
	        return String.format("%.2f",num);
	}
	public static void main(String[] args) {
		Application.launch(args);

	}

}
