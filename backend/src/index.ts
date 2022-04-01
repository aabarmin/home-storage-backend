import bodyParser from "body-parser";
import cors from "cors";
import dotenv from "dotenv";
import express, { Express } from "express";
import { devices } from "./devices";
import { files } from "./files";
import { flats } from "./flats";
import { health } from "./health";
import { records } from "./records";

dotenv.config();
const app: Express = express();

app.use(bodyParser.json());
app.use(cors());

app.use("/health", health);
app.use("/flats", flats);
app.use("/devices", devices);
app.use("/records", records);
app.use("/files", files);

app.listen(process.env.SERVER_PORT, () => {
  console.log(`App started on port ${process.env.SERVER_PORT}`);
});
