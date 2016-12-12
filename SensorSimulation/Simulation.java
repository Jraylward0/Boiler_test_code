/* Simulation
 * Sensor sample interval is 500 ms. Nominal sensor value random-walks up
 * and down from an initial value of 100; sensor "noise" = 5.
 *
 * Text output of readings and associated nominal values are output on
 * console and can be redirected to a log file for experimentation.
 */
import java.util.*;

public class Simulation {
  private DataDisplay display;
  private SensorSim sensor;
  private double sensorNom;       //nominal value -- random-walks -- mean
  private final double sensorErr; //fixed --  standard deviation

  public Simulation(double n, double e) { //constructor
    sensorNom = n;
    sensorErr = e;
    display = new DataDisplay();
    runSimulation();
  }

  public void runSimulation()  {
    Random rng = new Random();
    sensor = new SensorSim(sensorNom, sensorErr);
    sensor.start();
    while(true) {
      double rdg = sensor.getRdg();
      System.out.printf("%7.2f (%5.1f): %4.1f\n", rdg, sensorNom, rdg-sensorNom);
        //sensor:output(nominal):difference on console; can be redirected to log
      display.update(rdg, sensorNom); 

      if (rng.nextBoolean())  //nominal sensor output random-walks up & down
        sensorNom++;
      else
        sensorNom --;
      sensor.setNominal(sensorNom);

      try {  // 0.5-second sleep
        Thread.sleep(500);
      } catch (InterruptedException ix) {}
    }
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Using defaults initial nominal = 100.0, noise = 5.0");
      System.out.println("For other settings use java Simulation <nom> <noise>");
      new Simulation(100, 5);
    }
    else {
      new Simulation(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
    }
  } 

} //end class Simulation
