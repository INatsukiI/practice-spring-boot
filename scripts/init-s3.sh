#!/bin/bash

echo "Creating S3 bucket..."

aws --endpoint-url=http://localstack:4566 s3 mb s3://sample-bucket

echo "Bucket created successfully!"