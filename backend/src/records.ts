import { DataRecord, Prisma } from "@prisma/client";
import { Router } from "express";
import { prisma } from "./db";

export const records = Router();

records.get("/", async (req, res) => {
  const query: Prisma.DataRecordWhereInput = {};
  if ("flatId" in req.query) {
    const flatId = Number(req.query.flatId);
    query.flatId = flatId;
  }
  if ("year" in req.query) {
    const year = Number(req.query.year);
    query.year = year;
  }
  const records = await prisma.dataRecord.findMany({
    where: query,
  });
  res.json(records);
});

/**
 * Create a new record
 */
records.post("/", async (req, res) => {
  const record = req.body as DataRecord;
  // fixing year
  if (!record.year || record.year == 0) {
    const date = new Date(record.date);
    record.year = date.getFullYear();
  }
  // checking if the flat exist
  const existingFlat = await prisma.flat.findUnique({
    where: { id: record.flatId },
  });
  if (existingFlat == null) {
    res.status(500).send(`No flat with id ${record.flatId}`);
    return;
  }
  // checking if the device exist
  const existingDevice = await prisma.device.findUnique({
    where: { id: record.deviceId },
  });
  if (existingDevice == null) {
    res.status(500).send(`No device with id ${record.deviceId}`);
    return;
  }
  // saving a record
  const inserted = await prisma.dataRecord.create({ data: record });
  res.status(201).location(`/records/${inserted.id}`).send();
});

records.put("/:id", async (req, res) => {
  const id = Number(req.params.id);
  const existingRecord = await prisma.dataRecord.findUnique({
    where: { id: id },
  });
  if (existingRecord == null) {
    res.status(500).send(`No record with id ${id}`);
    return;
  }
  const record = req.body as DataRecord;
  await prisma.dataRecord.update({
    data: record,
    where: { id: id },
  });
  res.json(record);
});
