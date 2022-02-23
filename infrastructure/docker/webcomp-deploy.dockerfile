FROM debian:10-slim


RUN apt-get update \
    && apt-get -y upgrade \
    && apt-get -y install --no-install-recommends \
        postgresql-client \
        jq \
        git \
        wget \
        uuid-runtime \
        ca-certificates \
        openssh-client \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
