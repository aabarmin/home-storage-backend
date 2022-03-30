import { Router } from "express";
import { copyFileSync, existsSync, mkdirSync, rmSync } from "fs";
import multer from "multer";
import { prisma } from "./db";

export const files = Router();
const upload = multer({
  dest: "/tmp",
});

files.get("/:fileId/download", async (req, res) => {
  const id = Number(req.params.fileId);
  const fileInfo = await prisma.fileInfo.findUnique({ where: { fileId: id } });
  if (fileInfo == null) {
    res.status(500).send(`No file with id ${id}`);
    return;
  }
  res.download(fileInfo.filePath);
});

files.get("/:fileId", async (req, res) => {
  const id = Number(req.params.fileId);
  const record = await prisma.fileInfo.findUnique({ where: { fileId: id } });
  res.json(record);
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
  const record = await prisma.fileInfo.create({
    data: {
      fileName: file.originalname,
      filePath: targetPath,
      fileType: file.mimetype,
    },
  });
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
