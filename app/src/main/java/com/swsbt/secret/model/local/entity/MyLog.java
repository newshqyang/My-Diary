package com.swsbt.secret.model.local.entity;

public class MyLog {
    private String _id;
    private String _do_thing;
    private String _dateline;

    public String get_id(){
        return _id;
    }

    public String get_do_thing(){
        return _do_thing;
    }

    public String get_dateline(){
        return _dateline;
    }

    public MyLog(String _id, String _do_thing, String _dateline){
        super();
        this._id = _id;
        this._do_thing = _do_thing;
        this._dateline = _dateline;
    }
}
