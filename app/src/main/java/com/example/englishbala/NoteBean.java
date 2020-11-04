package com.example.englishbala;

public class NoteBean {
    private long id;
    private String content;
    private String time;
    private int tag;
    private String img;

    public NoteBean() {
    }
    public NoteBean(String content, String time, int tag,String img) {
        this.content = content;
        this.time = time;
        this.tag = tag;
        this.img=img;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getTag() {
        return tag;
    }

    public String getImg() {
        return img;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return content + "\n" + time.substring(5,16) + " "+ id;
    }

}
