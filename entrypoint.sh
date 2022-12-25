#!/bin/sh -l

set -e

if [ -z "$INPUT_ACCESS_TOKEN" ]; then
  echo 'Required access_token'
  exit 1
fi

if [ -z "$INPUT_PAYLOAD" ]; then
  echo 'Required payload'
  exit 1
fi

if [ ! -e ./dingtalk-notify.jar ]; then
  echo "Can not find file dingtalk-notify.jar in $(pwd)"
  exit 1
fi

ENCODE_PAYLOAD=$(echo "$INPUT_PAYLOAD" | sed 's/{/%7B/g;s/}/%7D/g;s/:/%3A/g;s/\"/%22/g;s/,/%2C/g;s/ /%20/g')

if [ -z "$INPUT_SECRET" ]; then
  java -jar ./dingtalk-notify.jar -accessToken "$INPUT_ACCESS_TOKEN" -payload "$ENCODE_PAYLOAD"
else
  java -jar ./dingtalk-notify.jar -accessToken "$INPUT_ACCESS_TOKEN" -payload "$ENCODE_PAYLOAD" -secret "$INPUT_SECRET"
fi
