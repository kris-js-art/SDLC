package ExcuseGenerator {
    part def ExcuseModel;
    part def MainView;
    part def InputDialog;
    part def ExcuseController;

    part application {
        part model : ExcuseModel;
        part view : MainView;
        part dialog : InputDialog;
        part controller : ExcuseController;

        connect view to controller;
        connect controller to model;
        connect model to view;
        connect controller to dialog;
    }
}
