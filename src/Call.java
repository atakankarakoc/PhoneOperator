import java.util.concurrent.CountDownLatch;

public class Call extends Thread{
    private String caller;
    private String persontocall;
    private Operator operator;
    private CountDownLatch latch;

    public Call(Operator operator, String caller, String persontocall, CountDownLatch latch) {
        this.caller = caller;
        this.persontocall = persontocall;
        this.operator = operator;
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (operator){
            operator.setStatus(false);
            System.out.println(Call.currentThread().getName()+" The Phone call started between "
                    +caller+ " and "+ persontocall);
            try {
                Thread.sleep(2000); // Calling for 2 seconds

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Call.currentThread().getName()+" The phone call between "+ caller+
                    " and "+ persontocall+" ended");
            latch.countDown();
        }
        operator.setStatus(true);
    }
}

