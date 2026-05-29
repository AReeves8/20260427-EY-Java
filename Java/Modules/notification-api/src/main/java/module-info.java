module com.skillstorm.notification_api {

    /**
     * requires will make sure this module brings in the listed package
     * transitive just auto-exports what it brings in through "required" to the module that requires this module
     */
    requires transitive com.skillstorm.notification_status;
    exports com.skillstorm.notification_api;

    // requires static enforces checks for the module only at compile time, not run time
    // (testing doesn't exist in this project, just wanted to show the syntax of requires static)
    requires static com.skillstorm.testing;
}
