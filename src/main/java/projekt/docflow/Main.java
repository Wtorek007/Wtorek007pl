package projekt.docflow;

import projekt.confirmation.commands.ConfirmDocumentCommand;
import projekt.confirmation.commands.ConfirmDocumentOnBehalfCommand;
import projekt.confirmation.handlers.ConfirmDocumentHandler;
import projekt.core.CommandGateway;
import projekt.core.CommandLogger;
import projekt.core.SecurityManager;
import projekt.core.TxManager;
import projekt.docflow.commands.CreateDocumentCommand;
import projekt.docflow.handlers.CreateDocumentHandler;

public class Main {

    public static void main(String[] args) {
        CommandGateway commandGateway = new CommandGateway(new CommandLogger(), new SecurityManager(), new TxManager());
        commandGateway.registerHandler(CreateDocumentCommand.class, new CreateDocumentHandler());
        ConfirmDocumentHandler confirmDocumentHandler = new ConfirmDocumentHandler();
        commandGateway.registerHandler(ConfirmDocumentCommand.class, confirmDocumentHandler::confirm);
        commandGateway.registerHandler(ConfirmDocumentOnBehalfCommand.class, confirmDocumentHandler::confirmOnBehalf);
    }

}
