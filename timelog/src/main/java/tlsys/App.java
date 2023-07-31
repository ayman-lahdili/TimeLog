package tlsys;

import tlsys.controller.TimeLogController;
import tlsys.model.TimeLogModel;
import tlsys.view.TimeLogView;

public class App {
    public static void main(String[] args) {
        TimeLogModel model = new TimeLogModel();
        TimeLogView view = new TimeLogView();
        TimeLogController controller = new TimeLogController(model, view);

        controller.run();
    }

}
