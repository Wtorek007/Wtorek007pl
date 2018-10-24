package projekt;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projekt.core.*;
import projekt.core.SecurityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CommandGatewayTest {

    @Mock
    private SecurityManager securityManager;

    @Mock
    private CommandLogger commandLogger;

    @Mock
    private TxManager txManager;

    @Mock
    private CommandGateway gateway;

    private TestHandler handler = new TestHandler();

    private TestTxHandler txHandler = new TestTxHandler();

    private TestCommand command = new TestCommand();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        gateway = new CommandGateway(commandLogger, securityManager, txManager);
    }

    @Test
    public void throwsErrorWhenNoRegisteredHandler() {
        assertThatThrownBy(() -> gateway.execute(command)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void handlesCommand() {
        registerAndExecute();

        assertThat(handler.isHandled()).isTrue();
    }

    @Test
    public void logsCommands() {
        registerAndExecute();

        verify(commandLogger, times(1)).executionStarted(command);
        verify(commandLogger, times(1)).executionFinished(command);
    }

    @Test
    public void verifiesSecurity() {
        registerAndExecute();

        verify(securityManager).checkSecurity(command, handler);
    }

    @Test
    public void doesNotOpenTransactionForNonTxHandlers() {
        registerAndExecute();

        verifyZeroInteractions(txManager);
    }

    @Test
    public void beginsAndCommitsTransaction() {
        registerAndExecute(txHandler);

        verify(txManager, times(1)).begin();
        verify(txManager, times(1)).commit();
        verify(txManager, times(0)).rollback();
    }


    @Test
    public void rollbacksTransactionInCaseOfErrors() {
        txHandler.throwException = true;
        assertThatThrownBy(() -> registerAndExecute(txHandler)).isInstanceOf(RuntimeException.class);

        verify(txManager, times(1)).begin();
        verify(txManager, times(0)).commit();
        verify(txManager, times(1)).rollback();
    }

    private void registerAndExecute() {
        registerAndExecute(handler);
    }

    private void registerAndExecute(Handler handler) {
        gateway.registerHandler(TestCommand.class, handler);
        gateway.execute(command);
    }

}
