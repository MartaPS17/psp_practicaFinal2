package assembly;

public class AGVTask implements Runnable {
  private final int idAGV;
  private final Cinta cinta;
  private volatile boolean executant = true;

  public AGVTask (int idAGV, Cinta cinta){
    this.idAGV = idAGV;
    this.cinta = cinta;
  }

  public void executant(){
    this.executant = false;
  }

  @Override
  public void run(){
    System.out.println("Productor AGV #" + idAGV + " activat.");
    while (!executant && !Thread.currentThread().isInterrupted()) {
      try {
          Bateria novaBateria = new Bateria();
          cinta.posarBateria(novaBateria, idAGV);
          Thread.sleep(1200);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      } 
    }
    System.out.println("AGV #" + idAGV + "ha estat aturat.");

  }

}
