#!/bin/bash
# 本地构建docker镜像并推送到仓库
IMAGE_NAME=registry.cn-shanghai.aliyuncs.com/epiboly/demo
TAG=$(git rev-parse HEAD)

mvn clean package -U -Dmaven.test.skip=true

docker build -f Dockerfile --force-rm=true --rm -t ${IMAGE_NAME}:"${TAG}" .

docker push ${IMAGE_NAME}:"${TAG}"