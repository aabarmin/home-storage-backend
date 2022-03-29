import bodyParser from "body-parser";
import express, { Express } from "express";
import { devices } from "./devices";
import { flats } from "./flats";

const app: Express = express();

app.use(bodyParser.json());

app.get("/", (request, response) => {
  response.send("Hello, world");
});

app.use("/flats", flats);
app.use("/devices", devices);

app.listen(8080, () => {
  console.log(`App started on port 8080`);
});
