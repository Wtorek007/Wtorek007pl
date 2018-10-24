package projekt.docflow.handlers;

import projekt.core.Handler;
import projekt.docflow.commands.PublishDocumentCommand;

public class PublishDocumentHandler implements Handler<PublishDocumentCommand, Void> {
    @Override
    public Void handle(PublishDocumentCommand publishDocumentCommand) {
        return null;
    }
}
