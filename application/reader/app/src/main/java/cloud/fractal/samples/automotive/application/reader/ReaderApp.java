package cloud.fractal.samples.automotive.application.reader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReaderApp {
  private final static String TOPIC_FORMAT = "audi-%s-eh";

  private final static int NUM_THREADS = 1;

  public static void main(String... args) throws Exception {

    final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    for (int i = 0; i < NUM_THREADS; i++){
      var environment = System.getenv("ENVIRONMENT");
      executorService.execute(new ReaderThread(String.format(TOPIC_FORMAT, environment)));
    }
  }
}
