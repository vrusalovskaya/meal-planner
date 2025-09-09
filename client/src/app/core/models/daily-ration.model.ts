import { RationItem } from "./ration-item.model";

export interface DailyRation{
   id: number;
   date: string;
   rationItems: RationItem[];
}