package net.richarddawkins.watchmaker.component;

public interface WatchComponent extends WatchContainer {
    public Object getComponent();


    void setLayout(Object layout);

    void setBorder(Object border);

    void repaint();


    void setOpaque(boolean b); 

}
