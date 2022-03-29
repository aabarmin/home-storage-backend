import { Collection, Db, MongoClient } from "mongodb";

async function getMongoClient(): Promise<MongoClient> {
  const client: MongoClient = new MongoClient("mongodb://localhost:27017");
  return await client.connect();
}

async function getMongoDb(): Promise<Db> {
  const client = await getMongoClient();
  return client.db("home-storage");
}

export async function getCollection(collection: string): Promise<Collection> {
  const db = await getMongoDb();
  return db.collection(collection);
}
