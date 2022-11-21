package com.codestates.flyaway.global.exception;

import lombok.Getter;

public enum ExceptionCode {

    //MEMBER
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    MEMBER_NOT_AUTHORIZED(403, "로그인되지 않은 사용자입니다."),
    MEMBER_ALREADY_AUTHORIZED(400, "이미 로그인 상태입니다."),
    NOT_AUTHORIZED(403, "작성자가 일치하지 않습니다."),

    //TOKEN
    REFRESH_TOKEN_EXPIRED(401, "refresh token 만료, 강제 로그아웃"),
    REQUIRED_TOKEN_MISSING(401, "토큰이 존재하지 않습니다."),
    TOKEN_FROM_BLACKLIST(401, "blacklist에 등록된 토큰입니다."),
    REISSUED_ACCESS_TOKEN(401, "토큰을 재발급합니다."),
    PAYLOAD_NOT_VALID(401, "토큰의 payload가 유효하지 않습니다."),

    //USER INFO
    EMAIL_ALREADY_EXISTS(409, "이미 존재하는 이메일입니다."),
    EMAIL_NOT_EXISTS(400, "존재하지 않는 이메일입니다."),
    EMAIL_NOT_VALID(400, "이메일 형식이 올바르지 않습니다."),
    PASSWORD_NOT_VALID(400, "비밀번호 형식이 올바르지 않습니다."),
    PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),

    //FILE
    IMAGE_NOT_FOUND(404, "이미지가 존재하지 않습니다."),
    FILE_NOT_FOUND(404, "파일이 존재하지 않습니다."),
    FILE_DELETE_FAILED(404, "파일 삭제에 실패했습니다."),
    FILE_CANNOT_SAVE(404, "파일을 저장하지 못했습니다."),

    //BOARD
    ARTICLE_NOT_FOUND(404, "존재하지 않는 게시글입니다."),
    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    CATEGORY_NOT_FOUND(404, "존재하지 않는 카테고리 입니다.");

    @Getter
    private final int status;
    @Getter
    private final String message;

    ExceptionCode(final int code, final String message){
        this.status = code;
        this.message = message;
    }
}
