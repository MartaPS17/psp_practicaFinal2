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
          Thread.sleep(2000);
          //consultar estat cinta i estacions
          int bateriesActuals = cinta.getCapacitatActual();
          int bateriesMaximes = cinta.getCapacitatMax();
          int estacionsLliures = estacions.getEstacionsLliures();

          // Imprimir per pantalla
          System.out.println("----------DASHBOARD---------\n" + "---------------------------");

          System.out.println("Cinta: " + bateriesActuals + "/" + bateriesMaximes);
          System.out.println("Estacions lliures: " + estacionsLliures);
          System.err.println("------------------------");
        //durant thread sleep...
      } catch (InterruptedException e) {
        System.out.println("Dashbaord interromput. Esta dormint vale? Espera.");
        Thread.currentThread().interrupt(); 
        break;
      }
      
        
    }}
  }



