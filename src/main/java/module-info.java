module br.edu.ifba.saj.fwads {
    requires javafx.controls;
    requires javafx.fxml;    
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens br.edu.ifba.saj.fwads.controller to javafx.fxml;    
    opens br.edu.ifba.saj.fwads.model to javafx.base, javafx.fxml, com.fasterxml.jackson.databind;   
    
    exports br.edu.ifba.saj.fwads;
    exports br.edu.ifba.saj.fwads.model;
}
