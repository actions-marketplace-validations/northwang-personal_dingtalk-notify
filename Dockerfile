FROM openjdk:17

COPY ./entrypoint.sh /entrypoint.sh
COPY ./target/dingtalk-notify-1.0-jar-with-dependencies.jar /dingtalk-notify.jar

ENTRYPOINT ["/entrypoint.sh"]