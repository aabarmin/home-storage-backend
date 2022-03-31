import { Router } from "express";
import { readdirSync, readFileSync } from "fs";

interface VersionInfo {
  version?: string;
  build?: string;
}

export const health = Router();

health.get("/", (req, res) => {
  res.json({
    health: "OK",
  });
});

health.get("/env", (req, res) => {
  const versionContent = readFileSync("./dist/version.json").toString();
  const versionInfo = JSON.parse(versionContent) as VersionInfo;

  res.json({
    name: process.env.ENVIRONMENT_NAME,
    version: versionInfo?.version || "not set",
    build: versionInfo?.build || "not set",
  });
});
