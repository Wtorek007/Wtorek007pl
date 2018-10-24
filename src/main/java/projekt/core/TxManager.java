package projekt.core;

public class TxManager {

    public void begin() {
        System.out.println("Transaction begun");
    }

    public void rollback() {
        System.out.println("Transaction rolled back");
    }

    public void commit() {
        System.out.println("Transaction committed");
    }

}