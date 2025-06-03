docker buildx build -t cytonic_bedwars --platform linux/amd64 --load --progress plain -f docker/Dockerfile .
docker tag cytonic_bedwars ghcr.io/cytonicmc/cytonic_bedwars:latest

docker stop cytonic_bedwars || true
docker rm cytonic_bedwars || true
docker run --network host -e WORLD_NAME=farm -e WORLD_TYPE=solos -e GAME_TYPE=solos -e SERVER_PORT=25566 --name cytonic_bedwars -d cytonic_bedwars:latest

docker stop cytosis_lobby || true
docker rm cytosis_lobby || true
docker run --network host -e SERVER_PORT=25565 --name cytosis_lobby -d cytosis:latest
