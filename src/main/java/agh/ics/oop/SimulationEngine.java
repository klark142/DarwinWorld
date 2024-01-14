package agh.ics.oop;

import java.util.List;
import java.util.concurrent.*;

public class SimulationEngine {
    private List<Simulation> simulations;
    private CountDownLatch latch;
    private ExecutorService executor;
    private List<Thread> threads;
    private boolean isRunning = true;
    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
        this.latch = new CountDownLatch(simulations.size());
        this.executor = Executors.newFixedThreadPool(4);
    }

    public void runSync() {
        for (Simulation simulation : simulations) {
            simulation.run();
        }
    }

//    public void runAsync() {
//        for (Simulation simulation : simulations) {
//            Thread thread = new Thread(() -> {
//                simulation.run();
//                latch.countDown();
//            });
//            thread.start();
//        }
//    }
    public void runAsync() {
        for (Simulation simulation : simulations) {
            executor.submit(() -> {
                simulation.run();
                latch.countDown();
            });
        }
    }

    public void runAsyncInThreadPool() {
        this.executor = Executors.newFixedThreadPool(4);
        for (Simulation simulation : simulations) {
            executor.submit(simulation);
        }
    }

//    public void awaitSimulationsEnd() throws InterruptedException {
//        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
//            executor.shutdownNow();
//        }
//    }
    public void awaitSimulationsEnd() throws InterruptedException {
        latch.await(); // Wait for all simulations to complete
        executor.shutdown();
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }
}
