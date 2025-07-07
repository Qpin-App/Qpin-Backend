# open jdk 17 버전의 환경을 구성
FROM openjdk:17-jdk-slim

# 빌드 인수 정의
ARG PROFILES
ARG ENV

# JAR 파일 복사
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 실행 시점에 환경변수로 전달받은 값 적용 (기본값 지정 가능)
ENV SPRING_PROFILES_ACTIVE=${PROFILES}
ENV SERVER_ENV=${ENV}

# 컨테이너 시작 명령 설정
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -Dserver.env=$SERVER_ENV -jar app.jar"]