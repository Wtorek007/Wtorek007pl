package projekt;

import projekt.core.Handler;

public class TestHandler implements Handler<TestCommand, Void> {
    private boolean handled;

    @Override
    public Void handle(TestCommand testCommand) {
        handled = true;
        return null;
    }

    public boolean isHandled() {
        return handled;
    }

}
