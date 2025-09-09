import { Injectable } from '@angular/core';
import { MenusDataService } from '../../../core/services/menus-data.service';
import { Menu } from '../../../core/models/menu.model';

@Injectable()
export class ArchiveMenusService {

   constructor(
      private readonly menusDataService: MenusDataService
   ) { }

   pastMenus: Menu[] = [];

   async refresh(): Promise<void> {
      this.pastMenus = await this.menusDataService.getPastMenus();
   }
}
