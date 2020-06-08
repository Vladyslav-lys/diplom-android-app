package com.diplom.vlad.diplom;

public class Plomb {
    private String id;
    private String plomb_num;
    private String date_issue;
    private String id_whom_issue;
    private String date_set;
    private String id_enterprise;
    private String id_loco_seria;
    private String id_loco_num;
    private String id_place_set;
    private String date_off;
    private String id_adjuster;
    private String cause_off;
    private String id_getter;
    private String comment;
    private String date_stocked;

    public Plomb(String plomb_num, String id_whom_issue, String date_issue)
    {
        this.plomb_num = plomb_num;
        this.date_issue = date_issue;
        this.id_whom_issue = id_whom_issue;
    }

    public Plomb(String id, String plomb_num, String id_whom_issue, String date_issue, String date_set, String id_enterprise, String id_loco_seria,
                 String id_loco_num,String id_place_set, String date_off, String id_adjuster, String cause_off, String id_getter, String comment, String date_stocked)
    {
        this.id = id;
        this.plomb_num = plomb_num;
        this.date_issue = date_issue;
        this.id_whom_issue = id_whom_issue;
        this.date_set = date_set;
        this.id_enterprise = id_enterprise;
        this.id_loco_seria = id_loco_seria;
        this.id_loco_num = id_loco_num;
        this.id_place_set = id_place_set;
        this.date_off = date_off;
        this.id_adjuster = id_adjuster;
        this.cause_off = cause_off;
        this.id_getter = id_getter;
        this.comment = comment;
        this.date_stocked = date_stocked;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPlomb_num() {
        return plomb_num;
    }
    public void setPlomb_num(String plomb_num) {
        this.plomb_num = plomb_num;
    }

    public String getDate_issue() {
        return date_issue;
    }
    public void setDate_issue(String date_issue) {
        this.date_issue = date_issue;
    }

    public String getIdWhom_issue() {
        return id_whom_issue;
    }
    public void setIdWhom_issue(String id_whom_issue) {
        this.id_whom_issue = id_whom_issue;
    }

    public String getDate_set() {
        return date_set;
    }
    public void setDate_set(String date_set) {
        this.date_set = date_set;
    }

    public String getIdEnterprise() {
        return id_enterprise;
    }
    public void setIdEnterprise(String id_enterprise) {
        this.id_enterprise = id_enterprise;
    }

    public String getIdLoco_seria() {
        return id_loco_seria;
    }
    public void setIdLoco_seria(String id_loco_seria) {
        this.id_loco_seria = id_loco_seria;
    }

    public String getIdLoco_num() {
        return id_loco_num;
    }
    public void setIdLoco_num(String id_loco_num) {
        this.id_loco_num = id_loco_num;
    }

    public String getIdPlace_set() {
        return id_place_set;
    }
    public void setIdPlace_set(String id_place_set) {
        this.id_place_set = id_place_set;
    }

    public String getDate_off() {
        return date_off;
    }
    public void setDate_off(String date_off) {
        this.date_off = date_off;
    }

    public String getIdAdjuster() {
        return id_adjuster;
    }
    public void setIdAdjuster(String id_adjuster) {
        this.id_adjuster = id_adjuster;
    }

    public String getCause_off() {
        return cause_off;
    }
    public void setCause_off(String cause_off) {
        this.cause_off = cause_off;
    }

    public String getIdGetter() {
        return id_getter;
    }
    public void setIdGetter(String id_getter) {
        this.id_getter = id_getter;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate_stocked() {
        return date_stocked;
    }
    public void setDate_stocked(String date_stocked) {
        this.date_stocked = date_stocked;
    }
}
