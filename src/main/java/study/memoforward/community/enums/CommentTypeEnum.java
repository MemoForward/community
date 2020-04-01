package study.memoforward.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private final int type;

    CommentTypeEnum(int type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum typeEnum : CommentTypeEnum.values()) {
            if(typeEnum.getType() == type) return true;
        }
        return false;
    }

    public int getType() {
        return type;
    }
}
