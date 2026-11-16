package assembly;

public class Dashboard implements Runnable{
  private final Cinta cinta;
  private final EstacionsSoldadura estacions;
  private volatile boolean executant = true;

  public Dashboard(Cinta cinta, EstacionsSoldadura estacions){
    this.cinta = cinta;
    this.estacions = estacions;
  }

  public void aturar(){
    this.executant = false;

  }

  public void run(){
    System.out.println("Dashboard activat...");
    while (executant && !Thread.currentThread().isInterrupted()) { 
      try {
          Thread.sleep(200);
          //consultar estat
          int bateriesActuals = cinta.getCapacitatActual();
          int bateriesMaximes = cinta.getCapacitatMax();
          int estacionsLliures = estacions.getNumEstacionsLliures();
          
      } catch (Exception e) {
      }
      
        
    }}
  }



