package assembly;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //1.Instanciar recursos compartits
        Cinta cintaX = new Cinta(8);
        EstacionsSoldadura estacioY = new EstacionsSoldadura(3);
        TaulaRegistre taula = new TaulaRegistre();
        SupervisorCentral supervisor = new SupervisorCentral();

        //2.Instanciar productor i consumidor
        List<Thread> llistaFils = new ArrayList<>();
        List<Thread> llistaRobots = new ArrayList<>();
        List<Thread> llistaTasquesAGV = new ArrayList<>();

        //3.Dashboard
        Dashboard dash = new Dashboard(cintaX, estacioY);
        Thread filsDashboard = new Thread(dash, "Dashboard");
        llistaFils.add(filsDashboard);

        //4. Consumidors
        RobotTask[] robots = new RobotTask[10];
        Thread[] tRobots = new Thread[10];
        for (int i = 0; i <=4; i++){
            robots[i] = new RobotTask(i, cintaX, estacioY, taula, supervisor);
            tRobots[i] = new Thread(robots[i]);
        }

    
    }
}