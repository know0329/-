# Ubuntu 20.04 기반 이미지를 사용합니다.
FROM ubuntu:20.04

# 패키지 관리자 업데이트 및 Python 및 Java 11 설치
ARG DEBIAN_FRONTEND=noninteractive
RUN sed -i 's/archive.ubuntu.com/mirror.kakao.com/g' /etc/apt/sources.list && \
    apt-get update && \
    apt-get install -y python3 python3-pip openjdk-11-jdk

RUN pip install --upgrade pip

COPY ./requirements.txt .
RUN pip install -r requirements.txt

COPY ./giggyanalysis /app

WORKDIR /app

COPY ./entrypoint.sh /
ENTRYPOINT [ "sh", "/entrypoint.sh" ]

