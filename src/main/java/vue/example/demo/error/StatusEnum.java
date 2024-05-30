package vue.example.demo.error;

public enum StatusEnum { //에러코드 모음집
    OK(200, "OK"), //200. 정상
    BAD_REQUEST(400, "REQUEST ERROR"), //400. 리퀘스트 에러. 웬만한 에러가 모두 여기에 속함
    NOT_PERMISSION(401, "NOT PERMISSION"), //401.권한 없음. 권한이 없는데 권한이 필요한 페이지에 접속 시 방생
    NOT_FOUND(404, "PAGE NOT FOUND"), //404. 페이지 없음.
    INTER_SERVER_ERROR(500, "INTER SERVER ERROR"),; //500. 백엔드 서버 에러.

    int statusCode;
    String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
