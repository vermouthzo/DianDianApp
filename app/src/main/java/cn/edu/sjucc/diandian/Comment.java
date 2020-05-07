package cn.edu.sjucc.diandian;


import java.util.Date;

public class Comment {
    /**
     * 评论作者
     */
    private String author;

    /**
     * 评论时间
     */
    private Date dt;

    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论点赞数
     */
    private int star;

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + author + '\'' +
                ", dt=" + dt +
                ", content='" + content + '\'' +
                ", star=" + star +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
