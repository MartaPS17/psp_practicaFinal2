package assembly;

public class RobotTask implements Runnable {
  private final int idRobot;
  private final Cinta cinta;
  private final EstacionsSoldadura estacions;
  private final TaulaRegistre tauler;
  private final SupervisorCentral supervisor;
  private volatile boolean executant = true;

  //constructor
  public RobotTask(int idRobot, Cinta cinta, EstacionsSoldadura estacions,TaulaRegistre tauler, SupervisorCentral supervisor){
    this.idRobot =idRobot;
    this.cinta = cinta;
    this.tauler = tauler;
    this.supervisor = supervisor;
    this.estacions = estacions;
  }

  public void aturarRobot(){
    this.executant = false;
  }

  //metode run
  @Override
  public void run(){
    System.out.println("Robot " + idRobot + "  iniciat i a la espera.");

    while (executant && !Thread.currentThread().isInterrupted()){
      try{
        supervisor.comprovarAlerta(idRobot);
      //agafa bateria
      Bateria bateria = cinta.extreureBateria(idRobot);
      //estacio soldadura
      int numEstacio = estacions.ocuparEstacio(idRobot); 

      try{
        Thread.sleep(500);

      } finally {
        estacions.alliberarEstacio(idRobot, idRobot);
      }
      //signa certificat
      tauler.signarCertificat(idRobot);

    } catch(InterruptedException e){
      System.err.println("Robot amb id: " + idRobot + "  ha estat interromput.");
      Thread.currentThread().interrupt();
      break;

    }
  }
}

}