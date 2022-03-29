import { Router } from "express";
import { FindOptions } from "mongodb";
import { getCollection } from "./db";

export const flats: Router = Router();

interface Flat {
  title: String;
  alias: String;
}

flats.get("/", async (req, res) => {
  const collection = await getCollection("home_flats");
  const options: FindOptions = {
    projection: {
      title: 1,
      alias: 1,
    },
  };
  const records = await collection.find({}, options).toArray();
  res.json(records);
});

flats.post("/", async (req, res) => {
  const flat = req.body as Flat;
  const collection = await getCollection("home_flats");
  const inserted = await collection.insertOne(flat);

  res.location(`/flats/${inserted.insertedId}`);
});
