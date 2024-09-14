package com.mycompany.mavenproject1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        final String validate = "[^0-9.]";
        
        //creating a GridPane 
        GridPane gridPane = new GridPane();   
        //setting size for the pane  
        gridPane.setMinSize(400, 200); 
        //setting the padding  
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        //setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);          
        //setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
       
        //creating labels, textfields and calculate button
        Label rateLabel = new Label("Annual Interest Rate: ");
        Label yearsLabel = new Label("Number of Years: ");
        Label loanLabel = new Label("Loan Amount: ");
        Label monthlyLabel = new Label("Monthly Payment: ");
        Label totalLabel = new Label("Total Payment: ");
        TextField rate = new TextField();
        TextField years = new TextField();
        TextField loan = new TextField();
        TextField monthlyPay = new TextField();
        TextField total = new TextField();
        Button btn = new Button("Calculate");
        
        //making monthly and total uneditable
        monthlyPay.setEditable(false);
        total.setEditable(false);
        
        //adding action to calculate button to perform calculations and display results
        btn.setOnAction(e->{
            //validating my textfields with a regex that removes nondigits except for '.'
            String rateValidated = rate.getText().replaceAll(validate, "");
            rate.setText(rateValidated);
            String yearsValidated = years.getText().replaceAll(validate, "");
            years.setText(yearsValidated);
            String loanValidated = loan.getText().replaceAll(validate, "");
            loan.setText(loanValidated);
            
            //if fields are blank or only contain '.' return. fails to function properly if there are multiple '.'
            //I do not know how to fix that without looping through the entire string
            if (rateValidated.isBlank() || yearsValidated.isBlank() || loanValidated.isBlank()
                    || rateValidated.equals(".") || yearsValidated.equals(".") || loanValidated.equals(".")){
                //setting calculated fields to blank if there is an issue
                monthlyPay.setText("");
                total.setText("");
                return;
            }
            
            int months = Integer.parseInt(yearsValidated) * 12;
            double monthlyRate = Double.parseDouble(rateValidated) / 1200; //converting annual rate to monthly
            double monthlyPayment = Double.parseDouble(loanValidated) * monthlyRate * Math.pow(1+monthlyRate, months) 
                    / (Math.pow(1+monthlyRate, months) - 1); //calculating monthly payments
            monthlyPayment = (double)Math.round(monthlyPayment*100)/100; //rounding the payment to cents
            
            monthlyPay.setText(String.valueOf(monthlyPayment));
            total.setText(String.valueOf((double)Math.round(monthlyPayment * months * 100) / 100)); 
            //I dont know why, but the total is off by 12 cents compared to the example. I assume its some rounding issue with my algorithm
        });
      
        //placing my controls in the container
        gridPane.add(rateLabel, 1, 0);
        gridPane.add(yearsLabel, 1, 1);
        gridPane.add(loanLabel, 1, 2);
        gridPane.add(monthlyLabel, 1, 3);
        gridPane.add(totalLabel, 1, 4);
        gridPane.add(rate, 2, 0);
        gridPane.add(years, 2, 1);
        gridPane.add(loan, 2, 2);
        gridPane.add(monthlyPay, 2, 3);
        gridPane.add(total, 2, 4);
        gridPane.add(btn, 2, 5);
        
        var scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}