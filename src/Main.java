import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread currentThread = Thread.currentThread();
                    int sum = 0;
                    Random random = new Random();
                    List<Integer> numbers = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        numbers.add(random.nextInt(100));
                    }
                    System.out.println(currentThread.getName() + " generated numbers: " + numbers);
                    int sleepTime = random.nextInt(10) + 1;
                    System.out.println(currentThread.getName() + " is going to sleep for " + sleepTime + " seconds");
                    TimeUnit.SECONDS.sleep(sleepTime);
                    for (int number : numbers) {
                        sum += number;
                    }
                    return String.valueOf(sum);
                }
            };
            futures.add(executorService.submit(task));
        }
        executorService.shutdown();
        for (int i = 0; i < futures.size(); i++) {
            Future<String> future = futures.get(i);
            try {
                String result = future.get();
                System.out.println("Task " + i + " result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}