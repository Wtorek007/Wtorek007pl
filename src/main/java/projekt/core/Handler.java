package projekt.core;

public interface Handler<CommandT extends Command, ReturnT> {

    ReturnT handle(CommandT commandT);

}
