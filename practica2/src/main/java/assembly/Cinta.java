package assembly;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Cinta {
  private final int capacitatMax;
  private final Queue<Bateria> cinta;

  private final ReentrantLock lock;

  // files de espera indep
  private final Condition cintaNoPlena;
  private final Condition cintaNoBuida;

  public Cinta(int capacitatMax) {
    this.capacitatMax = capacitatMax;
    this.cinta = new LinkedList<>();
    this.lock = new ReentrantLock();
    this.cintaNoPlena = lock.newCondition();
    this.cintaNoBuida = lock.newCondition();
  }

  public int getCapacitatActual(){
    lock.lock();
    try {
      return cinta.size();
    } finally{
      lock.unlock();
    }
  }

  public int getCapacitatMax(){
    return capacitatMax;
  }

  // metode per robot productor posar bateria a cinta
  public void posarBateria(Bateria bateria, int idAGV) throws InterruptedException {
    lock.lock();
    try {
      while (cinta.size() == capacitatMax) {
        System.out.println("Cinta plena. Productor AGV # " + idAGV + "espera a la fila.");
        cintaNoPlena.await();
      }
      // sino esta plean afegim bateria
      cinta.add(bateria);
      System.out.println("Productor AGV #" + idAGV + " ha afegit bateria #" + bateria.getBateriaId()
          + " | Capacitat actual: " + cinta.size() + "/" + capacitatMax);
      cintaNoBuida.signal();

    } finally {
      lock.unlock();
    }
  }

  public Bateria extreureBateria(int idRobot) throws InterruptedException {
    lock.lock();
    try {
      while (cinta.isEmpty()) {
        System.out.println("ALERTA: CINTA BUIDA.Robot consumidor # " + idRobot + " no ha pogut extreure una bateria.");
        cintaNoBuida.await();
      }
      Bateria bateria = cinta.poll(); 

      System.out.println("Consumidor robot # " + idRobot + "ha agafat la bateria # " + bateria.getBateriaId()
          + " | Capacitat actual: " + cinta.size() + "/" + capacitatMax);
      // avisar a productor que hem tret una
      cintaNoPlena.signal();
      return bateria;
    } finally {
      lock.unlock();
    }
  }
}
