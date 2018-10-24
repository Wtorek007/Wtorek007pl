package projekt;

import projekt.core.TxHandler;

public class TestTxHandler implements TxHandler<TestCommand, Void> {

    private boolean handled;
    private boolean postTxCalled;
    private boolean preTxCalled;
    public boolean throwException;

    @Override
    public Void handle(TestCommand testCommand) {
        handled = true;
        if(throwException) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public void preTx(TestCommand command) {
        preTxCalled = true;
    }

    @Override
    public void postTx(TestCommand command) {
        postTxCalled = true;
    }

    public boolean isHandled() {
        return handled;
    }

    public boolean isPostTxCalled() {
        return postTxCalled;
    }

    public boolean isPreTxCalled() {
        return preTxCalled;
    }
}
