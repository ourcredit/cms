import Entity from "@/store/entities/entity";

export default class SaleForce extends Entity<number> {
  month: string;
  force: Number;
  comeTrue: Number;
}
