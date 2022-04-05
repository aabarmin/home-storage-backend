export interface BuildInfo {
  artifact: string;
  name: string;
  time: string;
  version: string;
  group: string;
}

export interface VersionInfo {
  build: BuildInfo;
}
