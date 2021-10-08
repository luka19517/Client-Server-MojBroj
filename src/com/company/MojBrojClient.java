package com.company;

import com.sun.deploy.util.StringUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MojBrojClient extends Application{
    Socket client;
    BufferedReader in;
    BufferedWriter out;
    int goal;
    int []niz;

    @Override
    public void start(final Stage primaryStage){
        try {
            VBox root = new VBox();
            root.setSpacing(20);
            root.toBack();

            HBox goalBox = new HBox();
            goalBox.setSpacing(20);
            goalBox.setAlignment(Pos.CENTER);
            goalBox.toBack();

            HBox inputBox = new HBox();
            inputBox.setSpacing(10);
            inputBox.toBack();

            HBox buttonBox = new HBox();
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setSpacing(10);
            buttonBox.toBack();

            HBox resultBox = new HBox();
            resultBox.setSpacing(10);
            resultBox.setAlignment(Pos.CENTER);
            resultBox.toBack();


            root.getChildren().add(goalBox);
            root.getChildren().add(inputBox);
            root.getChildren().add(buttonBox);
            root.getChildren().add(resultBox);

            final TextField tf = new TextField();
            Label label = new Label("Target:");
            label.setLabelFor(tf);
            goalBox.getChildren().addAll(label,tf);

            final TextField tf0 = new TextField();
            final TextField tf1 = new TextField();
            final TextField tf2 = new TextField();
            final TextField tf3 = new TextField();
            final TextField tf4 = new TextField();
            final TextField tf5 = new TextField();
            inputBox.getChildren().addAll(tf0,tf1,tf2,tf3,tf4,tf5);

            final Button button = new Button("Resi");
            buttonBox.getChildren().add(button);

            final TextField tfResult = new TextField();
            Label labelResult = new Label("Result:");
            labelResult.setLabelFor(tfResult);
            tfResult.setPrefWidth(200);

            resultBox.getChildren().add(labelResult);
            resultBox.getChildren().add(tfResult);

            EventHandler<WindowEvent> eventHandler = new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        client = new Socket("192.168.1.250", MojBrojServer.PORT);
                        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            };

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int niz[]=new int[6];

                    if(!tf0.getText().isEmpty() && !tf1.getText().isEmpty() && !tf2.getText().isEmpty() && !tf3.getText().isEmpty() && !tf4.getText().isEmpty() && !tf5.getText().isEmpty() && !tf.getText().isEmpty()){
                        int goal=0;
                        try {
                            niz[0] = Integer.parseInt(tf0.getText().trim());
                            niz[1] = Integer.parseInt(tf1.getText().trim());
                            niz[2] = Integer.parseInt(tf2.getText().trim());
                            niz[3] = Integer.parseInt(tf3.getText().trim());
                            niz[4] = Integer.parseInt(tf4.getText().trim());
                            niz[5] = Integer.parseInt(tf5.getText().trim());

                            goal = Integer.parseInt(tf.getText().trim());
                        }catch(NumberFormatException e){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Kraj rada!",ButtonType.CANCEL);
                            alert.setTitle("POGRESAN FORMAT");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.CANCEL) {
                                primaryStage.close();
                                return ;
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(""+goal);
                        for (int i =0;i<6;i++){
                            sb.append(" "+niz[i]);
                        }
                        String message = sb.toString();
                        try {
                            out.write(message);
                            out.newLine();
                            out.flush();

                            String result = in.readLine();
                            tfResult.setText(result);
                            tf0.setEditable(false);
                            tf1.setEditable(false);
                            tf2.setEditable(false);
                            tf3.setEditable(false);
                            tf4.setEditable(false);
                            tf5.setEditable(false);
                            tf.setEditable(false);
                            tfResult.setEditable(false);
                            button.setVisible(false);

                        }catch (IOException e ){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Nastavi sa radom?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                            alert.showAndWait();
                            alert.setTitle("GRESKA SA KONEKCIJOM");
                            if (alert.getResult() == ButtonType.NO) {
                                tfResult.setText("Kraj rada na zahtev!");
                                primaryStage.close();
                                return ;
                            }
                            e.printStackTrace();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Nastavi sa radom?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                        alert.setTitle("NEPOPUNJENO POLJE");
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.NO) {
                            tfResult.setText("Kraj rada na zahtev!");
                            primaryStage.close();
                            return ;
                        }
                    }
                }
            });

            primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN,eventHandler);
            Scene scene = new Scene(root,400,200);
            scene.setFill(Color.BEIGE);
            primaryStage.setTitle("Moj Broj");
            primaryStage.setScene(scene);
            primaryStage.show();









        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isNum(String text) {
        try{
            Integer.parseInt(text);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch();
    }
}
