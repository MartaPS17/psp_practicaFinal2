package assembly;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;



public class Cinta {
  private final int capacitatMax;
  private final Queue<Bateria>cinta;

  private final ReentrantLock lock;

  //files de espera indep
  private final Condition cintaNoPlena;
  private final Condition cintaNoBuida;

  public Cinta(int capacitatMax){
    this.capacitatMax = capacitatMax;
    this.cinta = new LinkedList<>();
    this.lock = new ReentrantLock();
    this.cintaNoPlena = lock.newCondition();
    this.cintaNoBuida = lock.newCondition();
  }

  //metode per robot productor posar bateria a cinta
  public void posarBateria(Bateria bateria, int idAGV) throws InterruptedException{
    lock.lock();
    try {
        while(cinta.size() == capacitatMax){
          System.out.println("Cinta plena. Productor AGV # " + idAGV + "espera a la fila.");
          cintaNoPlena.await();
        }
        //sino esta plean afegim bateria
        cinta.add(bateria);
        System.out.println("Productor AGV #" + idAGV + " ha afegit bateria #" + bateria.getBateriaId() + " | Capacitat actual: " + cinta.size() + "/" + capacitatMax); cintaNoBuida.signal(); 
      
    } finally {   
        lock.unlock(); 
        } 
    }
}
