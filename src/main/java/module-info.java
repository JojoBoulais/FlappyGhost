module com.flappy.flappyghost {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.flappy.flappyghost to javafx.fxml;
    exports com.flappy.flappyghost;
}