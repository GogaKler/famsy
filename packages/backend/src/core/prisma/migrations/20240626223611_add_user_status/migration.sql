-- CreateEnum
CREATE TYPE "Status" AS ENUM ('ACTIVE', 'DELETED');

-- AlterTable
ALTER TABLE "User" ADD COLUMN     "status" "Status" DEFAULT 'ACTIVE';
