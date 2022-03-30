#!/bin/sh

# Apply DB migrations
npx prisma db push

# Run the app
npm start