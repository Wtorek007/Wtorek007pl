package projekt.confirmation.commands;

import projekt.core.Command;

public class ConfirmDocumentOnBehalfCommand implements Command {

    public String documentNumber;
    public Long confirmerId;
    public Long targetEmployeeId;

}
