-- CreateTable
CREATE TABLE "flats" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "title" TEXT NOT NULL,
    "alias" TEXT NOT NULL
);

-- CreateTable
CREATE TABLE "devices" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "title" TEXT NOT NULL,
    "alias" TEXT NOT NULL,
    "flat_id" INTEGER NOT NULL,
    CONSTRAINT "devices_flat_id_fkey" FOREIGN KEY ("flat_id") REFERENCES "flats" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);

-- CreateTable
CREATE TABLE "files" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "file_name" TEXT NOT NULL,
    "file_type" TEXT NOT NULL,
    "file_path" TEXT NOT NULL
);

-- CreateTable
CREATE TABLE "data_records" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "flat_id" INTEGER NOT NULL,
    "year" INTEGER NOT NULL,
    "date" DATETIME NOT NULL,
    "device_id" INTEGER NOT NULL,
    "reading" INTEGER,
    "invoice_file_id" INTEGER,
    "receipt_file_id" INTEGER,
    CONSTRAINT "data_records_flat_id_fkey" FOREIGN KEY ("flat_id") REFERENCES "flats" ("id") ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT "data_records_device_id_fkey" FOREIGN KEY ("device_id") REFERENCES "devices" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);

-- CreateIndex
CREATE UNIQUE INDEX "flats_alias_key" ON "flats"("alias");

-- CreateIndex
CREATE UNIQUE INDEX "devices_alias_key" ON "devices"("alias");
