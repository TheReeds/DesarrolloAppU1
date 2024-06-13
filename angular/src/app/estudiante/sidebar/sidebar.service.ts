import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  private sidebarStateKey = 'sidebarState';

  constructor() { }

  setSidebarState(state: any) {
    localStorage.setItem(this.sidebarStateKey, JSON.stringify(state));
  }

  getSidebarState() {
    const state = localStorage.getItem(this.sidebarStateKey);
    return state ? JSON.parse(state) : {};
  }
}
