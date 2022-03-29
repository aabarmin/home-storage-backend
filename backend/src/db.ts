import {
  Collection,
  Db,
  Filter,
  FindOptions,
  InsertOneResult,
  MongoClient,
} from "mongodb";

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

export async function getRecords(
  collectionName: string,
  query?: any,
  options?: FindOptions
): Promise<any[]> {
  const collection = await getCollection(collectionName);
  if (collection == null) {
    throw new Error(`Can't get collection with name ${collectionName}`);
  }
  const records = await collection.find(query, options).toArray();
  return records;
}

export async function getRecord(
  collectionName: string,
  query?: any,
  options?: FindOptions
): Promise<any | null> {
  const collection = await getCollection(collectionName);
  if (collection == null) {
    throw new Error(`Can't get collection with name ${collectionName}`);
  }
  const record = await collection.findOne(query, options);
  return record;
}

export async function insertOne(
  collectionName: string,
  record: any
): Promise<InsertOneResult> {
  const collection = await getCollection(collectionName);
  if (collection == null) {
    throw new Error(`Can't get collection with name ${collectionName}`);
  }
  const inserted = await collection.insertOne(record);
  return inserted;
}
