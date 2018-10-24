package projekt.core;

public class CommandLogger {

    public void executionStarted(Command command) {
        System.out.println(String.format("execution of %s started", command.getClass()));
    }

    public void executionFinished(Command command) {
        System.out.println(String.format("execution of %s finished", command.getClass()));
    }

}