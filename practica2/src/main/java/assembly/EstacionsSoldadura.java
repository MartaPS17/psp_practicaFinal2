package assembly;

import java.util.concurrent.Semaphore;

public class EstacionsSoldadura {
  private final int numEstacio;
  private final Semaphore semafor;
  private final boolean[] estacionsOcupades;

  public EstacionsSoldadura(int numEstacio) {
    this.numEstacio = numEstacio;
    this.semafor = new Semaphore(numEstacio);
    this.estacionsOcupades = new boolean[numEstacio];
  }

  // 1-control, si no hi ha places lliures - es bloquetja
  public int ocuparEstacio(int idRobot) throws InterruptedException {
    semafor.acquire();
    int estacioAssignada = -1;

    // evitar que més de un robot agafin sa mateixa posició fisica quan obtenen es
    // permisos
    synchronized (this) {
      for (int i = 0; i < numEstacio; i++) {
        if (!estacionsOcupades[i]) {
          estacionsOcupades[i] = true;
          estacioAssignada = i;

          System.out.println("Estat de les estacions: "
        + java.util.Arrays.toString(estacionsOcupades));
          break;
        }
      }
    }
    System.out.println("Robot amb id " + "" + idRobot + " ha ocupat sa estació # " + estacioAssignada);
    return estacioAssignada;
  }

  public void alliberarEstacio(int idRobot, int numEstacio) {
    synchronized (this) {
      estacionsOcupades[numEstacio] = false;
    }

    System.out.println("Robot amb id " + idRobot + "ha sortit de la estació " + numEstacio);

    semafor.release();
  }

  public int getEstacionsLliures() {
    synchronized (this) {
      int lliures = 0;
      for (boolean ocupada : estacionsOcupades) {
        if (!ocupada) {
          lliures++;
        }
      }
      return lliures;
    }
  }

}
