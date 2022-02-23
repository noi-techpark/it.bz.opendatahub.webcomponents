FROM node:15

ARG SSH_CDN_ADDR
ARG SSH_CDN_USER

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

WORKDIR /webcompbuild

COPY infrastructure/utils/wcstorecli.sh /webcompbuild/wcstorecli.sh
COPY .env /webcompbuild/.env

RUN rm -rf ~/.ssh \
    && mkdir -p ~/.ssh  \
    && ssh-keyscan -H $SSH_CDN_ADDR >> ~/.ssh/known_hosts \
    && ssh-keyscan -H github.com >> ~/.ssh/known_hosts \
    && echo "Host testcdnhost" >> ~/.ssh/config \
    && echo "  User $SSH_CDN_USER" >> ~/.ssh/config \
    && echo "  Hostname $SSH_CDN_ADDR" >> ~/.ssh/config \
	&& echo "  StrictHostKeyChecking no" >> ~/.ssh/config
# FIXME: "StrictHostKeyChecking no" must be fixed, replace everything with ansible or CDN API calls

RUN git config --global user.email "info@opendatahub.bz.it" \
    && git config --global user.name "Github Actons"

