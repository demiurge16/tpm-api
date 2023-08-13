#!/bin/sh
"$@" &
minio server --address "0.0.0.0:9000" --console-address "0.0.0.0:9001" /data
