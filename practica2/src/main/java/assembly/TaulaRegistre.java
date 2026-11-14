package assembly;
import java.util.concurrent.Semaphore;

public class TaulaRegistre {
  private Semaphore semaforCertificat = new Semaphore(1);

  public void signarCertificat(int idRobot) throws InterruptedException{
    semaforCertificat.acquire();

    try {
        System.out.println("Robot amb id " + idRobot + "està obtenguent es signat de certificat.");

        Thread.sleep(600);

        System.out.println("Robot " + idRobot + " ja te es certificat!");
    } finally {
      semaforCertificat.release();
    }
  }


}
