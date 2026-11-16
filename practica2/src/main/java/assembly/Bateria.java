package assembly;
import java.util.concurrent.atomic.AtomicInteger;

public class Bateria {
  private static final AtomicInteger contadorBateries = new AtomicInteger(0);
  private final int id;

  public Bateria(){
    this.id = contadorBateries.incrementAndGet();
  }
  public int getBateriaId(){
    return this.id;
  }
}
