import { Router } from "express";
import { copyFileSync, existsSync, mkdirSync, rmSync } from "fs";
import { ObjectId } from "mongodb";
import multer from "multer";
import { getRecord, insertOne } from "./db";

export const files = Router();
const upload = multer({
  dest: "/tmp",
});

export interface FileInfo {
  fileId?: string;
  fileName: string;
  fileType: string;
  filePath: string;
}

files.get("/:fileId", async (req, res) => {
  const id = req.params.fileId;
  const query = { _id: new ObjectId(id) };
  const fileInfo = await getRecord("home_files", query);
  if (fileInfo == null) {
    res.status(500).send(`No file with id ${id}`);
    return;
  }
  fileInfo.fileId = fileInfo._id;
  delete fileInfo._id;
  res.json(fileInfo);
});

files.post("/", upload.single("file"), async (req, res, next) => {
  const file = req.file as Express.Multer.File;
  // move file to the proper location
  const uploadDir = process.env.UPLOAD_DIRECTORY as string;
  if (!existsSync(uploadDir)) {
    mkdirSync(uploadDir);
  }
  const targetPath = getTargetPath(file, uploadDir);
  copyFileSync(file?.path, targetPath);
  rmSync(file.path);
  // creating db record
  const record: FileInfo = {
    fileName: file.originalname,
    filePath: targetPath,
    fileType: file.mimetype,
  };
  const inserted = await insertOne("home_files", record);
  record.fileId = inserted.insertedId.toString();
  res.json(record);
});

function getTargetPath(file: Express.Multer.File, uploadDir: string): string {
  let index = 0;
  const originalName = file.originalname;
  let targetPath = `${uploadDir}/${originalName}`;
  while (existsSync(targetPath)) {
    targetPath = `${uploadDir}/${index}_${originalName}`;
    index++;
  }
  return targetPath;
}
