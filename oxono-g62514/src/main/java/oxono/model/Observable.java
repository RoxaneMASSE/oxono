package oxono.model;

public interface Observable {
    void register(Observer o);
    void unregister(Observer o);
}
