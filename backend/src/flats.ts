import { Router } from "express";
import { FindOptions } from "mongodb";
import { getRecord, getRecords, insertOne } from "./db";

export const flats: Router = Router();

export interface Flat {
  title: String;
  alias: String;
}

const options: FindOptions = {
  projection: {
    _id: 0,
    title: 1,
    alias: 1,
  },
};

/**
 * Find all records.
 */
flats.get("/", async (req, res) => {
  const records = await getRecords("home_flats", {}, options);
  res.json(records);
});

/**
 * Find by alias
 */
flats.get("/:alias", async (req, res) => {
  const alias = req.params.alias as string;
  const query = { alias: alias };
  const record = await getRecord("home_flats", query, options);
  res.json(record);
});

/**
 * Create a new record.
 */
flats.post("/", async (req, res) => {
  const flat = req.body as Flat;
  const query = {
    alias: flat.alias,
  };
  const existingFlat = (await getRecord("home_flats", query)) as Flat;
  if (existingFlat != null) {
    res.status(500).send(`Flat with alias ${flat.alias} already exists`);
    return;
  }

  const inserted = await insertOne("home_flats", flat);
  res.location(`/flats/${inserted.insertedId}`).send();
});
