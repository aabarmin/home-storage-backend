import { Flat } from ".prisma/client";
import { Router } from "express";
import { prisma } from "./db";

export const flats: Router = Router();

/**
 * Find all records.
 */
flats.get("/", async (req, res) => {
  const records = await prisma.flat.findMany();
  res.json(records);
});

/**
 * Find by id
 */
flats.get("/:id", async (req, res) => {
  const id = Number(req.params.id);
  const flat = await prisma.flat.findUnique({
    where: {
      id: id,
    },
  });
  res.json(flat);
});

/**
 * Create a new record.
 */
flats.post("/", async (req, res) => {
  const flat = req.body as Flat;

  const existingFlat = await prisma.flat.findFirst({
    where: {
      alias: flat.alias,
    },
  });
  if (existingFlat != null) {
    res.status(500).send(`Flat with alias ${flat.alias} already exists`);
    return;
  }
  const created = await prisma.flat.create({
    data: {
      title: flat.title,
      alias: flat.alias,
    },
  });

  res.location(`/flats/${created.id}`).send();
});
