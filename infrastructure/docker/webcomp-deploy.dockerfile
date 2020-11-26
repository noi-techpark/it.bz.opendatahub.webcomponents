FROM debian:10-slim

ARG JENKINS_GROUP_ID=1000
ARG JENKINS_USER_ID=1000

RUN groupadd --gid $JENKINS_GROUP_ID jenkins && \
    useradd --uid $JENKINS_USER_ID --gid $JENKINS_GROUP_ID --create-home jenkins

RUN apt-get update \
    && apt-get -y upgrade \
    && apt-get -y install --no-install-recommends \
        postgresql-client \
        jq \
        wget \
        ca-certificates \
        openssh-client \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
