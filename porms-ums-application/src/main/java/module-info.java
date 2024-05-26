module prefect.user.management {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires org.slf4j;
    requires org.apache.logging.log4j.slf4j2.impl;
    requires jbcrypt;

    opens com.system.demo to javafx.fxml;
    opens com.system.demo.controllers.main to javafx.fxml;
    opens com.system.demo.controllers.dashboard to javafx.fxml;
    opens com.system.demo.controllers.modal to javafx.fxml;
    opens com.system.demo.controllers.search to javafx.fxml;
    opens com.system.demo.controllers to javafx.fxml;
    opens com.system.demo.appl.model.communityservice to javafx.fxml;
    opens com.system.demo.appl.model.offense to javafx.fxml;
    opens com.system.demo.appl.model.violation to javafx.fxml;
    opens com.system.demo.appl.model.user to javafx.fxml;

    exports com.system.demo.controllers;
    exports com.system.demo;
    exports com.system.demo.controllers.main;
    exports com.system.demo.controllers.dashboard;
    exports com.system.demo.controllers.modal;
    exports com.system.demo.controllers.search;
    exports com.system.demo.appl.model.communityservice;
    exports com.system.demo.appl.model.offense;
    exports com.system.demo.appl.model.violation;
    exports com.system.demo.appl.model.user;
}