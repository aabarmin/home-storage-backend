#!/bin/sh

# Generate .env file
rm -rf .env
echo "UPLOAD_DIRECTORY=${UPLOAD_DIRECTORY:-./uploads}" >> .env
echo "SERVER_PORT=${SERVER_PORT:-8080}" >> .env

DB_URL=file:./../db/storage_${ENVIRONMENT:-local}.db
echo "DATABASE_URL=${DB_URL}" >> .env
echo "ENVIRONMENT_NAME=${ENVIRONMENT:-local}" >> .env

# Apply DB migrations
npm run db:migrate

# Run the app
npm start