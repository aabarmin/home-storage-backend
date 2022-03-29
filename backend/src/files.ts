import { Router } from "express";
import { ObjectId } from "mongodb";
import { getRecord } from "./db";

export const files = Router();

export interface FileInfo {
  fileId: string;
  fileName: string;
  fileType: string;
}

files.get("/:fileId", async (req, res) => {
  const id = req.params.fileId;
  const query = { _id: new ObjectId(id) };
  const fileInfo: FileInfo = await getRecord("home_files", query);
  if (fileInfo == null) {
    res.status(500).send(`No file with id ${id}`);
    return;
  }
  res.json(fileInfo);
});
