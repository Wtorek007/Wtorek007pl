package projekt.core;

public class SecurityManager {

    public void checkSecurity(Command command, Handler handler) {
        System.out.println(String.format("checking securty for %s and %s", command.getClass(), handler.getClass()));
    }

}
