package net.richarddawkins.watchmaker.menu;

import net.richarddawkins.watchmaker.app.AppData;

public abstract class SimpleMenuBuilder implements MenuBuilder {


    protected AppData appData;
    public SimpleMenuBuilder() {
    }
    public SimpleMenuBuilder(AppData appData) {
        this.appData = appData;
    }

    public AppData getAppData() {
        return appData;
    }
    public void setAppData(AppData appData) {
        this.appData = appData;
    }
}
