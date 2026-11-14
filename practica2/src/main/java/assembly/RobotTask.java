package assembly;
import java.io.InterruptedIOException;
import assembly.EstacionsSoldadura;

public class RobotTask implements Runnable {
  private final int idRobot;
  private final CintaBateries cinta;
  private final EstacionsSoldadura estacions;
  private final TaulaRegistre tauler;
  private final SupervisorCentral supervisor;
  private volatile boolean executant;

  //constructor
  public RobotTask(int idRobot, CintaBateries cinta, TaulaRegistre tauler, SupervisorCentral supervisor){
    this.idRobot =idRobot;
    this.cinta = cinta;
    this.tauler = tauler;
    this.supervisor = supervisor;
  }

  public void aturarRobot(){
    this.executant = false;
  }

  //metode run
  @Override
  public void run(){
    System.out.println("Robot " + idRobot + "iniciat i a la espera.");
    while (executant && !Thread.currentThread().isInterrupted()){
      //agafra bateria
      Bateria bateria = cinta.extreureBateria();
      //estacio soldadura
      int numEstacio = estacions.ocuparEstacio(idRobot);
      Thread.sleep(500); //simular temps 
      estacions.alliberarEstacio(idRobot, numEstacio);
      //signar certificat
      tauler.signarCertificat();

    } catch(InterruptedException e){
      System.err.println("Robot amb id: " + idRobot + " ha estat interromput.");
      Thread.currentThread().interrupt();
      break;

    }
  }

}
