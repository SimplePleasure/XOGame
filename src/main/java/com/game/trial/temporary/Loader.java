package com.game.trial.temporary;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Loader {

    public static void main(String[] args) throws Exception {

        ConsumeUsers<String> c = new ConsumeUsers<>();
        ExecutorService ex = Executors.newScheduledThreadPool(2);

        Callable<Integer> produce = () -> {
            int count = 0;
            for (int i = 0; i<10; i++) {
                boolean res = c.addEl("test");
                if (res) {
                    count++;
                    System.out.println("Produce: counting");
                }
                Thread.sleep(5000);
            }
            return count;
        };

        Callable<List<String>> consume = () -> {
            List<String> test = new ArrayList<>();
            for (int i = 0; i<10; i++) {
                System.out.println("\t\t\tConcume: before blocking method");
                test.add(c.getEl());
                System.out.println("\t\t\tConsume: getEl after blocking");
                Thread.sleep(1000);
            }
            return test;
        };

        System.err.println("Start execution");
        Future<Integer> prod = ex.submit(produce);
        Future<List<String>> cons = ex.submit(consume);


        boolean d= ex.awaitTermination(10, TimeUnit.SECONDS);
        ex.shutdown();
        System.err.println(d);



    }
}
