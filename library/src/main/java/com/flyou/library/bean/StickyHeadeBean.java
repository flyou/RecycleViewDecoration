package com.flyou.library.bean;

/**
 * Created by fzl on 2017/08/28.
 * VersionCode: 1
 * Desc:
 */

public class StickyHeadeBean {
    private String title;
    private boolean isFirst;
    private boolean isTeamFirst;
    private boolean isTeamLast;

    public StickyHeadeBean(String title, boolean isFirst, boolean isTeamFirst, boolean isTeamLast) {
        this.title = title;
        this.isFirst = isFirst;
        this.isTeamFirst = isTeamFirst;
        this.isTeamLast = isTeamLast;
    }

    public boolean isTeamLast() {
        return isTeamLast;
    }

    public void setTeamLast(boolean teamLast) {
        isTeamLast = teamLast;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isTeamFirst() {
        return isTeamFirst;
    }

    public void setTeamFirst(boolean teamFirst) {
        isTeamFirst = teamFirst;
    }
}
