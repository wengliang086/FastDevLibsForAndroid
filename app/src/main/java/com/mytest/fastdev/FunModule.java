package com.mytest.fastdev;

/**
 * Created by Administrator on 2017/6/8.
 */

public class FunModule {

    private String title;
    private String description;
    private Class activityClass;

    public FunModule(String title, Class activityClass) {
        this.title = title;
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }
}
