package assembly;

import java.util.concurrent.Semaphore;

public class EstacionsSoldadura {
  private final int numEstacions;
  private final Semaphore semafor;
  private final boolean[] estacionsOcupades;

  public EstacionsSoldadura(int numEstacions){
    this.numEstacions = numEstacions;
    this.semafor = new Semaphore(numEstacions);
    this.estacionsOcupades = new boolean[numEstacions];
  }

  //1-control, si no hi ha places lliures - es bloquetja
  public int ocuparEstacio(int idRobot) throws InterruptedException{
    semafor.acquire();
    int estacioAssignada = -1;

    //evitar que dos robots agafin sa mateixa posició fisica quan obtenen es permisos
    synchronized (this) {
      for (int i = 0; i < numEstacions; i++){
        if(!estacionsOcupades[i]){
          estacionsOcupades[i] = true;
          estacioAssignada = i;
          break;
        }
      }  
    }
    System.err.println("Robot amb id " + idRobot + " ha ocupat sa estació # " + estacioAssignada);

  }


}
