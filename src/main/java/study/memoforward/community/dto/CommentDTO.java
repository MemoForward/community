package study.memoforward.community.dto;

import study.memoforward.community.model.User;

public class CommentDTO {
     private Long parentId;
     private String content;
     private Integer type;
     private Long gmtCreate;
     private Long gmtModified;
     private Long likeCount;
     private Long commentCount;
     private Long id;
     private Long commentator;
     private User user;

     public Long getParentId() {
          return parentId;
     }

     public void setParentId(Long parentId) {
          this.parentId = parentId;
     }

     public String getContent() {
          return content;
     }

     public void setContent(String content) {
          this.content = content;
     }

     public Integer getType() {
          return type;
     }

     public void setType(Integer type) {
          this.type = type;
     }

     public Long getGmtCreate() {
          return gmtCreate;
     }

     public void setGmtCreate(Long gmtCreate) {
          this.gmtCreate = gmtCreate;
     }

     public Long getGmtModified() {
          return gmtModified;
     }

     public void setGmtModified(Long gmtModified) {
          this.gmtModified = gmtModified;
     }

     public Long getLikeCount() {
          return likeCount;
     }

     public void setLikeCount(Long likeCount) {
          this.likeCount = likeCount;
     }

     public Long getCommentCount() {
          return commentCount;
     }

     public void setCommentCount(Long commentCount) {
          this.commentCount = commentCount;
     }

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public Long getCommentator() {
          return commentator;
     }

     public void setCommentator(Long commentator) {
          this.commentator = commentator;
     }

     public User getUser() {
          return user;
     }

     public void setUser(User user) {
          this.user = user;
     }

     @Override
     public String toString() {
          return "CommentDTO{" +
                  "parentId=" + parentId +
                  ", content='" + content + '\'' +
                  ", type=" + type +
                  ", gmtCreate=" + gmtCreate +
                  ", gmtModified=" + gmtModified +
                  ", likeCount=" + likeCount +
                  ", commentCount=" + commentCount +
                  ", id=" + id +
                  ", commentator=" + commentator +
                  ", user=" + user +
                  '}';
     }
}
