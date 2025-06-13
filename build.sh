docker stop cytonicbedwars || true
docker rm cytonicbedwars || true
docker build -t ghcr.io/cytonicmc/cytonicbedwars:latest --no-cache --progress plain -f docker/Dockerfile .
docker run -e WORLD_NAME=farm -e WORLD_TYPE=bedwars_map_solos -e GAME_TYPE=solos -e -d -t --network host --name cytonicbedwars ghcr.io/cytonicmc/cytonicbedwars:latest
#docker push ghcr.io/cytonicmc/cytonicbedwars:latest
