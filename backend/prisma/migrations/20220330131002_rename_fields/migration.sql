/*
  Warnings:

  - You are about to drop the column `needInvoices` on the `devices` table. All the data in the column will be lost.
  - You are about to drop the column `needReadings` on the `devices` table. All the data in the column will be lost.
  - You are about to drop the column `needReceipts` on the `devices` table. All the data in the column will be lost.

*/
-- RedefineTables
PRAGMA foreign_keys=OFF;
CREATE TABLE "new_devices" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "title" TEXT NOT NULL,
    "alias" TEXT NOT NULL,
    "flat_id" INTEGER NOT NULL,
    "need_readings" BOOLEAN NOT NULL DEFAULT false,
    "need_receipts" BOOLEAN NOT NULL DEFAULT false,
    "need_invoices" BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT "devices_flat_id_fkey" FOREIGN KEY ("flat_id") REFERENCES "flats" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);
INSERT INTO "new_devices" ("alias", "flat_id", "id", "title") SELECT "alias", "flat_id", "id", "title" FROM "devices";
DROP TABLE "devices";
ALTER TABLE "new_devices" RENAME TO "devices";
CREATE UNIQUE INDEX "devices_alias_key" ON "devices"("alias");
PRAGMA foreign_key_check;
PRAGMA foreign_keys=ON;
