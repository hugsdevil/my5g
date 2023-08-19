package org.career.my5g.domain.type;

public enum LevelType {
    SUPER_ADMIN(1),
    ENTERPRISE_ADMIN(2),
    SERVICE_USER(3),
    GUEST(4),
    NEW(-1),
    ;

    // code는 실제 t_user_level에 predefined 된 값
    // 따라서 NEW code는 존재하지 않음
    private int code;

    LevelType(int code) {
        this.code = code;
    }

    public static LevelType stringOf(String value) {
        switch (value) {
            case "S":
                return LevelType.SUPER_ADMIN;
            case "E":
                return LevelType.ENTERPRISE_ADMIN;
            case "U":
                return LevelType.SERVICE_USER;
            case "N":
                return LevelType.NEW;
            default:
                return LevelType.GUEST;
        }
    }

    public int getCode() {
        return code;
    }

    public String getCodeName() {
        return toString();
    }

    @Override
    public String toString() {
        switch (this) {
            case SUPER_ADMIN:
                return "S";
            case ENTERPRISE_ADMIN:
                return "E";
            case SERVICE_USER:
                return "U";
            case NEW:
                return "N";
            default:
                return "G";
        }
    }
}
