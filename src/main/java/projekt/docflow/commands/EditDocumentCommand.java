package projekt.docflow.commands;

import projekt.core.Command;

import java.util.Optional;

public class EditDocumentCommand implements Command {

    public String documentNumber;
    public Long editorId;
    public Optional<String> title, content;

}