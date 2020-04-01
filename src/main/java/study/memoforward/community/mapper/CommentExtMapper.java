package study.memoforward.community.mapper;

import org.springframework.stereotype.Repository;
import study.memoforward.community.model.Comment;

@Repository
public interface CommentExtMapper {
    int incCommentCount(Comment record);
}

