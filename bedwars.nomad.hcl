job "bedwars" {
  datacenters = ["dc1"]

  group "development" {
    count = 1

    service {
      name = "bedwars-development"
      tags = ["bedwars", "development"]
      port = "minecraft"

      check {
        type         = "tcp"
        interval     = "10s"
        timeout      = "2s"
        port         = "minecraft"
        address_mode = "host"
      }
    }

    network {
      port "minecraft" {
        to = 25565
      }
    }

    task "bedwars" {
      driver = "docker"

      kill_signal  = "SIGINT"
      kill_timeout = "90s"

      logs {
        max_files = 5          # Keep the last 5 log files
        max_file_size = 10         # Rotate when files exceed 10MB
      }

      config {
        image = "ghcr.io/cytonicmc/cytonic_bedwars:entrypoint"
        ports = ["minecraft"]

        auth {
          username       = "${DOCKER_USERNAME}"
          password       = "${DOCKER_PASSWORD}"
          server_address = "ghcr.io"
        }

        mount {
          type     = "bind"
          source   = "local/config.json"
          target   = "/app/config.json"
          readonly = true
        }

        mount {
          type     = "bind"
          source   = "local/bedwars_config.json"
          target   = "/app/bedwars_config.json"
          readonly = true
        }
      }

      template {
        data        = <<EOF
DOCKER_USERNAME={{ key "cytonic/DOCKER_USERNAME" }}
DOCKER_PASSWORD={{ key "cytonic/DOCKER_PASSWORD" }}
EOF
        destination = "secrets/docker.env"
        env         = true
      }

      template {
        data        = <<EOF
{
  "environment": "development",
  "standalone": false,
  "secret": "{{ key "cytonic/SERVER_SECRET" }}",
  "port": 25565,
  "database": {
    "host": "{{ key "cytonic/DATABASE_HOST" }}",
    "port": {{ key "cytonic/DATABASE_PORT" }},
    "username": "{{ key "cytonic/DATABASE_USER" }}",
    "password": "{{ key "cytonic/DATABASE_PASSWORD" }}",
    "database": "cytonic",
    "global_database": "{{ key "cytonic/GLOBAL_DATABASE_NAME" }}"
  },
  "redis": {
    "host": "{{ key "cytonic/REDIS_HOST" }}",
    "port": {{ key "cytonic/REDIS_PORT" }},
    "password": "{{ key "cytonic/REDIS_PASSWORD" }}"
  },
  "nats": {
    "host": "{{ key "cytonic/NATS_HOSTNAME" }}",
    "port": 4222,
    "username": "{{ key "cytonic/NATS_USERNAME" }}",
    "password": "{{ key "cytonic/NATS_PASSWORD" }}"
  },
  "garage": {
    "host": "{{ key "cytonic/GARAGE_HOST" }}",
    "port": 3900,
    "username": "{{ key "cytonic/GARAGE_USERNAME" }}",
    "password": "{{ key "cytonic/GARAGE_PASSWORD" }}"
  },
  "mongo": {
    "host": "{{ key "cytonic/MONGO_HOST" }}",
    "port": 27017,
    "user": "{{ key "cytonic/MONGO_USERNAME" }}",
    "password": "{{ key "cytonic/MONGO_PASSWORD" }}",
    "database": "{{ key "cytonic/MONGO_DATABASE" }}"
  }
}
EOF
        destination = "local/config.json"
      }

      template {
        data        = <<EOF
{
  "mode": "normal"
}
EOF
        destination = "local/bedwars_config.json"
      }

      resources {
        cpu    = 500
        memory = 512
      }
    }
  }

  group "alpha" {
    count = 0

    service {
      name = "bedwars-alpha"
      tags = ["bedwars", "alpha"]
      port = "minecraft"

      check {
        type         = "tcp"
        interval     = "10s"
        timeout      = "2s"
        port         = "minecraft"
        address_mode = "host"
      }
    }

    network {
      port "minecraft" {
        to = 25565
      }
    }

    task "bedwars" {
      driver = "docker"

      kill_signal  = "SIGINT"
      kill_timeout = "90s"

      logs {
        max_files = 5          # Keep the last 5 log files
        max_file_size = 10         # Rotate when files exceed 10MB
      }

      config {
        image = "ghcr.io/cytonicmc/cytonic_bedwars:entrypoint"
        ports = ["minecraft"]

        auth {
          username       = "${DOCKER_USERNAME}"
          password       = "${DOCKER_PASSWORD}"
          server_address = "ghcr.io"
        }

        mount {
          type     = "bind"
          source   = "local/config.json"
          target   = "/app/config.json"
          readonly = true
        }

        mount {
          type     = "bind"
          source   = "local/bedwars_config.json"
          target   = "/app/bedwars_config.json"
          readonly = true
        }
      }

      template {
        data        = <<EOF
DOCKER_USERNAME={{ key "cytonic/DOCKER_USERNAME" }}
DOCKER_PASSWORD={{ key "cytonic/DOCKER_PASSWORD" }}
EOF
        destination = "secrets/docker.env"
        env         = true
      }

      template {
        data        = <<EOF
{
  "environment": "alpha",
  "standalone": false,
  "secret": "{{ key "cytonic/SERVER_SECRET" }}",
  "port": 25565,
  "database": {
    "host": "{{ key "cytonic/DATABASE_HOST" }}",
    "port": {{ key "cytonic/DATABASE_PORT" }},
    "username": "{{ key "cytonic/DATABASE_USER" }}",
    "password": "{{ key "cytonic/DATABASE_PASSWORD" }}",
    "database": "cytonic",
    "global_database": "{{ key "cytonic/GLOBAL_DATABASE_NAME" }}"
  },
  "redis": {
    "host": "{{ key "cytonic/REDIS_HOST" }}",
    "port": {{ key "cytonic/REDIS_PORT" }},
    "password": "{{ key "cytonic/REDIS_PASSWORD" }}"
  },
  "nats": {
    "host": "{{ key "cytonic/NATS_HOSTNAME" }}",
    "port": 4222,
    "username": "{{ key "cytonic/NATS_USERNAME" }}",
    "password": "{{ key "cytonic/NATS_PASSWORD" }}"
  },
  "garage": {
    "host": "{{ key "cytonic/GARAGE_HOST" }}",
    "port": 3900,
    "username": "{{ key "cytonic/GARAGE_USERNAME" }}",
    "password": "{{ key "cytonic/GARAGE_PASSWORD" }}"
  },
  "mongo": {
    "host": "{{ key "cytonic/MONGO_HOST" }}",
    "port": 27017,
    "user": "{{ key "cytonic/MONGO_USERNAME" }}",
    "password": "{{ key "cytonic/MONGO_PASSWORD" }}",
    "database": "{{ key "cytonic/MONGO_DATABASE" }}"
  }
}
EOF
        destination = "local/config.json"
      }

      template {
        data        = <<EOF
{
  "mode": "normal"
}
EOF
        destination = "local/bedwars_config.json"
      }

      resources {
        cpu    = 500
        memory = 512
      }
    }
  }

  group "production" {
    count = 0

    service {
      name = "bedwars-production"
      tags = ["bedwars", "production"]
      port = "minecraft"

      check {
        type     = "tcp"
        interval = "10s"
        timeout  = "2s"
        port     = "minecraft"
      }
    }

    network {
      port "minecraft" {
        to = 25565
      }
    }

    task "bedwars" {
      driver = "docker"

      kill_signal  = "SIGINT"
      kill_timeout = "90s"

      logs {
        max_files = 5          # Keep the last 5 log files
        max_file_size = 10         # Rotate when files exceed 10MB
      }

      config {
        image = "ghcr.io/cytonicmc/cytonic_bedwars:entrypoint"
        ports = ["minecraft"]

        auth {
          username       = "${DOCKER_USERNAME}"
          password       = "${DOCKER_PASSWORD}"
          server_address = "ghcr.io"
        }

        mount {
          type     = "bind"
          source   = "local/config.json"
          target   = "/app/config.json"
          readonly = true
        }

        mount {
          type     = "bind"
          source   = "local/bedwars_config.json"
          target   = "/app/bedwars_config.json"
          readonly = true
        }
      }

      template {
        data        = <<EOF
DOCKER_USERNAME={{ key "cytonic/DOCKER_USERNAME" }}
DOCKER_PASSWORD={{ key "cytonic/DOCKER_PASSWORD" }}
EOF
        destination = "secrets/docker.env"
        env         = true
      }

      template {
        data        = <<EOF
{
  "environment": "production",
  "standalone": false,
  "secret": "{{ key "cytonic/SERVER_SECRET" }}",
  "port": 25565,
  "database": {
    "host": "{{ key "cytonic/DATABASE_HOST" }}",
    "port": {{ key "cytonic/DATABASE_PORT" }},
    "username": "{{ key "cytonic/DATABASE_USER" }}",
    "password": "{{ key "cytonic/DATABASE_PASSWORD" }}",
    "database": "cytonic",
    "global_database": "{{ key "cytonic/GLOBAL_DATABASE_NAME" }}"
  },
  "redis": {
    "host": "{{ key "cytonic/REDIS_HOST" }}",
    "port": {{ key "cytonic/REDIS_PORT" }},
    "password": "{{ key "cytonic/REDIS_PASSWORD" }}"
  },
  "nats": {
    "host": "{{ key "cytonic/NATS_HOSTNAME" }}",
    "port": 4222,
    "username": "{{ key "cytonic/NATS_USERNAME" }}",
    "password": "{{ key "cytonic/NATS_PASSWORD" }}"
  },
  "garage": {
    "host": "{{ key "cytonic/GARAGE_HOST" }}",
    "port": 3900,
    "username": "{{ key "cytonic/GARAGE_USERNAME" }}",
    "password": "{{ key "cytonic/GARAGE_PASSWORD" }}"
  },
  "mongo": {
    "host": "{{ key "cytonic/MONGO_HOST" }}",
    "port": 27017,
    "user": "{{ key "cytonic/MONGO_USERNAME" }}",
    "password": "{{ key "cytonic/MONGO_PASSWORD" }}",
    "database": "{{ key "cytonic/MONGO_DATABASE" }}"
  }
}
EOF
        destination = "local/config.json"
      }

      template {
        data        = <<EOF
{
  "mode": "normal"
}
EOF
        destination = "local/bedwars_config.json"
      }

      resources {
        cpu    = 500
        memory = 512
      }
    }
  }

  update {
    stagger = "1s"        # Wait time between instance restarts
    max_parallel = 1               # multiple instance updated at a time
    min_healthy_time = "15s"
    healthy_deadline = "60s"         # Rollback if instance isn't healthy within 60 seconds
    auto_revert      = true            # Automatically revert on failure
  }
}