import { Consignment } from "./consignment";

/**
 * Resource description.
 */
export interface Resource {
  id: string;
  name: string;
  consignments: Consignment[];
}
