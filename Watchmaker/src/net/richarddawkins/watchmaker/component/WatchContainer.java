package net.richarddawkins.watchmaker.component;

public interface WatchContainer {

    void add(Object newComponent);

    void add(Object newComponent, Object constraints);
    void removeAll();
}
