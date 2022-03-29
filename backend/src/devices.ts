import { Router } from "express";
import { FindOptions } from "mongodb";
import { getRecord, getRecords, insertOne } from "./db";
import { Flat } from "./flats";

export const devices: Router = Router();

export interface Device {
  title: String;
  alias: String;
  flat: String;
}

devices.get("/", async (req, res) => {
  const options: FindOptions = {
    projection: {
      _id: 0,
      title: 1,
      alias: 1,
      flat: 1,
    },
  };
  const devices = await getRecords("home_devices", {}, options);
  res.json(devices);
});

devices.post("/", async (req, res) => {
  const device = req.body as Device;
  // checking if the flat exists
  const flatQuery = {
    alias: device.flat,
  };
  const flat = (await getRecord("home_flats", flatQuery)) as Flat;
  if (flat == null) {
    res.status(500).send(`No flat with alias ${device.flat}`);
    return;
  }
  // checking if a device with the given alias already exists
  const deviceQuery = {
    alias: device.alias,
  };
  const existingDevice = (await getRecord(
    "home_devices",
    deviceQuery
  )) as Device;
  if (existingDevice != null) {
    res.status(500).send(`Device with alias ${device.alias} already exists`);
    return;
  }
  // inserting
  const inserted = await insertOne("home_devices", device);
  res.location(`/devices/${inserted.insertedId}`).send();
});
