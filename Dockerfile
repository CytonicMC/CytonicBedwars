FROM eclipse-temurin:25

LABEL authors="CytonicMC"

WORKDIR /app

ADD build/libs/CytonicBedwars.jar .

# Expose the port
EXPOSE 25565

CMD ["java", "-jar", "CytonicBedwars.jar"]