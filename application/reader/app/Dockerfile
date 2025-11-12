FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY build/distributions/app-reader.tar .
RUN tar -xf app-reader.tar --strip-components=1 && rm app-reader.tar
USER 1000
EXPOSE 8080
ENTRYPOINT ["./bin/app-reader"]