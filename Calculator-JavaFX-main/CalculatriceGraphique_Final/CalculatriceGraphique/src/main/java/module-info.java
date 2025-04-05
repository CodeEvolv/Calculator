module com.example.calculatricegraphique {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;
    requires java.naming;
    requires MathParser.org.mXparser;


    opens com.example.calculatricegraphique to javafx.fxml;
    exports com.example.calculatricegraphique;
}
