package com.example.sidhant.datavault.POJOs;

/**
 * Created by Sidhant on 11/5/2017.
 */

public class Note {
    String noteBody;
    String mUid;
    public Note() {
    }

    public Note(String noteBody) {
        this.noteBody = noteBody;
    }

    public Note(String noteBody, String Uid) {
        this.noteBody = noteBody;
        mUid=Uid;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }
}
