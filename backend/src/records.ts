import { Router } from "express";
import { ObjectId } from "mongodb";
import { getRecord, getRecords, insertOne, updateOne } from "./db";
import { Device } from "./devices";
import { Flat } from "./flats";

export const records = Router();

export interface Record {
  id?: string;
  flat: string;
  year: number;
  date: Date;
  device: string;
  reading?: number;
  invoiceFile?: string;
  receiptFile?: string;
}

export interface Query {
  flat?: string;
  year?: number;
}

records.get("/", async (req, res) => {
  const query: Query = {};
  if ("flat" in req.query) {
    const flat = req.query.flat as string;
    query.flat = flat;
  }
  if ("year" in req.query) {
    const yearString = req.query.year as string;
    const year: number = parseInt(yearString);
    query.year = year;
  }

  const records = await (
    await getRecords("home_records", query)
  ).map((record) => {
    const target = record;
    target.id = target._id;
    delete target._id;
    return target;
  });
  res.json(records);
});

/**
 * Create a new record
 */
records.post("/", async (req, res) => {
  const record = req.body as Record;
  // fixing year
  if (!record.year || record.year == 0) {
    const date = new Date(record.date);
    record.year = date.getFullYear();
  }
  // checking if the flat exist
  const existingFlat = (await getRecord("home_flats", {
    alias: record.flat,
  })) as Flat;
  if (existingFlat == null) {
    res.status(500).send(`No flat with alias ${record.flat}`);
    return;
  }
  // checking if the device exist
  const existingDevice = (await getRecord("home_devices", {
    alias: record.device,
  })) as Device;
  if (existingDevice == null) {
    res.status(500).send(`No device with alias ${record.device}`);
    return;
  }
  // saving a record
  const inserted = await insertOne("home_records", record);
  res.status(201).location(`/records/${inserted.insertedId}`).send();
});

records.put("/:id", async (req, res) => {
  const id = req.params.id as string;
  // checking if the record exists
  const query = { _id: new ObjectId(id) };
  const existingRecord = await getRecord("home_records", query);
  if (existingRecord == null) {
    res.status(500).send(`No record with id ${id}`);
    return;
  }
  const record = req.body as Record;
  delete record.id;
  // updating the record
  const updated = await updateOne("home_records", query, record);
  res.json(record);
});
