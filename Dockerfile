FROM java:8-jre-alpine
ADD target/misrobot-workflow.jar ./
RUN apk add --no-cache bash
#RUN echo $(date) > /image_built_at
ENTRYPOINT ["java","-jar","/misrobot-workflow.jar"]