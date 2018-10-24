package projekt.core;

public class Profiler {

    private final Command command;
    private final Handler handler;

    private long ts;

    public Profiler(Command command, Handler handler) {
        this.command = command;
        this.handler = handler;
        this.ts = System.currentTimeMillis();
    }

    public void finished() {
        long te = System.currentTimeMillis();
        long t = te - ts;
        System.out.println(String.format("execution of %s by %s took %d", command.getClass(), handler.getClass(), t));
    }

}