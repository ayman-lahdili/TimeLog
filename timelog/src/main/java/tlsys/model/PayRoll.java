package tlsys.model;

import java.time.LocalDateTime;

public class PayRoll implements PayRollInterface{

    TimeLogModel model;
    PayInfo payInfo;

    public PayRoll(TimeLogModel model, PayInfo payInfo) {
        this.model = model;
        this.payInfo = payInfo;
    }

    public PayInfo printPay(PayInfo payInfo) {
        LocalDateTime currentTime = LocalDateTime.now();

        System.out.println(currentTime);
        return payInfo;
    }

    public void netFromBrute(){
        //À implémenter dans le futur sous-système
    }

    public void deductionsReport(){
        //À implémenter dans le futur sous-système
    }

}
