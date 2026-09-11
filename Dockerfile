FROM eclipse-temurin:25

LABEL authors="CytonicMC"

WORKDIR /app

ADD build/libs/Bedwars.jar .

# Expose the port
EXPOSE 25565

CMD ["java","-Dminestom.shutdown-on-signal=false", "-jar", "Bedwars.jar"]