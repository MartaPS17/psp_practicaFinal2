package assembly;

public class Bateria {
  private static int contadorBateries = 0;
  private final int id;

  public Bateria(){
    this.id = ++contadorBateries;
  }
  public int getBateriaId(){
    return this.id;
  }
}
