import { Device } from ".prisma/client";
import { Router } from "express";
import { prisma } from "./db";

export const devices: Router = Router();

devices.get("/", async (req, res) => {
  if ("flatId" in req.query) {
    const records = await prisma.device.findMany({
      where: {
        flatId: Number(req.query.flatId),
      },
    });
    res.json(records);
    return;
  }
  const records = await prisma.device.findMany();
  res.json(records);
});

devices.get("/:id", async (req, res) => {
  const id = Number(req.params.id);
  const device = await prisma.device.findUnique({
    where: {
      id: id,
    },
  });
  res.json(device);
});

devices.post("/", async (req, res) => {
  const device = req.body as Device;
  // checking if the flat exists
  const existingFlat = await prisma.flat.findUnique({
    where: {
      id: device.flatId,
    },
  });
  if (existingFlat == null) {
    res.status(500).send(`No flat with id ${device.flatId}`);
    return;
  }
  // checking if a device with the given alias already exists
  const existingDevice = await prisma.device.findUnique({
    where: { alias: device.alias },
  });
  if (existingDevice != null) {
    res.status(500).send(`Device with alias ${device.alias} already exists`);
    return;
  }
  // inserting
  const inserted = await prisma.device.create({ data: device });
  res.location(`/devices/${inserted.id}`).send();
});
