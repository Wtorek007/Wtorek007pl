package projekt.core;

import java.util.HashMap;
import java.util.Map;

public class CommandGateway {

    public Map<Class<? extends Command>, Handler> handlersMap = new HashMap<>();

    private CommandLogger commandLogger;
    private SecurityManager securityManager;
    private TxManager txManager;

    public CommandGateway(CommandLogger commandLogger, SecurityManager securityManager,
                          TxManager txManager) {
        this.commandLogger = commandLogger;
        this.securityManager = securityManager;
        this.txManager = txManager;
    }

    public <ReturnT> ReturnT execute(Command command) {
        Handler handler = handlerFor(command);
        securityManager.checkSecurity(command, handler);
        Profiler profiler = new Profiler(command, handler);
        commandLogger.executionStarted(command);
        ReturnT returnValue;
        if (handler instanceof TxHandler) {
            returnValue = executeInTx(command, (TxHandler) handler);
        } else {
            returnValue = (ReturnT) handler.handle(command);
        }
        commandLogger.executionFinished(command);
        profiler.finished();
        return returnValue;
    }

    private <ReturnT> ReturnT executeInTx(Command command, TxHandler handler) {
        handler.preTx(command);
        ReturnT value;
        try {
            txManager.begin();
            value = (ReturnT) handler.handle(command);
            txManager.commit();
        } catch (RuntimeException re) {
            txManager.rollback();
            throw re;
        }
        handler.postTx(command);
        return value;
    }

    public <CommandT extends Command> void registerHandler(Class<CommandT> commandClass, Handler<CommandT, ?> handler) {
        handlersMap.put(commandClass, handler);
    }

    private Handler handlerFor(Command command) {
        if (!handlersMap.containsKey(command.getClass())) {
            throw new IllegalArgumentException(String.format("No handler found for %s", command.getClass()));
        }
        return handlersMap.get(command.getClass());
    }

}
