package assembly;

public class SupervisorCentral {
  private boolean alertaSuperescalfament = true;

  public synchronized void comprovarAlerta(int idRobot) throws InterruptedException{
    while(alertaSuperescalfament){
      System.out.println("ALERTA: Superescalfament. Robot #" + idRobot + "cancela la activitat.");
      wait();
    }
  }

    public synchronized void activarAlerta(){
      this.alertaSuperescalfament = true;
      System.err.println("ALERTA ACTIVADA per Superescalfament en actiu.");
      }

    public synchronized void desactivarAlarma(){
      this.alertaSuperescalfament = false;
      System.err.println("ALERTA RESOLTA: Reprenent activitat...");
      notifyAll();

    }  

    }



