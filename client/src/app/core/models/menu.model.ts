import { DailyRation } from "./daily-ration.model";

export interface Menu{
   id: number;
   startDate: string;
   endDate: string;
   rations: DailyRation[]
}
