FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY build/distributions/app-writer.tar .
RUN tar -xf app-writer.tar --strip-components=1 && rm app-writer.tar
USER 1000
EXPOSE 8080
ENTRYPOINT ["./bin/app-writer"]