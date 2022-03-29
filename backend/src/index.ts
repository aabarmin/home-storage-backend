import bodyParser from "body-parser";
import express, { Express } from "express";
import { devices } from "./devices";
import { flats } from "./flats";
import { health } from "./health";
import { records } from "./records";

const app: Express = express();

app.use(bodyParser.json());

app.use("/health", health);
app.use("/flats", flats);
app.use("/devices", devices);
app.use("/records", records);

app.listen(8080, () => {
  console.log(`App started on port 8080`);
});
