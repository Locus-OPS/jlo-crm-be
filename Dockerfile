# This Dockerfile is to generate docker image for jlo-crm-be application,
# JLO's Java Spring Boot Backend project.
# To run this Dockerfile, you need to copy this file into the parenet folder of jlo-crm-be project.
# Example build image command from this Dockerfile:
#   docker build -t jlo-crm-be .
# Example start container command:
#   docker run -d --name jlo-crm-be -p 8080:8080 -e PORT=8080 -e TZ=Asia/Bangkok -v C:/test2/logs:/home/ec2-user/jlocrm/logs -v C:/test2/attachment:/opt/attachment jlo-crm-be

# ================= STAGE 1: Build Java Spring application as JAR file =================
# Build an image that has both Maven and Java 17 to build the project.
FROM maven:3.8-openjdk-17 AS build

# Set the working directory inside the container.
WORKDIR /app

# Copy jlo-starter-parent and jlo-common-lib projects and install them to the local Maven repository.
COPY ./jlo-starter-parent ./jlo-starter-parent
RUN cd jlo-starter-parent && mvn clean install
COPY ./jlo-common-lib ./jlo-common-lib
RUN cd jlo-common-lib && mvn clean install

# Copy jlo-crm-be, a main project into the container.
COPY ./jlo-crm-be ./jlo-crm-be

# Build jlo-crm-be project and create a jar file.
RUN cd jlo-crm-be && mvn clean package

# ================= STAGE 2: Runtime =================
# Use an image that only has JRE 17, which is much smaller for running the application.
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container.
WORKDIR /app

# 3. Create folders for logs and attachments.
RUN mkdir -p /home/ec2-user/jlocrm/logs
RUN mkdir -p /opt/attachment

# Copy the built .jar file from the build stage to the runtime stage
# and rename it to app.jar for easier execution
COPY --from=build /app/jlo-crm-be/target/*.jar app.jar

# Best Practice: Create a specific user to run the application (not using root).
# This enhances security by not running the application as the root user.
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring

# Set spring profile active to "dev".
ENV SPRING_PROFILES_ACTIVE=dev

# Inform Docker that the container will run the application with this command.
ENTRYPOINT ["java", "-jar", "app.jar"]