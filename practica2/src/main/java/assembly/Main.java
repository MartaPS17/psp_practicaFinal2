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
        int numRobots = 5;
        RobotTask[] robots = new RobotTask[numRobots];
        Thread[] tRobots = new Thread[numRobots];
        for (int i = 0; i < numRobots; i++){
            robots[i] = new RobotTask(i, cintaX, estacioY, taula, supervisor);

            tRobots[i] = new Thread(robots[i], "Robot " +(i + 1));

            tRobots[i].start();
        }

        //5.Productors
        int numAGV = 5;
        AGVTask[] agvs = new AGVTask[numAGV];
        Thread[] tAGVs = new Thread[numAGV];
        for (int i = 0; i < numAGV; i++){
            agvs[i] = new AGVTask(i + 1, cintaX);
            tAGVs[i] = new Thread(agvs[i], "AGV | " + (i + 1));
            tAGVs[i].start();
        }

        // Pausar fabrica i tancar totes es processos amb join()
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.err.println("Fàbrica pausada (10s)");
        }

        //enviar interrupció
        dash.aturar();
        filsDashboard.interrupt();

        for (int i = 0; i < numRobots; i++){
            robots[i].aturarRobot();
            tRobots[i].interrupt();
        }
        for (int i = 0; i < numAGV; i++){
            agvs[i].aturarAGV();
            tAGVs[i].interrupt();
        }

        //join()

        try {
            filsDashboard.join();
            for(int i = 0; i < numRobots; i++){
                tRobots[i].join();
            }
            for(int i = 0; i < numAGV; i++){
                tAGVs[i].join();
            }
            
        } catch (InterruptedException e) {
            System.err.println("ALERTA: Error durant el join per el tancament dels fils.");
        }

        
    }
}