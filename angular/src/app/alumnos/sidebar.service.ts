import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  private sidebarStateKey = 'sidebarState';

  constructor() { }
  // Guarda el estado del sidebar en localStorage
  setSidebarState(state: any) {
    localStorage.setItem(this.sidebarStateKey, JSON.stringify(state));
  }

  // Obtiene el estado del sidebar desde localStorage
  getSidebarState() {
    const state = localStorage.getItem(this.sidebarStateKey);
    return state ? JSON.parse(state) : {};
  }
}
