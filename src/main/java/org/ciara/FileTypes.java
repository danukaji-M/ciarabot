package org.ciara;

public enum FileTypes {
    AVI(".avi"),
    MP4(".mp4"),
    MKV(".mkv"),
    MOV(".mov"),
    WMV(".wmv"),
    FLV(".flv"),
    RM(".rm"),
    SWF(".swf"),
    MPEG(".mpeg"),
    WEBM(".webm"),
    OGG(".ogg");

    private final String typename;
    FileTypes(String s) {
        this.typename = s;
    }

    public String getTypename() {
        return typename;
    }
}