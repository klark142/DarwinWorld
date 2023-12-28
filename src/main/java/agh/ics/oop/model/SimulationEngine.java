//package agh.ics.oop.model;
//
//import agh.ics.oop.model.WorldMap;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class SimulationEngine {
//    private List<WorldMap> simulations;
//    private List<Thread> threads;
//    private ExecutorService executorService;
//    public SimulationEngine(List<WorldMap> simulations){
//        this.simulations = simulations;
//        this.threads = new ArrayList<>();
//    }
////    public void runSync(){
////        for(WorldMap simulation : simulations){
////            for (int i = 0; i<15; i++)
////                simulation.move();
////        }
////    }
////
////    public void runAsync() throws InterruptedException {
////        List <Thread> threads = new ArrayList<>();
////        for (WorldMap simulation : simulations){
////            Thread thread = new Thread(simulation);
////            thread.start();
////            threads.add(thread);
////        }
////
////        for (Thread thread : threads) {
////            thread.join();
////        }
////    }
//    public void awaitSimulationsEnd() throws InterruptedException {
//        while (!allSimulationsFinished()) {
//            wait();
//        }
//        System.out.println("System zakończył działanie");
//    }
//
//
//    private boolean allSimulationsFinished() {
//        for (Thread thread : threads) {
//            if (thread.isAlive()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void runAsyncInThreadPool() {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        for (WorldMap simulation : simulations) {
//            executorService.submit(simulation);
//            for(int i = 0; i< 20; i++){
//                simulation.move();
//            }
//        }
//        shutdownExecutor(executorService);
//    }
//
//    private void shutdownExecutor(ExecutorService executorService) {
//        executorService.shutdown();
//        try {
//            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
//                executorService.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            executorService.shutdownNow();
//        }
//    }
//
//}
