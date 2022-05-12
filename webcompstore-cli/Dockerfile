ARG PATH
FROM python:3.8.13-alpine3.15

COPY requirements.txt ./
COPY .env.example ./.env
COPY webcompstore-cli.py ./
COPY wait-for ./

RUN pip install --no-cache-dir -r requirements.txt
